package cz.martlin.jmop.player.players;

import cz.martlin.jmop.common.data.model.Track;
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
	 * Specifies the handler, which gets notified when the plaing of the track finishes.
	 * The player's responsibility is to do so.
	 * 
	 * @param listener
	 */
	public void specifyListener(TrackFinishedListener listener);

	///////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the current status of the player.
	 * 
	 * @return
	 */
	public PlayerStatus currentStatus();
	
	/**
	 * Returns the actual track o the player.
	 * 
	 * @return
	 */
	public Track actualTrack();
	
	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Starts playing given track. If playing another, might first stop and then
	 * start playing the given one.
	 * 
	 * @param track
	 * @
	 */
	public void startPlaying(Track track) ;

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
