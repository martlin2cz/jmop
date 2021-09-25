package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.utils.Lifecycle;
import cz.martlin.jmop.player.engine.dflt.BaseDefaultEngineConfig.NonExistingFileStrategy;
import cz.martlin.jmop.player.engine.dflt.handlers.IgnoringNonexistingHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.MarkingBundlePlayedHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.MarkingPlaylistAsPlayedHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.PlaylistAndBundleStartedHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.PlaylistAndBundleStartedHandler.OnBundleStartedHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.SkippingNonexistingHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.UpdatingPlaylistCurrentTrackHandler;
import cz.martlin.jmop.player.engine.dflt.handlers.UpdatingTrackMetadataHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.OnPlaylistEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.OnPlaylistStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineWithHandlers;
import cz.martlin.jmop.player.players.BasePlayer;

public class DefaultEngine extends EngineWithHandlers {

	private DefaultEngine(BasePlayer player, //
			OnPlaylistStartedHandler playlistStarted, OnPlaylistEndedHandler playlistEnded, //
			BeforeTrackStartedHandler beforeTrackStarted, AfterTrackStartedHandler afterTrackStarted, //
			BeforeTrackEndedHandler beforeTrackEnded, AfterTrackEndedHandler afterTrackEnded) {

		super(player, playlistStarted, playlistEnded, //
				beforeTrackStarted, afterTrackStarted, beforeTrackEnded, afterTrackEnded);
	}

	public static DefaultEngine create(BasePlayer player, BaseMusicbase musicbase, BaseDefaultEngineConfig config) {
		OnPlaylistStartedHandler playlistStarted = obtainPlaylistStartedHandler(musicbase);
		OnPlaylistEndedHandler playlistEnded = obtainPlaylistEndedHandler(musicbase);
		BeforeTrackStartedHandler beforeTrackStarted = obtainBeforeTrackStartedHandler(musicbase, config);
		AfterTrackStartedHandler afterTrackStarted = obtainAfterTrackStartedHandler(musicbase);
		BeforeTrackEndedHandler beforeTrackEnded = obtainBeforeTrackEndedHandler(musicbase, config);
		AfterTrackEndedHandler afterTrackEnded = obtainAfterTrackEndedHandler();

		return new DefaultEngine(player, playlistStarted, playlistEnded, beforeTrackStarted, afterTrackStarted, beforeTrackEnded, afterTrackEnded);
	}

	private static OnPlaylistStartedHandler obtainPlaylistStartedHandler(BaseMusicbase musicbase) {
		OnPlaylistStartedHandler playlistHandler = new MarkingPlaylistAsPlayedHandler(musicbase);
		OnBundleStartedHandler bundleHandler = new MarkingBundlePlayedHandler(musicbase);
		OnPlaylistStartedHandler handler = new PlaylistAndBundleStartedHandler(bundleHandler, playlistHandler);
		return handler;
	}

	private static OnPlaylistEndedHandler obtainPlaylistEndedHandler(Lifecycle musicbase) {
		return null;
	}

	private static BeforeTrackStartedHandler obtainBeforeTrackStartedHandler(BaseMusicbase musicbase,
			BaseDefaultEngineConfig config) {

		TracksLocator tracks = musicbase;
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
