package cz.martlin.jmop.core.player;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.player.base.player.BasePlayer;
import cz.martlin.jmop.core.player.base.player.PlayerStatus;
import javafx.util.Duration;

public class CachingPlayer extends AbstractPlayer {

	private final BaseErrorReporter reporter;
	private final BaseCachingManager cacher;
	private final BasePlayer player;

	public CachingPlayer(BaseErrorReporter reporter, BaseCachingManager cacher, BasePlayer player) {
		super();

		this.reporter = reporter;
		this.cacher = cacher;
		this.player = player;
	}

	///////////////////////////////////////////////////////////////////////////
	
	@Override
	public Duration currentTime() {
		if (cacher.isCaching(actualTrack())) {
			return Duration.ZERO;
		} else {
			return player.currentTime();
		}
	}

	@Override
	protected void doStartPlaying(Track track) throws JMOPMusicbaseException {
		if (cacher.isCached(track)) {
			player.startPlaying(track);
		} else {
			cacher.startCaching(track, this::onCached);
		}
	}

	@Override
	protected void doStopPlaying() {
		if (cacher.isCaching(actualTrack())) {
			// okay, just wait
		} else {
			player.stop();
		}
	}

	@Override
	protected void doPausePlaying() {
		if (cacher.isCaching(actualTrack())) {
			// okay, just wait
		} else {
			player.pause();
		}
	}

	@Override
	protected void doResumePlaying() {
		if (cacher.isCaching(actualTrack())) {
			// okay, just wait
		} else {
			player.resume();
		}
	}

	@Override
	protected void doSeek(Duration to) {
		if (cacher.isCaching(actualTrack())) {
			// okay, just wait
		} else {
			player.seek(to);
		}
	}
	
	@Override
	protected void doTrackFinished() {
		// okay
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void onCached(Track track) {
		try {
			PlayerStatus status = currentStatus();
			triggerTheDesiredOperation(status, track);
		} catch (JMOPMusicbaseException e) {
			reporter.report(e);
		}
	}

	private void triggerTheDesiredOperation(PlayerStatus status, Track track) throws JMOPMusicbaseException {
		
		switch (status) {
		case NO_TRACK:
			throw new IllegalStateException("This should never happen");
		case PAUSED:
			player.startPlaying(track);
			player.pause();
			break;
		case PLAYING:
			player.startPlaying(track);
			break;
		case STOPPED:
			player.stop();
			break;
		default:
			throw new IllegalArgumentException("Invalid status: " + status);
		}
		
	}


}
