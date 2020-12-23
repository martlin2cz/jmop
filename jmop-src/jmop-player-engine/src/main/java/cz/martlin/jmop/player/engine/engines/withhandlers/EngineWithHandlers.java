package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.players.BasePlayer;
import javafx.util.Duration;

public class EngineWithHandlers extends AbstractEngineWithPlayerAndRuntime {

	private BeforeTrackPlayedHandler before;
	private AfterTrackPlayedHandler after;

	public EngineWithHandlers(BasePlayer player) {
		super(player);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void play() throws JMOPMusicbaseException {
		Track track = runtime.current();
		playTrack(track);
	}

	@Override
	public void play(int index) throws JMOPMusicbaseException {
		Track track = runtime.play(index);
		playTrack(track);
	}

	@Override
	public void stop() {
		Track track = runtime.current();
		stopTrack(track);
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
		playTrack(track);
	}

	@Override
	public void toPrevious() throws JMOPMusicbaseException {
		Track track = runtime.toPrevious();
		playTrack(track);
	}

	@Override
	public void trackOver(Track track) throws JMOPMusicbaseException {
		if (runtime.hasNextToPlay()) {
			toNext();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void playTrack(Track track) throws JMOPMusicbaseException {
		if (player.currentStatus().isPlayingTrack()) {
			stopTrack(track);
		}

		if (before.beforeTrackPlayed(track)) {
			player.startPlaying(track);
		}
	}
	

	private void stopTrack(Track track) {
		after.beforeTrackEnded(track);
		player.stop();
	}


}
