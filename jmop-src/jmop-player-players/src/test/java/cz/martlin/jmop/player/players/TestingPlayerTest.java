package cz.martlin.jmop.player.players;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.TestingPlayer;

public class TestingPlayerTest extends AbstractPlayerTest {

	public TestingPlayerTest() {
		super();
	}

	@Override
	protected BasePlayer createPlayer() {
		return new TestingPlayer();
	}

	
	@Disabled
	@Test
	@Override
	public void testToFinish() throws JMOPMusicbaseException {
		// we haven't implemented this feature in the testing player.
		super.testToFinish();
	}
	
}
