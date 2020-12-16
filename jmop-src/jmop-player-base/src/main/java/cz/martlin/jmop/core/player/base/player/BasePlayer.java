package cz.martlin.jmop.core.player.base.player;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
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
	 * Returns the current status of the player.
	 * 
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public PlayerStatus currentStatus() throws JMOPMusicbaseException;
	
	/**
	 * Returns the actual track o the player.
	 * 
	 * @return
	 * @throws JMOPMusicbaseException
	 */
	public Track actualTrack() throws JMOPMusicbaseException;
	
	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Starts playing given track. If playing another, might first stop and then
	 * start playing the given one.
	 * 
	 * @param track
	 * @throws JMOPMusicbaseException
	 */
	public void startPlaying(Track track) throws JMOPMusicbaseException;

	/**
	 * Stops the player. If already stopped might do nothing.
	 */
	public void stop();

	///////////////////////////////////////////////////////////////////////////////////

	/**
	 * Pauses plaing. If alread paused or not playing might do nothing.
	 */
	public void pause();

	/**
	 * Resumes plaing. If not paused or not playing might do nothing.
	 */
	public void resume();

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
