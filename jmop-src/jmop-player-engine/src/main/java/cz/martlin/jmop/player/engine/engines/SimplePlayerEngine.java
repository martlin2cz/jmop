package cz.martlin.jmop.player.engine.engines;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.engines.withhandlers.AbstractEngineWithPlayerAndRuntime;
import cz.martlin.jmop.player.players.BasePlayer;
import javafx.util.Duration;

/**
 * Player engine is just simply the JMOP core of core. It is responsible for
 * both track playing (player) and also for the choosing of track (playlister).
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
		return player.currentTime();
	}
	
	

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void play() throws JMOPMusicbaseException {
		Track track = runtime.current();
		player.startPlaying(track);
	}

	@Override
	public void play(int index) throws JMOPMusicbaseException {
		Track track = runtime.play(index);
		player.startPlaying(track);
	}

	@Override
	public void stop() {
		player.stop();
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
		player.startPlaying(track);
	}

	@Override
	public void toPrevious() throws JMOPMusicbaseException {
		Track track = runtime.toPrevious();
		player.startPlaying(track);
	}
	
	@Override
	public void trackOver(Track track) throws JMOPMusicbaseException {
		if (runtime.hasNextToPlay()) {
			toNext();
		}
	}
}
