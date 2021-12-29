package cz.martlin.jmop.player.engine.engines;

import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.testing.AbstractPlayerEngineTest;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.TestingPlayer;

class SimplePlayerEngineTest extends AbstractPlayerEngineTest {

	@Override
	protected BasePlayerEngine createEngine() {
		BasePlayer player = new TestingPlayer();
		return new SimplePlayerEngine(player);
	}


}
