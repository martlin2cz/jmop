package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.playlister.SimplePlayerEngine;
import javafx.util.Duration;

/**
 * The playing JMOP wrapper. Class responsible for playing stuff (play, pause,
 * next, prev, add, ...). Do not invoke directly, use {@link JMOPPlayer} to do
 * so.
 * 
 * @author martin
 */
public class JMOPPlaying {
	private final SimplePlayerEngine engine;

	private Playlist currentPlaylist;

	public JMOPPlaying(SimplePlayerEngine engine) {
		super();
		this.engine = engine;

	}

	/**
	 * Returns the engine.
	 * 
	 * @return
	 */
	protected SimplePlayerEngine getEngine() {
		return engine;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Mark as playing given playlist. If start playing, start the playing
	 * immediatelly (if not only mark).
	 * 
	 * @param playlist
	 * @param startPlaying
	 * @throws JMOPMusicbaseException
	 */
	public void startPlayingPlaylist(Playlist playlist, boolean startPlaying) throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public void startPlaying() throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public void toNext() throws JMOPMusicbaseException {
		engine.toNext();
	}

	/**
	 * Go back to the previous track.
	 * 
	 * @throws JMOPMusicbaseException
	 */
	public void toPrevious() throws JMOPMusicbaseException {
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
	 * @throws JMOPMusicbaseException
	 */
	public void playTrack(int index) throws JMOPMusicbaseException {
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
