package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.engines.AbstractEngineWithPlayerAndRuntime;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackStartedHandler;
import cz.martlin.jmop.player.players.BasePlayer;
import javafx.util.Duration;

public class EngineWithHandlers extends AbstractEngineWithPlayerAndRuntime {

	private final BeforeTrackStartedHandler beforeStarted;
	private final AfterTrackStartedHandler afterStarted;
	private final BeforeTrackEndedHandler beforeEnded;
	private final AfterTrackEndedHandler afterEnded;

	public EngineWithHandlers(BasePlayer player, //
			BeforeTrackStartedHandler beforeStarted, AfterTrackStartedHandler afterStarted,
			BeforeTrackEndedHandler beforeEnded, AfterTrackEndedHandler afterEnded) {

		super(player);

		this.beforeStarted = beforeStarted;
		this.afterStarted = afterStarted;
		this.beforeEnded = beforeEnded;
		this.afterEnded = afterEnded;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track currentTrack() {
		return player.actualTrack();
	}

	@Override
	public Duration currentDuration() {
		return player.currentTime();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void play() throws JMOPMusicbaseException {
		Track track = runtime.current();
		stopAndPlayAnother(track);
	}

	@Override
	public void play(int index) throws JMOPMusicbaseException {
		Track track = runtime.play(index);
		stopAndPlayAnother(track);
	}

	@Override
	public void stop() throws JMOPMusicbaseException {
		stopTrack();
	}

	@Override
	public void pause() {
		player.pause();
	}

	@Override
	public void resume() {
		player.resume();
	}

	@Override
	public void seek(Duration to) {
		player.seek(to);
	}

	@Override
	public void toNext() throws JMOPMusicbaseException {
		Track track = runtime.toNext();
		stopAndPlayAnother(track);
	}

	@Override
	public void toPrevious() throws JMOPMusicbaseException {
		Track track = runtime.toPrevious();
		stopAndPlayAnother(track);
	}

	@Override
	public void trackOver(Track track) throws JMOPMusicbaseException {
		ifHasPlayNext();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected void playTrack(Track track) throws JMOPMusicbaseException {
		if (beforeStarted != null) {
			boolean canStart = beforeStarted.beforeTrackStarted(this, track);

			if (!canStart) {
				return;
			}
		}

		super.playTrack(track);

		if (afterStarted != null) {
			afterStarted.afterTrackStarted(this, track);
		}
	}

	@Override
	protected void stopTrack() throws JMOPMusicbaseException {
		Track track = runtime.current();
		
		if (beforeEnded != null) {
			beforeEnded.beforeTrackEnded(this, track);
		}

		player.stop();

		if (afterEnded != null) {
			afterEnded.afterTrackEnded(this, track);
		}
	}

}
