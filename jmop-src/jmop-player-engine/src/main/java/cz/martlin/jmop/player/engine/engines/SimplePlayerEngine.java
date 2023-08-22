package cz.martlin.jmop.player.engine.engines;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.players.BasePlayer;
import javafx.util.Duration;

/**
 * The simple player engine. Just plays by the given player.
 * No other interactions happening.
 * 
 * @author martin
 *
 */
public class SimplePlayerEngine extends AbstractEngineWithPlayerAndRuntime {

	public SimplePlayerEngine(BasePlayer player) {
		super(player);
	}

	@Override
	public Track currentTrack() {
		return player.actualTrack();
	}

	@Override
	public Duration currentDuration() {
		if (player.currentStatus().isPlayingTrack()) {
			return player.currentTime();
		} else {
			return null;
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void play()  {
		Track track = runtime.current();
		stopAndPlayAnother(track);
	}

	@Override
	public void play(TrackIndex index)  {
		Track track = runtime.play(index);
		stopAndPlayAnother(track);
	}

	@Override
	public void stop()  {
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
	public void toNext()  {
		Track track = runtime.toNext();
		stopAndPlayAnother(track);
	}

	@Override
	public void toPrevious()  {
		Track track = runtime.toPrevious();
		stopAndPlayAnother(track);
	}
	
	@Override
	public void trackOver(Track track)  {
		ifHasPlayNext();
	}

}
