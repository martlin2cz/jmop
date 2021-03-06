package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;

/**
 * The base abstract player. The player can play track, and then is played. The
 * playing can be paused and then resumed back. By stopping playing is marked as
 * no track played. Player also provides current time - and also seek to
 * particullar time.
 * 
 * 
 * @see AbstractPlayer
 * @author martin
 *
 */
public interface BasePlayer extends ObservableValue<BasePlayer> {

	/**
	 * Returns file format which this player supports.
	 * 
	 * @return
	 */
	public TrackFileFormat getPlayableFormat();

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Returns currently played track. If stopped returns null.
	 * 
	 * @return
	 */
	public Track getPlayedTrack();

	/**
	 * Returns true if the track to be played have been whole played.
	 * 
	 * @return
	 */
	public boolean isPlayOver();

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Starts playing given track. If playing another, might first stop and then
	 * start playing the given one.
	 * 
	 * @param track
	 * @throws JMOPSourceException
	 */
	public void startPlaying(Track track) throws JMOPSourceException;

	/**
	 * Stops the player. If already stopped might do nothing.
	 */
	public void stop();

	/**
	 * Returns true whether is stopped.
	 * 
	 * @return
	 */
	public boolean isStopped();

	///////////////////////////////////////////////////////////////////////////////////

	/**
	 * Pauses plaing. If alread paused or not playing might do nothing.
	 */
	public void pause();

	/**
	 * Resumes plaing. If not paused or not playing might do nothing.
	 */
	public void resume();

	/**
	 * Returns true if paused.
	 * 
	 * @return
	 */
	public boolean isPaused();

	///////////////////////////////////////////////////////////////////////////////////
	/**
	 * Seeks (jumps) to given time.
	 * 
	 * @param to
	 */
	public void seek(Duration to);

	/**
	 * Returns the current time.
	 * 
	 * @return
	 */
	public Duration currentTime();

}
