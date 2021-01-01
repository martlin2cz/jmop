package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.player.engine.dflt.BaseDefaultEngineConfig.NonExistingFileStrategy;
import cz.martlin.jmop.player.engine.dflt.handlers.IgnoringNonexistingHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.SkippingNonexistingHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.UpdatingPlaylistCurrentTrackHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.UpdatingTrackMetadataHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineWithHandlers;
import cz.martlin.jmop.player.players.BasePlayer;

public class DefaultEngine extends EngineWithHandlers {

	private DefaultEngine(BasePlayer player, BeforeTrackStartedHandler beforeStarted,
			AfterTrackStartedHandler afterStarted, BeforeTrackEndedHandler beforeEnded,
			AfterTrackEndedHandler afterEnded) {
		super(player, beforeStarted, afterStarted, beforeEnded, afterEnded);
	}

	public static DefaultEngine create(BasePlayer player, BaseMusicbase musicbase, BaseDefaultEngineConfig config) {
		BeforeTrackStartedHandler beforeStarted = obtainBeforeTrackStartedHandler(musicbase, config);
		AfterTrackStartedHandler afterStarted = obtainAfterTrackStartedHandler(musicbase);
		BeforeTrackEndedHandler beforeEnded = obtainBeforeTrackEndedHandler(musicbase, config);
		AfterTrackEndedHandler afterEnded = obtainAfterTrackEndedHandler();

		return new DefaultEngine(player, beforeStarted, afterStarted, beforeEnded, afterEnded);
	}

	private static BeforeTrackStartedHandler obtainBeforeTrackStartedHandler(BaseMusicbase musicbase,
			BaseDefaultEngineConfig config) {

		TracksSource tracks = musicbase;
		NonExistingFileStrategy strategy = config.getNonexistingFileStrategy();

		switch (strategy) {
		case SKIP:
			return new SkippingNonexistingHandler(tracks);
		case STOP:
			return new IgnoringNonexistingHandler(tracks);
		default:
			throw new IllegalArgumentException("Invalid strategy " + strategy);
		}
	}

	private static AfterTrackStartedHandler obtainAfterTrackStartedHandler(BaseMusicbase musicbase) {
		AfterTrackStartedHandler afterStarted = new UpdatingPlaylistCurrentTrackHandler(musicbase);
		return afterStarted;
	}

	private static BeforeTrackEndedHandler obtainBeforeTrackEndedHandler(BaseMusicbase musicbase,
			BaseDefaultEngineConfig config) {
		int markAsPlayedAfter = config.getMarkAsPlayedAfter();
		BeforeTrackEndedHandler beforeEnded = new UpdatingTrackMetadataHandler(musicbase, markAsPlayedAfter);
		return beforeEnded;
	}

	private static AfterTrackEndedHandler obtainAfterTrackEndedHandler() {
		AfterTrackEndedHandler afterEnded = null;
		return afterEnded;
	}
}
