package cz.martlin.jmop.player.engine;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

public interface BasePlayerEngine {

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Mark as playing given playist.
	 * 
	 * @param playlist
	 * @throws JMOPMusicbaseException 
	 */
	void startPlayingPlaylist(Playlist playlist) throws JMOPMusicbaseException;

	/**
	 * Mark as not playing given playlist.
	 * 
	 * @param currentPlaylist
	 * @throws JMOPMusicbaseException 
	 */
	void stopPlayingPlaylist() throws JMOPMusicbaseException;
	

	/**
	 * TODO doc 
	 */
	void playlistChanged();
	

	/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the currently played playlist or null if no such.
	 * @return
	 */
	Playlist currentPlaylist();
	
	/**
	 * Returns the currently played track or null if not playing track.
	 * @return
	 */
	Track currentTrack();
	
	/**
	 * Returns the current duration of the track played or null if not any.
	 * @return
	 */
	Duration currentDuration();
	
	
	/**
	 * Returns the current player status. Never null.
	 * @return
	 */
	PlayerStatus currentStatus();
	

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Play next track in the queque.
	 * 
	 * @throws JMOPMusicbaseException
	 */
	void play() throws JMOPMusicbaseException;

	/**
	 * Play the index-th track in the (current) playlist.
	 * 
	 * @param index
	 * @throws JMOPMusicbaseException
	 */
	void play(int index) throws JMOPMusicbaseException;

	/**
	 * Stop playing.
	 * 
	 * @throws JMOPMusicbaseException 
	 */
	void stop() throws JMOPMusicbaseException;

	/**
	 * Pause playing.
	 */
	void pause();

	/**
	 * Resume playing.
	 */
	void resume();

	/**
	 * Seek to given time.
	 * 
	 * @param to
	 */
	void seek(Duration to);
	

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Go to (start playing) next track.
	 * 
	 * @throws JMOPMusicbaseException
	 */
	void toNext() throws JMOPMusicbaseException;

	/**
	 * Go to (start playing) previous track.
	 * 
	 * @throws JMOPMusicbaseException
	 */
	void toPrevious() throws JMOPMusicbaseException;

}