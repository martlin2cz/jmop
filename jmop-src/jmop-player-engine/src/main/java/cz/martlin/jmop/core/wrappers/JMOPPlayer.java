package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.BaseOperations;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
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
	private final BaseErrorReporter reporter;
	private final JMOPSources sources;
	private final JMOPPlaying playing;
	private final JMOPData data;

	public JMOPPlayer(BaseConfiguration config, BaseErrorReporter reporter, PlayerEngine engine,
			BaseLocalSource local, BaseOperations operations) {
		this.config = config;
		this.reporter = reporter;
		this.sources = new JMOPSources(local, operations);
		this.playing = new JMOPPlaying(engine);
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
	public BaseErrorReporter getErrorReporter() {
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
	 * @throws JMOPMusicbaseException
	 */
	public void startNewBundle(SourceKind kind, String bundleName, String querySeed) throws JMOPMusicbaseException {

		PlayerEngine engine = playing.getEngine();
		Playlist playlist = sources.createNewBundleAndPrepare(kind, bundleName, querySeed, engine);
		playing.startPlayingPlaylist(playlist, false);
	}

	/**
	 * Starts new playlist.
	 * 
	 * @param query
	 * @throws JMOPMusicbaseException
	 */
	public void startNewPlaylist(String query) throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public void startPlaylist(String bundleName, String playlistName) throws JMOPMusicbaseException {
		PlayerEngine engine = playing.getEngine();

		Playlist playlist = sources.loadPlaylist(bundleName, playlistName, engine);
		playing.startPlayingPlaylist(playlist, true);
	}

	/**
	 * Saves paylist with new name.
	 * 
	 * @param newPlaylistName
	 * @throws JMOPMusicbaseException
	 */
	public void savePlaylistAs(String newPlaylistName) throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public void loadAndAddTrack(String query) throws JMOPMusicbaseException {
		Bundle bundle = getCurrentBundle();
		PlayerEngine engine = playing.getEngine();

		sources.queryAndLoad(bundle, query, engine);
	}

	/**
	 * Plays track at given index (of the current playlist).
	 * 
	 * @param index
	 * @throws JMOPMusicbaseException
	 */
	public void playTrack(int index) throws JMOPMusicbaseException {
		playing.playTrack(index);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Starts playing.
	 * 
	 * @throws JMOPMusicbaseException
	 */
	public void startPlaying() throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public void toNext() throws JMOPMusicbaseException {
		playing.toNext();
	}

	/**
	 * Goes back to the previous track.
	 * 
	 * @throws JMOPMusicbaseException
	 */
	public void toPrevious() throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public List<String> listBundles() throws JMOPMusicbaseException {
		return sources.listAllBundles();
	}

	/**
	 * Lists all playlists in the given bundle.
	 * 
	 * @param bundleName
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public List<String> listPlaylists(String bundleName) throws JMOPMusicbaseException {
		return sources.listPlaylists(bundleName);
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
