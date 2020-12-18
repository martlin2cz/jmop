package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.player.base.player.BasePlayer;

public class TestingPlayerTest extends AbstractPlayerTest {

	public TestingPlayerTest() {
		super();
	}

	@Override
	protected BasePlayer createPlayer() {
		return new TestingPlayer();
	}

}
