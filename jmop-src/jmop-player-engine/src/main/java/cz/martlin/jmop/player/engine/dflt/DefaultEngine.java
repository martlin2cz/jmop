package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
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

/**
 * The default player engine.
 * 
 * @author martin
 *
 */
public class DefaultEngine extends EngineWithHandlers {

	/**
	 * Creates.
	 * 
	 * @param player
	 * @param playlistStarted
	 * @param playlistEnded
	 * @param beforeTrackStarted
	 * @param afterTrackStarted
	 * @param beforeTrackEnded
	 * @param afterTrackEnded
	 */
	private DefaultEngine(BasePlayer player, //
			OnPlaylistStartedHandler playlistStarted, OnPlaylistEndedHandler playlistEnded, //
			BeforeTrackStartedHandler beforeTrackStarted, AfterTrackStartedHandler afterTrackStarted, //
			BeforeTrackEndedHandler beforeTrackEnded, AfterTrackEndedHandler afterTrackEnded) {

		super(player, playlistStarted, playlistEnded, //
				beforeTrackStarted, afterTrackStarted, beforeTrackEnded, afterTrackEnded);
	}

	/**
	 * Constructs.
	 * 
	 * @param player
	 * @param musicbase
	 * @param config
	 * @return
	 */
	public static DefaultEngine create(BasePlayer player, BaseMusicbase musicbase, BaseDefaultEngineConfig config) {
		OnPlaylistStartedHandler playlistStarted = obtainPlaylistStartedHandler(musicbase);
		OnPlaylistEndedHandler playlistEnded = obtainPlaylistEndedHandler(musicbase);
		BeforeTrackStartedHandler beforeTrackStarted = obtainBeforeTrackStartedHandler(musicbase, config);
		AfterTrackStartedHandler afterTrackStarted = obtainAfterTrackStartedHandler(musicbase);
		BeforeTrackEndedHandler beforeTrackEnded = obtainBeforeTrackEndedHandler(musicbase, config);
		AfterTrackEndedHandler afterTrackEnded = obtainAfterTrackEndedHandler();

		return new DefaultEngine(player, playlistStarted, playlistEnded, beforeTrackStarted, afterTrackStarted, beforeTrackEnded, afterTrackEnded);
	}

	/**
	 * Constructs the handler.
	 * The one marking all the things as played.
	 * 
	 * @param musicbase
	 * @return
	 */
	private static OnPlaylistStartedHandler obtainPlaylistStartedHandler(BaseMusicbase musicbase) {
		OnPlaylistStartedHandler playlistHandler = new MarkingPlaylistAsPlayedHandler(musicbase);
		OnBundleStartedHandler bundleHandler = new MarkingBundlePlayedHandler(musicbase);
		OnPlaylistStartedHandler handler = new PlaylistAndBundleStartedHandler(bundleHandler, playlistHandler);
		return handler;
	}

	/**
	 * Constructs the handler.
	 * 
	 * @param musicbase
	 * @return
	 */
	private static OnPlaylistEndedHandler obtainPlaylistEndedHandler(Lifecycle musicbase) {
		return null;
	}

	/**
	 * Constructs the handler.
	 * The one deciding what to do next.
	 * 
	 * 
	 * @param musicbase
	 * @param config
	 * @return
	 */
	private static BeforeTrackStartedHandler obtainBeforeTrackStartedHandler(BaseMusicbase musicbase,
			BaseDefaultEngineConfig config) {

		NonExistingFileStrategy strategy = config.getNonexistingFileStrategy();

		switch (strategy) {
		case SKIP:
			return new SkippingNonexistingHandler();
		case STOP:
			return new IgnoringNonexistingHandler();
		default:
			throw new IllegalArgumentException("Invalid strategy " + strategy);
		}
	}

	/**
	 * Constructs the handler.
	 * 
	 * @param musicbase
	 * @return
	 */
	private static AfterTrackStartedHandler obtainAfterTrackStartedHandler(BaseMusicbase musicbase) {
		AfterTrackStartedHandler afterStarted = new UpdatingPlaylistCurrentTrackHandler(musicbase);
		return afterStarted;
	}

	/**
	 * Constructs the handler.
	 * The one which marks played after some time.
	 * 
	 * 
	 * @param musicbase
	 * @param config
	 * @return
	 */
	private static BeforeTrackEndedHandler obtainBeforeTrackEndedHandler(BaseMusicbase musicbase,
			BaseDefaultEngineConfig config) {
		int markAsPlayedAfter = config.getMarkAsPlayedAfter();
		BeforeTrackEndedHandler beforeEnded = new UpdatingTrackMetadataHandler(musicbase, markAsPlayedAfter);
		return beforeEnded;
	}

	/**
	 * Constructs the handler.
	 * Null actually.
	 * 
	 * @return
	 */
	private static AfterTrackEndedHandler obtainAfterTrackEndedHandler() {
		AfterTrackEndedHandler afterEnded = null;
		return afterEnded;
	}
}
