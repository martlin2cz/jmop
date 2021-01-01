package cz.martlin.jmop.player.engine.engines.withhandlers;

import org.junit.jupiter.api.Nested;

import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackStartedHandler;
import cz.martlin.jmop.player.engine.testing.AbstractPlayerEngineTest;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.TestingPlayer;


class EnginesWithHandlersTest {

	@Nested
	public static class EngineWithNoHandlersTest extends AbstractPlayerEngineTest {

		@Override
		protected BasePlayerEngine createEngine() {
			BasePlayer player = new TestingPlayer();
			return new EngineWithHandlers(player, null, null, null, null);
		}
	}
	
	@Nested
	public static class EngineWithPrintingHandlersTest extends AbstractPlayerEngineTest {

		@Override
		protected BasePlayerEngine createEngine() {
			BasePlayer player = new TestingPlayer();
			
			BeforeTrackStartedHandler beforeStarted = (e, t) -> { 
				System.out.println("x.-.. Before started");
				return true;
			};
			AfterTrackStartedHandler afterStarted = (e, t) -> System.out.println(".x-.. After started");
			BeforeTrackEndedHandler beforeEnded = (e, t) -> System.out.println("..-x. Before ended");
			AfterTrackEndedHandler afterEnded = (e, t) -> System.out.println("..-.x After ended");
			
			return new EngineWithHandlers(player, beforeStarted, afterStarted, beforeEnded, afterEnded);
		}
	}


}