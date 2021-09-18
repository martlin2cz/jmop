package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import javafx.util.Duration;

/**
 * The playing JMOP wrapper. Class responsible for playing stuff (play, pause,
 * next, prev, add, ...). Do not invoke directly, use {@link JMOPPlayer} to do
 * so.
 * 
 * @author martin
 *
 */
public class JMOPPlaying {
	private final PlayerEngine engine;

	private Playlist currentPlaylist;

	public JMOPPlaying(PlayerEngine engine) {
		super();
		this.engine = engine;

	}

	/**
	 * Returns the engine.
	 * 
	 * @return
	 */
	protected PlayerEngine getEngine() {
		return engine;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Mark as playing given playlist. If start playing, start the playing
	 * immediatelly (if not only mark).
	 * 
	 * @param playlist
	 * @param startPlaying
	 * @throws JMOPSourceException
	 */
	public void startPlayingPlaylist(Playlist playlist, boolean startPlaying) throws JMOPSourceException {
		if (currentPlaylist != null) {
			engine.stopPlayingPlaylist(currentPlaylist);
		}

		engine.startPlayingPlaylist(playlist);

		if (startPlaying) {
			startPlaying();
		}
	}

	/**
	 * Mark as stopped playing given playlist.
	 * 
	 * @param currentPlaylist
	 */
	public void stopPlayingPlaylist(Playlist currentPlaylist) {
		engine.stopPlayingPlaylist(currentPlaylist);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Start playing.
	 * 
	 * @throws JMOPSourceException
	 */
	public void startPlaying() throws JMOPSourceException {
		engine.playNext();
	}

	/**
	 * Stop playing.
	 */
	public void stopPlaying() {
		engine.stop();
	}

	/**
	 * Pause playing.
	 */
	public void pausePlaying() {
		engine.pause();
	}

	/**
	 * Resume playing.
	 */
	public void resumePlaying() {
		engine.resume();
	}

	/**
	 * Jump to next track.
	 * 
	 * @throws JMOPSourceException
	 */
	public void toNext() throws JMOPSourceException {
		engine.toNext();
	}

	/**
	 * Go back to the previous track.
	 * 
	 * @throws JMOPSourceException
	 */
	public void toPrevious() throws JMOPSourceException {
		engine.toPrevious();
	}

	/**
	 * Seek to given time (duration).
	 * 
	 * @param to
	 */
	public void seek(Duration to) {
		engine.seek(to);
	}

	/**
	 * Add track (to play(list)).
	 * 
	 * @param track
	 */
	public void addToPlaylist(Track track) {
		engine.add(track);
	}

	/**
	 * Play track at specified index.
	 * 
	 * @param index
	 * @throws JMOPSourceException
	 */
	public void playTrack(int index) throws JMOPSourceException {
		engine.play(index);
	}

	/**
	 * Lock/unlock playlist.
	 * 
	 * @param playlist
	 */
	public void togglePlaylistLockedStatus(Playlist playlist) {
		boolean is = playlist.isLocked();
		boolean isNot = !is;

		playlist.setLocked(isNot);
	}

	/**
	 * Clear all remaining tracks.
	 * 
	 * @param playlist
	 */
	public void clearRemainingTracks(Playlist playlist) {
		engine.clearRemaining();
	}

}
