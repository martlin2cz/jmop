package cz.martlin.jmop.core.player.base.player;

/**
 * The current player status.
 * 
 * @author martin
 *
 */
public enum PlayerStatus {
	/**
	 * The indicator no track have been started to be played, 
	 * or, to oppose, the track plaing have ended.
	 * 
	 * The playing has to be started, but can be stopped.
	 */
	NO_TRACK, 
	
	/**
	 * Identifies the track is currently beeing played.
	 * 
	 * The playing can be paused or stopped, or finished.
	 */
	PLAYING, 
	
	/**
	 * Identifies the playing of the track was paused.
	 * 
	 * The playing can be resumed or stopped.
	 */
	PAUSED, 
	
	/**
	 * Identifies the plaing was stopped.
	 * 
	 * The plaing has to be started.
	 */
	STOPPED;
	
	/**
	 * Returns whether there is any track to be playing 
	 * (actually playing or just paused, but there is any track specified)
	 *  
	 * @return
	 */
	public boolean isPlayingTrack() {
		return this == PLAYING || this == PAUSED;
	}
	
	/**
	 * Returns whether there is NO track playing
	 * (actually when no track is specified, or the playing is stopped)
	 * 
	 * @return
	 */
	public boolean isNotPlayingTrack() {
		return this == NO_TRACK || this == STOPPED;
	}

	/**
	 * Returns whether the playing is running.
	 * @return
	 */
	public boolean isPlaying() {
		return this == PLAYING;
	}

	/**
	 * Returns whether the playing is NOT running.
	 * (i.e. stopped, paused or quite everything else)
	 * 
	 * @return
	 */
	public boolean isNotPlaying() {
		return this != PLAYING;
	}
	
	/**
	 * Returns whether the playing is paused.
	 * 
	 * @return
	 */
	public boolean isPaused() {
		return this == PAUSED;
	}

	/**
	 * Returns whether the playing is NOT paused.
	 * (i.e. playing, stopped or quite everything else)
	 * 
	 * @return
	 */
	public boolean isNotPaused() {
		return this != PAUSED;
	}

}
