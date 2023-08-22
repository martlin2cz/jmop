package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.PlayerEngineWrapper;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

/**
 * The player current status querier fascade.
 *  
 * @author martin
 *
 */
public class JMOPStatus {

	private final PlayerEngineWrapper engine;

	public JMOPStatus(BasePlayerEngine engine) {
		super();
		this.engine = new PlayerEngineWrapper(engine);
	}

	/**
	 * Returns the curerntly played bundle. Null if none.
	 * @return
	 */
	public Bundle currentBundle() {
		return engine.currentBundle();
	}

	/**
	 * Returns the currently played playlist. Null if none.
	 * @return
	 */
	public Playlist currentPlaylist() {
		return engine.currentPlaylist();
	}

	/**
	 * Returns the currently played track. Null if none.
	 * @return
	 */
	public Track currentTrack() {
		return engine.currentTrack();
	}

	/**
	 * Returns the current duration of the played track. Null if not playing.
	 * @return
	 */
	public Duration currentDuration() {
		return engine.currentDuration();
	}
	
	/**
	 * Use all the {@link #isPlaying()} and so methods.
	 * @return
	 */
	@Deprecated
	public PlayerStatus currentStatus() {
		return engine.currentStatus();
	}

	/**
	 * Is currently beeing played some playlist?
	 * @return
	 */
	public boolean isPlayingSomePlaylist() {
		return engine.currentPlaylist() != null;
	}
	
	/**
	 * Is currently beeing played some track?
	 * @return
	 */
	public boolean isPlayingSomeTrack() {
		return engine.currentStatus().isPlayingTrack();
	}
	
	/**
	 * Is the player playing? (not paused?)
	 * @return
	 */
	public boolean isPlaying() {
		return engine.currentStatus().isPlaying();
	}

	/**
	 * Is the player paused?
	 * @return
	 */
	public boolean isPaused() {
		return engine.currentStatus().isPaused();
	}

	/**
	 * Is the player stopped?
	 * @return
	 */
	public boolean isStopped() {
		return engine.currentStatus().isPlaying();
	}

	/**
	 * Has previous current playlist track? Can play previous?
	 * @return
	 */
	public boolean hasPrevious() {
		return engine.hasPrevious();
	}

	/**
	 * Has the curernt playlist next track? Can play next?
	 * @return
	 */
	public boolean hasNext() {
		return engine.hasNext();
	}
	

}
