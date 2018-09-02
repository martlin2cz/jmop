package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.JMOPPlaylisterWithGui;
import cz.martlin.jmop.core.player.TrackPreparer;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.AutomaticSavesPerformer;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class JMOPPlayer {
	private final JMOPSources sources;
	private final JMOPPlaying playing;
	private final CoreGuiDescriptor descriptor;
	private final JMOPChecker checker;
	private final ObjectProperty<Playlist> currentPlaylistProperty;
	

	public JMOPPlayer(AbstractRemoteSource remote, BaseLocalSource local, BaseSourceDownloader downloader,
			BaseSourceConverter converter, GuiDescriptor gui, Playlist playlistToPlayOrNot,
			JMOPPlaylisterWithGui playlister, TrackPreparer preparer, AutomaticSavesPerformer saver) {

		this.sources = new JMOPSources(local, remote, downloader, converter, preparer, playlister, gui);
		this.playing = new JMOPPlaying(playlister, saver, playlistToPlayOrNot);
		this.descriptor = new CoreGuiDescriptor(this);
		this.checker = new JMOPChecker(downloader, converter);
		this.currentPlaylistProperty = new SimpleObjectProperty<>();
	}

	protected JMOPSources getSources() {
		return sources;
	}

	protected JMOPPlaying getPlaying() {
		return playing;
	}

	public CoreGuiDescriptor getDescriptor() {
		return descriptor;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public Bundle getCurrentBundle() {
		if (playing.getCurrentPlaylist() != null) {
			return playing.getCurrentPlaylist().getBundle();
		} else {
			return null;
		}
	}

	public Playlist getCurrentPlaylist() {
		return playing.getCurrentPlaylist();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void startNewBundle(SourceKind kind, String bundleName, String querySeed) throws JMOPSourceException {
		Playlist playlist = sources.createNewBundleAndPrepare(kind, bundleName, querySeed);
		playing.startPlayingPlaylist(playlist);
		currentPlaylistProperty.set(playlist);
	}

	public void startPlaylist(String bundleName, String playlistName) throws JMOPSourceException {
		Playlist playlist = sources.loadPlaylist(bundleName, playlistName);
		playing.startPlayingPlaylist(playlist);
		currentPlaylistProperty.set(playlist);
	}

	public void startNewPlaylist(String querySeed) throws JMOPSourceException {
		Bundle bundle = getCurrentBundle();
		Playlist playlist = sources.createNewPlaylist(bundle, querySeed);
		playing.startPlayingPlaylist(playlist);
		currentPlaylistProperty.set(playlist);
	}

	public void savePlaylistAs(String newPlaylistName) throws JMOPSourceException {
		Playlist playlist = getCurrentPlaylist();
		sources.savePlaylist(playlist, newPlaylistName);
	}
	

	public void loadAndAddTrack(String querySeed) throws JMOPSourceException {
		Bundle bundle = getCurrentBundle();
		Track track = sources.queryAndLoad(bundle, querySeed);
		playing.addToPlaylist(track);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	public void startPlaying() {
		playing.startPlaying();
		// TODO sources -> check and load next
	}

	public void stopPlaying() {
		playing.stopPlaying();
	}

	public void pausePlaying() {
		playing.pausePlaying();
	}

	public void resumePlaying() {
		playing.resumePlaying();
	}

	public void toNext() {
		playing.toNext();
	}

	public void toPrevious() {
		playing.toPrevious();
	}

	public void seek(Duration to) {
		playing.seek(to);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	public List<String> listBundles() throws JMOPSourceException {
		return sources.listAllBundles();
	}

	public List<String> listPlaylists(String bundleName) throws JMOPSourceException {
		return sources.listPlaylists(bundleName);
	}
	

	public String runCheck() {
		return checker.doCheck();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public String currentPlaylistAsString() {
		Playlist playlist = playing.getCurrentPlaylist();
		return playlist.toHumanString();
	}

	public ObjectProperty<Playlist> currentPlaylistProperty() {
		return currentPlaylistProperty;
	}



}
