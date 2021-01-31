package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.PlayerEngineWrapper;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

public class JMOPStatus {

	private final PlayerEngineWrapper engine;

	public JMOPStatus(BasePlayerEngine engine) {
		super();
		this.engine = new PlayerEngineWrapper(engine);
	}

	public Bundle currentBundle() {
		return engine.currentBundle();
	}

	public Playlist currentPlaylist() {
		return engine.currentPlaylist();
	}

	public Track currentTrack() {
		return engine.currentTrack();
	}

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

	public boolean isPlayingSomePlaylist() {
		return engine.currentPlaylist() != null;
	}
	
	public boolean isPlayingSomeTrack() {
		return engine.currentStatus().isPlayingTrack();
	}
	
	public boolean isPlaying() {
		return engine.currentStatus().isPlaying();
	}

	public boolean isPaused() {
		return engine.currentStatus().isPaused();
	}

	public boolean isStopped() {
		return engine.currentStatus().isPlaying();
	}

	public boolean hasPrevious() {
		return engine.hasPrevious();
	}

	public boolean hasNext() {
		return engine.hasNext();
	}

	

}
