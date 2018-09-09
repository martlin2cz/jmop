package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.DefaultConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.PlayerWrapper;
import cz.martlin.jmop.core.playlister.Playlister;
import cz.martlin.jmop.core.preparer.TrackPreparer;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class JMOPPlayer {
	private final BaseConfiguration config;
	private final JMOPSources sources;
	private final JMOPPlaying playing;
	private final BaseJMOPEnvironmentChecker checker;
	private final ObjectProperty<Playlist> currentPlaylistProperty;

	public JMOPPlayer(BaseConfiguration config, BaseLocalSource local, TrackPreparer preparer,
			Playlister playlister, PlayerWrapper player, BaseJMOPEnvironmentChecker checker) {

		this.config = config;
		this.sources = new JMOPSources(local, preparer, playlister);
		this.playing = new JMOPPlaying(playlister);
		this.checker = checker;
		this.currentPlaylistProperty = new SimpleObjectProperty<>();
	}

	protected JMOPSources getSources() {
		return sources;
	}

	protected JMOPPlaying getPlaying() {
		return playing;
	}


	public BaseConfiguration getConfig() {
		return config;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public Bundle getCurrentBundle() {
//		if (playing.getCurrentPlaylist() != null) {
//			return playing.getCurrentPlaylist().getBundle();
//		} else {
//			return null;
//		}
		return currentPlaylistProperty.get().getBundle();
	}

	public Playlist getCurrentPlaylist() {
//		return playing.getCurrentPlaylist();
		return currentPlaylistProperty.get();
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
		sources.queryAndLoad(bundle, querySeed);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	public void startPlaying() throws JMOPSourceException {
		playing.startPlaying();
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

	public void toNext() throws JMOPSourceException {
		playing.toNext();
	}

	public void toPrevious() throws JMOPSourceException {
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
//	@Deprecated
//	public String currentPlaylistAsString() {
//		Playlist playlist = playing.getCurrentPlaylist();
//		return playlist.toHumanString();
//	}

	public ObjectProperty<Playlist> currentPlaylistProperty() {
		return currentPlaylistProperty;
	}


}
