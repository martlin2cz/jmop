package cz.martlin.jmop.player.engine.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.utils.TestingMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.LoggingPlayerEngine;
import cz.martlin.jmop.player.players.PlayerStatus;

public abstract class AbstractPlayerEngineTest {

	protected TestingMusicbase musicbase;
	
	protected abstract BasePlayerEngine createEngine();

	/////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testSome() throws JMOPMusicbaseException {
		BasePlayerEngine engine = log(createEngine());

		check(engine, null, null, PlayerStatus.NO_TRACK);
		waitAbit();

		engine.startPlayingPlaylist(musicbase.discovery);
		check(engine, musicbase.discovery, null, PlayerStatus.NO_TRACK);
		waitAbit();

		engine.play();
		check(engine, musicbase.discovery, musicbase.oneMoreTime, PlayerStatus.PLAYING);
		waitAbit();

		engine.pause();
		check(engine, musicbase.discovery, musicbase.oneMoreTime, PlayerStatus.PAUSED);
		waitAbit();

		engine.toNext();
		check(engine, musicbase.discovery, musicbase.aerodynamic, PlayerStatus.PLAYING);
		waitAbit();

		engine.stop();
		check(engine, musicbase.discovery, null, PlayerStatus.STOPPED);
		waitAbit();

		engine.play();
		check(engine, musicbase.discovery, musicbase.aerodynamic, PlayerStatus.PLAYING);
		waitAbit();

		engine.toNext();
		check(engine, musicbase.discovery, musicbase.verdisQuo, PlayerStatus.PLAYING);
		waitAbit();

		engine.toPrevious();
		check(engine, musicbase.discovery, musicbase.verdisQuo, PlayerStatus.PLAYING);
		waitAbit();

		engine.stop();
		check(engine, musicbase.discovery, null, PlayerStatus.STOPPED);
		waitAbit();
		
		engine.stopPlayingPlaylist();
		check(engine, null, null, PlayerStatus.STOPPED);
		waitAbit();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void check(BasePlayerEngine engine, Playlist expectedPlaylist, Track expectedTrack,
			PlayerStatus expectedStatus) {
		
		assertEquals(expectedPlaylist, engine.currentPlaylist());
		assertEquals(expectedTrack, engine.currentTrack());
		assertEquals(expectedStatus, engine.currentStatus());
	}

	private void waitAbit() {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			assumeTrue(e != null);
		}
	}

	private BasePlayerEngine log(BasePlayerEngine engine) {
		return new LoggingPlayerEngine(engine);
	}

}
