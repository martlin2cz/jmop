package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.check.BaseJMOPEnvironmentChecker;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.preparer.TrackPreparer;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.LocalSourceWrapper;
import javafx.util.Duration;

/**
 * The. Main. Entry. Class. Encapsulates all what shall be done by UI (GUI, TUI)
 * to the JMOP.
 * 
 * @author martin
 *
 */
public class JMOPPlayer {
	private final BaseConfiguration config;
	private final ErrorReporter reporter;
	private final JMOPSources sources;
	private final JMOPPlaying playing;
	private final BaseJMOPEnvironmentChecker checker;
	private final JMOPData data;

	public JMOPPlayer(BaseConfiguration config, ErrorReporter reporter, PlayerEngine engine, LocalSourceWrapper local,
			TrackPreparer preparer, BaseJMOPEnvironmentChecker checker) {
		this.config = config;
		this.reporter = reporter;
		this.sources = new JMOPSources(local, preparer);
		this.playing = new JMOPPlaying(engine);
		this.checker = checker;
		this.data = new JMOPData(this);
	}

	/**
	 * Returns configuration.
	 * 
	 * @return
	 */
	public BaseConfiguration getConfig() {
		return config;
	}

	/**
	 * Returns error reporter.
	 * 
	 * @return
	 */
	public ErrorReporter getErrorReporter() {
		return reporter;
	}

	/**
	 * Returns JMOP sources wrapper.
	 * 
	 * @return
	 */
	protected JMOPSources getSources() {
		return sources;
	}

	/**
	 * Returns JMOP playing wrapper.
	 * 
	 * @return
	 */
	protected JMOPPlaying getPlaying() {
		return playing;
	}

	/**
	 * Returns JMOP data wrapper.
	 * 
	 * @return
	 */
	public JMOPData getData() {
		return data;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Starts new bundle.
	 * 
	 * @param kind
	 * @param bundleName
	 * @param querySeed
	 * @throws JMOPSourceException
	 */
	public void startNewBundle(SourceKind kind, String bundleName, String querySeed) throws JMOPSourceException {

		PlayerEngine engine = playing.getEngine();
		Playlist playlist = sources.createNewBundleAndPrepare(kind, bundleName, querySeed, engine);
		playing.startPlayingPlaylist(playlist, false);
	}

	/**
	 * Starts new playlist.
	 * 
	 * @param query
	 * @throws JMOPSourceException
	 */
	public void startNewPlaylist(String query) throws JMOPSourceException {
		Bundle bundle = getCurrentBundle();
		PlayerEngine engine = playing.getEngine();

		Playlist playlist = sources.createNewPlaylist(bundle, query, engine);
		playing.startPlayingPlaylist(playlist, false);
	}

	/**
	 * Starts specified playlist.
	 * 
	 * @param bundleName
	 * @param playlistName
	 * @throws JMOPSourceException
	 */
	public void startPlaylist(String bundleName, String playlistName) throws JMOPSourceException {
		PlayerEngine engine = playing.getEngine();

		Playlist playlist = sources.loadPlaylist(bundleName, playlistName, engine);
		playing.startPlayingPlaylist(playlist, true);
	}

	/**
	 * Saves paylist with new name.
	 * 
	 * @param newPlaylistName
	 * @throws JMOPSourceException
	 */
	public void savePlaylistAs(String newPlaylistName) throws JMOPSourceException {
		Playlist playlist = getCurrentPlaylist();
		sources.savePlaylist(playlist, newPlaylistName);
	}

	/**
	 * Locks/unlocks playlist.
	 */
	public void togglePlaylistLockedStatus() {
		Playlist playlist = getCurrentPlaylist();
		playing.togglePlaylistLockedStatus(playlist);
	}

	/**
	 * Clears remaining tracks.
	 */
	public void clearRemainingTracks() {
		Playlist playlist = getCurrentPlaylist();
		playing.clearRemainingTracks(playlist);
	}

	/**
	 * Adds track (to the current playlist) by specified search query.
	 * 
	 * @param query
	 * @throws JMOPSourceException
	 */
	public void loadAndAddTrack(String query) throws JMOPSourceException {
		Bundle bundle = getCurrentBundle();
		PlayerEngine engine = playing.getEngine();

		sources.queryAndLoad(bundle, query, engine);
	}

	/**
	 * Plays track at given index (of the current playlist).
	 * 
	 * @param index
	 * @throws JMOPSourceException
	 */
	public void playTrack(int index) throws JMOPSourceException {
		playing.playTrack(index);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Starts playing.
	 * 
	 * @throws JMOPSourceException
	 */
	public void startPlaying() throws JMOPSourceException {
		playing.startPlaying();
	}

	/**
	 * Stops playing.
	 */
	public void stopPlaying() {
		playing.stopPlaying();
	}

	/**
	 * Pauses playing.
	 */
	public void pausePlaying() {
		playing.pausePlaying();
	}

	/**
	 * Resumes playing.
	 */
	public void resumePlaying() {
		playing.resumePlaying();
	}

	/**
	 * Jumps to next track.
	 * 
	 * @throws JMOPSourceException
	 */
	public void toNext() throws JMOPSourceException {
		playing.toNext();
	}

	/**
	 * Goes back to the previous track.
	 * 
	 * @throws JMOPSourceException
	 */
	public void toPrevious() throws JMOPSourceException {
		playing.toPrevious();
	}

	/**
	 * Seeks to given time.
	 * 
	 * @param to
	 */
	public void seek(Duration to) {
		playing.seek(to);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Lists all bundle names.
	 * 
	 * @return
	 * @throws JMOPSourceException
	 */
	public List<String> listBundles() throws JMOPSourceException {
		return sources.listAllBundles();
	}

	/**
	 * Lists all playlists in the given bundle.
	 * 
	 * @param bundleName
	 * @return
	 * @throws JMOPSourceException
	 */
	public List<String> listPlaylists(String bundleName) throws JMOPSourceException {
		return sources.listPlaylists(bundleName);
	}

	/**
	 * Runs the environment check.
	 * 
	 * @return
	 */
	public String runCheck() {
		return checker.doCheck();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the current playlist.
	 * 
	 * @return
	 */
	private Playlist getCurrentPlaylist() {
		return playing.getEngine().getPlaylister().playlistProperty().get();
	}

	/**
	 * Returns the current bundle.
	 * 
	 * @return
	 */
	private Bundle getCurrentBundle() {
		Playlist playlist = getCurrentPlaylist();
		return playlist.getBundle();
	}

}
