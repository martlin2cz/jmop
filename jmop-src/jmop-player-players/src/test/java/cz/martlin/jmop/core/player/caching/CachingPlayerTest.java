package cz.martlin.jmop.core.player.caching;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.player.AbstractPlayerTest;
import cz.martlin.jmop.core.player.BaseCachingManager;
import cz.martlin.jmop.core.player.TestingPlayer;
import cz.martlin.jmop.core.player.base.player.BasePlayer;
import cz.martlin.jmop.core.player.base.player.PlayerStatus;

class CachingPlayerTest extends AbstractPlayerTest {

	private static final int CACHING_TIME = 5;

	@Override
	protected BasePlayer createPlayer() {
		BaseErrorReporter reporter = new SimpleErrorReporter();
		BasePlayer player = new TestingPlayer();
		BaseCachingManager cacher = new TestingCachingManager(reporter, CACHING_TIME);
		return new CachingPlayer(reporter, cacher, player);
	}

	@Disabled
	@Override
	public void testToFinish() throws JMOPMusicbaseException {
		// skipping, not implemented in the TestingPlayer
	}

	@Test
	void testCachingSimply() throws JMOPMusicbaseException, InterruptedException {
		BasePlayer inner = ((CachingPlayer) player).getPlayer();
		
		player.startPlaying(fooTrack);
		
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.PLAYING);
		assertEquals(inner.currentStatus(), PlayerStatus.NO_TRACK);
		
		TimeUnit.SECONDS.sleep(CACHING_TIME + 1);
		assertEquals(player.currentStatus(), PlayerStatus.PLAYING);
		assertEquals(inner.currentStatus(), PlayerStatus.PLAYING);
		
		player.stop();
	}
	
	@Test
	void testCachingWithActionsBeforeCached() throws JMOPMusicbaseException, InterruptedException {
		BasePlayer inner = ((CachingPlayer) player).getPlayer();
		
		player.startPlaying(fooTrack);
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.PLAYING);
		assertEquals(inner.currentStatus(), PlayerStatus.NO_TRACK);
		
		player.pause();
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.PAUSED);
		assertEquals(inner.currentStatus(), PlayerStatus.NO_TRACK);
		
		player.resume();
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.PLAYING);
		assertEquals(inner.currentStatus(), PlayerStatus.NO_TRACK);
		
		player.stop();
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.STOPPED);
		assertEquals(inner.currentStatus(), PlayerStatus.NO_TRACK);
		
		player.startPlaying(barTrack);
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.PLAYING);
		assertEquals(inner.currentStatus(), PlayerStatus.NO_TRACK);
		
		player.stop();
	}
	
	@Test
	void testCachingWithActionsAfterCached() throws JMOPMusicbaseException, InterruptedException {
		BasePlayer inner = ((CachingPlayer) player).getPlayer();
		
		player.startPlaying(fooTrack);

		TimeUnit.SECONDS.sleep(CACHING_TIME + 1);
		assertEquals(player.currentStatus(), PlayerStatus.PLAYING);
		assertEquals(inner.currentStatus(), PlayerStatus.PLAYING);
		
		player.pause();
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.PAUSED);
		assertEquals(inner.currentStatus(), PlayerStatus.PAUSED);
		
		player.resume();
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.PLAYING);
		assertEquals(inner.currentStatus(), PlayerStatus.PLAYING);
		
		player.stop();
		waitAsecond();
		assertEquals(player.currentStatus(), PlayerStatus.STOPPED);
		assertEquals(inner.currentStatus(), PlayerStatus.STOPPED);
	}

}
