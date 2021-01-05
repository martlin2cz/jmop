package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;

public interface EngineHandlers {
	@FunctionalInterface
	public static interface BeforeTrackStartedHandler {
		boolean beforeTrackStarted(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException;
	}

	@FunctionalInterface
	public static interface AfterTrackStartedHandler {
		void afterTrackStarted(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException;
	}

	@FunctionalInterface
	public interface BeforeTrackEndedHandler {
		void beforeTrackEnded(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException;
	}

	@FunctionalInterface
	public interface AfterTrackEndedHandler {
		void afterTrackEnded(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException;
	}

	@FunctionalInterface
	public interface OnPlaylistStartedHandler {
		void onPlaylistStarted(BasePlayerEngine engine, Playlist playlist) throws JMOPMusicbaseException;
	}

	@FunctionalInterface
	public interface OnPlaylistEndedHandler {
		void onPlaylistEnded(BasePlayerEngine engine, Playlist playlist) throws JMOPMusicbaseException;
	}

}
