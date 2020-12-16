package cz.martlin.jmop.core.player.base.player;

/**
 * The current player status.
 * 
 * @author martin
 *
 */
public enum PlayerStatus {
	NO_TRACK, PLAYING, PAUSED, STOPPED;
	
	public boolean isPlayingTrack() {
		return this == PLAYING || this == PAUSED;
	}
	
	public boolean isNotPlayingTrack() {
		return this == NO_TRACK || this == STOPPED;
	}

	public boolean isPlaying() {
		return this == PLAYING;
	}

	public boolean isPaused() {
		return this == PAUSED;
	}
}
