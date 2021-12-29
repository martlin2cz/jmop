package cz.martlin.jmop.player.engine.engines;

import org.junit.jupiter.api.Tag;

import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.testing.AbstractPlayerEngineTest;
import cz.martlin.jmop.player.players.AplayPlayer;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.JavaFXMediaPlayer;

@Tag(value = "IDE_ONLY")
class SimplePlayerEngineWithJavaFxPlayerTest extends AbstractPlayerEngineTest {

	@Override
	protected BasePlayerEngine createEngine() {
		BasePlayer player = new JavaFXMediaPlayer();
		return new SimplePlayerEngine(player);
	}


}
