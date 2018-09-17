package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.preparer.TrackPreparer;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.LocalSourceWrapper;
import javafx.util.Duration;

public class JMOPPlayer {
	private final BaseConfiguration config;
	private final JMOPSources sources;
	private final JMOPPlaying playing;
	private final BaseJMOPEnvironmentChecker checker;
	private final JMOPData data;

	public JMOPPlayer(BaseConfiguration config, PlayerEngine engine, LocalSourceWrapper local, TrackPreparer preparer,
			BaseJMOPEnvironmentChecker checker) {
		this.config = config;
		this.sources = new JMOPSources(local, preparer);
		this.playing = new JMOPPlaying(engine);
		this.checker = checker;
		this.data = new JMOPData(this);
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

	public JMOPData getData() {
		return data;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public void startNewBundle(SourceKind kind, String bundleName, String querySeed) throws JMOPSourceException {

		PlayerEngine engine = playing.getEngine();
		Playlist playlist = sources.createNewBundleAndPrepare(kind, bundleName, querySeed, engine);
		playing.startPlayingPlaylist(playlist, false);
	}

	public void startNewPlaylist(String querySeed) throws JMOPSourceException {
		Bundle bundle = getCurrentBundle();
		PlayerEngine engine = playing.getEngine();

		Playlist playlist = sources.createNewPlaylist(bundle, querySeed, engine);
		playing.startPlayingPlaylist(playlist, false);
	}

	public void startPlaylist(String bundleName, String playlistName) throws JMOPSourceException {
		PlayerEngine engine = playing.getEngine();

		Playlist playlist = sources.loadPlaylist(bundleName, playlistName, engine);
		playing.startPlayingPlaylist(playlist, true);
	}

	public void savePlaylistAs(String newPlaylistName) throws JMOPSourceException {
		Playlist playlist = getCurrentPlaylist();
		sources.savePlaylist(playlist, newPlaylistName);
	}

	public void loadAndAddTrack(String querySeed) throws JMOPSourceException {
		Bundle bundle = getCurrentBundle();
		PlayerEngine engine = playing.getEngine();

		sources.queryAndLoad(bundle, querySeed, engine);
	}
	

	public void playTrack(int index) throws JMOPSourceException {
		playing.playTrack(index);
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
	// @Deprecated
	// public String currentPlaylistAsString() {
	// Playlist playlist = playing.getCurrentPlaylist();
	// return playlist.toHumanString();
	// }

	private Playlist getCurrentPlaylist() {
		return playing.getEngine().getPlaylister().playlistProperty().get();
	}

	private Bundle getCurrentBundle() {
		Playlist playlist = getCurrentPlaylist();
		return playlist.getBundle();
	}


}
