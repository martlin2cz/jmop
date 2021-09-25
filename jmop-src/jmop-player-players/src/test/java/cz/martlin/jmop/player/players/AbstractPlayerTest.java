package cz.martlin.jmop.player.players;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.testing.testdata.TestingDataCreator;

public abstract class AbstractPlayerTest {

	private BasePlayer player;
	private Track fooTrack;
	private Track barTrack;

	public AbstractPlayerTest() {
		super();
	}

	@BeforeEach
	public void beforeEach() {
		player = createPlayer();

		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();
		Bundle bundle = TestingDataCreator.bundle(musicbase);
		fooTrack = TestingDataCreator.track(musicbase, bundle, "foo", true);
		barTrack = TestingDataCreator.track(musicbase, bundle, "bar", true);
	}

	protected abstract BasePlayer createPlayer();

	////////////////////////////////////////////////////////////////////////////

	@Test
	public void testValids()  {
		player.startPlaying(fooTrack);
		check(fooTrack, PlayerStatus.PLAYING);
		waitAsecond();

		player.pause();
		check(fooTrack, PlayerStatus.PAUSED);
		waitAsecond();
		
		player.resume();
		check(fooTrack, PlayerStatus.PLAYING);
		waitAsecond();
		
		player.stop();
		check(null, PlayerStatus.STOPPED);
		waitAsecond();
		
		player.startPlaying(barTrack);
		check(barTrack, PlayerStatus.PLAYING);
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

		player.startPlaying(fooTrack);
		assertThrows(IllegalStateException.class, () -> player.startPlaying(barTrack));
		assertThrows(IllegalStateException.class, () -> player.resume());
		waitAsecond();
		
		player.pause();
		assertThrows(IllegalStateException.class, () -> player.startPlaying(barTrack));
		assertThrows(IllegalStateException.class, () -> player.pause());
		waitAsecond();
		
		player.resume();
		assertThrows(IllegalStateException.class, () -> player.startPlaying(barTrack));
		assertThrows(IllegalStateException.class, () -> player.resume());
		waitAsecond();
			
		player.stop();
		assertThrows(IllegalStateException.class, () -> player.pause());
		assertThrows(IllegalStateException.class, () -> player.resume());
		assertThrows(IllegalStateException.class, () -> player.stop());
		waitAsecond();
	}

	@Test
	public void testToFinish()  {
		player.startPlaying(fooTrack);
		check(fooTrack, PlayerStatus.PLAYING);
		
		try {
			// 20s should be enough to play the whole testing track
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			fail(e);
		}
		
		check(fooTrack, PlayerStatus.NO_TRACK);
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
