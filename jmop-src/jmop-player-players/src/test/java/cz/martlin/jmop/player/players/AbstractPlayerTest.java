package cz.martlin.jmop.player.players;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.testdata.TestingDataCreator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public abstract class AbstractPlayerTest {

	private BasePlayer player;
	
	@RegisterExtension
	public TestingMusicdataExtension md = TestingMusicdataExtension.simple(getFormat());

	public AbstractPlayerTest() {
		super();
	}

	@BeforeEach
	public void beforeEach() {
		player = createPlayer();
	}

	protected abstract TrackFileFormat getFormat();

	protected abstract BasePlayer createPlayer();

	////////////////////////////////////////////////////////////////////////////

	@Test
	public void testValids()  {
		player.startPlaying(md.tmd.aerodynamic);
		check(md.tmd.aerodynamic, PlayerStatus.PLAYING);
		waitAsecond();

		player.pause();
		check(md.tmd.aerodynamic, PlayerStatus.PAUSED);
		waitAsecond();
		
		player.resume();
		check(md.tmd.aerodynamic, PlayerStatus.PLAYING);
		waitAsecond();
		
		player.stop();
		check(null, PlayerStatus.STOPPED);
		waitAsecond();
		
		player.startPlaying(md.tmd.allTheHellIsBreakingLoose);
		check(md.tmd.allTheHellIsBreakingLoose, PlayerStatus.PLAYING);
		waitAsecond();
		
		player.stop();
		check(null, PlayerStatus.STOPPED);
	}


	@Test
	public void testInvalids()  {
		assertThrows(IllegalStateException.class, () -> player.pause());
		assertThrows(IllegalStateException.class, () -> player.resume());
		assertThrows(IllegalStateException.class, () -> player.stop());
		waitAsecond();

		player.startPlaying(md.tmd.dontForgetToFly);
		assertThrows(IllegalStateException.class, () -> player.startPlaying(md.tmd.dontForgetToFly));
		assertThrows(IllegalStateException.class, () -> player.startPlaying(md.tmd.atZijiDuchove));
		assertThrows(IllegalStateException.class, () -> player.resume());
		waitAsecond();
		
		player.pause();
		assertThrows(IllegalStateException.class, () -> player.startPlaying(md.tmd.atZijiDuchove));
		assertThrows(IllegalStateException.class, () -> player.pause());
		waitAsecond();
		
		player.resume();
		assertThrows(IllegalStateException.class, () -> player.startPlaying(md.tmd.atZijiDuchove));
		assertThrows(IllegalStateException.class, () -> player.resume());
		waitAsecond();
			
		player.stop();
		assertThrows(IllegalStateException.class, () -> player.pause());
		assertThrows(IllegalStateException.class, () -> player.resume());
		assertThrows(IllegalStateException.class, () -> player.stop());
		waitAsecond();
	}

	@Test
	@Disabled
	public void testToFinish()  {
		player.startPlaying(md.tmd.atZijiDuchove);
		check(md.tmd.atZijiDuchove, PlayerStatus.PLAYING);
		
		try {
			// 20s should be enough to play the whole testing track
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			fail(e);
		}
		
		check(md.tmd.atZijiDuchove, PlayerStatus.NO_TRACK);
	}
	
	
////////////////////////////////////////////////////////////////////////////


	private void waitAsecond() {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			fail(e);
		}
	}
	
	private void check(Track expectedCurrentTrack, PlayerStatus expectedStatus)  {
		assertEquals(expectedCurrentTrack, player.actualTrack());
		assertEquals(expectedStatus, player.currentStatus());
	}
}
