package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.BasePlayerEngine;

/**
 * The track/plalyist/bundle playing started/ended handlers.
 * 
 * @author martin
 *
 */
public interface EngineHandlers {
	/**
	 * The handler of "track is about to be played".
	 * 
	 * @author martin
	 *
	 */
	@FunctionalInterface
	public static interface BeforeTrackStartedHandler {
		/**
		 * Reports track is about to be played. IF returned false, it may not be played.
		 * 
		 * @param engine
		 * @param track
		 * @return
		 */
		boolean beforeTrackStarted(BasePlayerEngine engine, Track track);
	}

	/**
	 * The handle of "track playing started".
	 * 
	 * @author martin
	 *
	 */
	@FunctionalInterface
	public static interface AfterTrackStartedHandler {
		void afterTrackStarted(BasePlayerEngine engine, Track track);
	}

	/**
	 * The handler "the track playing is going to end".
	 * 
	 * @author martin
	 *
	 */
	@FunctionalInterface
	public interface BeforeTrackEndedHandler {
		void beforeTrackEnded(BasePlayerEngine engine, Track track);
	}

	/**
	 * The handler of "the playing ended".
	 * 
	 * @author martin
	 *
	 */
	@FunctionalInterface
	public interface AfterTrackEndedHandler {
		void afterTrackEnded(BasePlayerEngine engine, Track track);
	}

	/**
	 * The handler of "playlist playing started".
	 * 
	 * @author martin
	 *
	 */
	@FunctionalInterface
	public interface OnPlaylistStartedHandler {
		void onPlaylistStarted(BasePlayerEngine engine, Playlist playlist);
	}

	/**
	 * The handler of "playlist playing ended".
	 * 
	 * @author martin
	 *
	 */
	@FunctionalInterface
	public interface OnPlaylistEndedHandler {
		void onPlaylistEnded(BasePlayerEngine engine, Playlist playlist);
	}

}
