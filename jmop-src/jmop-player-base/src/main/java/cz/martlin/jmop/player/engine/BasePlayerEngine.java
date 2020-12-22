package cz.martlin.jmop.player.engine;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import javafx.util.Duration;

public interface BasePlayerEngine {

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Mark as playing given playist.
	 * 
	 * @param playlist
	 */
	void startPlayingPlaylist(Playlist playlist);

	/**
	 * Mark as not playing given playlist.
	 * 
	 * @param currentPlaylist
	 */
	void stopPlayingPlaylist(Playlist currentPlaylist);
	

	/**
	 * TODO doc 
	 */
	void playlistChanged();
	

	/**
	 * Play next track in the queque.
	 * 
	 * @throws JMOPMusicbaseException
	 */
	void playNext() throws JMOPMusicbaseException;

	/**
	 * Play the index-th track in the (current) playlist.
	 * 
	 * @param index
	 * @throws JMOPMusicbaseException
	 */
	void play(int index) throws JMOPMusicbaseException;

	/**
	 * Stop playing.
	 */
	void stop();

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