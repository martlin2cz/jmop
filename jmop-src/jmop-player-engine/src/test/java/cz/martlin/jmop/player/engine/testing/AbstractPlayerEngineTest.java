package cz.martlin.jmop.player.engine.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.SimpleTestingMusicdata;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.LoggingPlayerEngine;
import cz.martlin.jmop.player.players.PlayerStatus;

public abstract class AbstractPlayerEngineTest {

	protected AbstractTestingMusicdata tmb;
	
	public AbstractPlayerEngineTest() {
	}
	
	@BeforeEach
	public void before() {
		tmb = new SimpleTestingMusicdata(TrackFileFormat.WAV);
	}
	
	protected abstract BasePlayerEngine createEngine();

	/////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testSome()  {
		BasePlayerEngine engine = log(createEngine());

		check(engine, null, null, PlayerStatus.NO_TRACK);
		waitAbit();

		engine.startPlayingPlaylist(tmb.discovery);
		check(engine, tmb.discovery, null, PlayerStatus.NO_TRACK);
		waitAbit();

		engine.play();
		check(engine, tmb.discovery, tmb.oneMoreTime, PlayerStatus.PLAYING);
		waitAbit();

		engine.pause();
		check(engine, tmb.discovery, tmb.oneMoreTime, PlayerStatus.PAUSED);
		waitAbit();

		engine.toNext();
		check(engine, tmb.discovery, tmb.aerodynamic, PlayerStatus.PLAYING);
		waitAbit();

		engine.stop();
		check(engine, tmb.discovery, null, PlayerStatus.STOPPED);
		waitAbit();

		engine.play();
		check(engine, tmb.discovery, tmb.aerodynamic, PlayerStatus.PLAYING);
		waitAbit();

		engine.toNext();
		check(engine, tmb.discovery, tmb.verdisQuo, PlayerStatus.PLAYING);
		waitAbit();

		engine.toPrevious();
		check(engine, tmb.discovery, tmb.aerodynamic, PlayerStatus.PLAYING);
		waitAbit();

		engine.stop();
		check(engine, tmb.discovery, null, PlayerStatus.STOPPED);
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
