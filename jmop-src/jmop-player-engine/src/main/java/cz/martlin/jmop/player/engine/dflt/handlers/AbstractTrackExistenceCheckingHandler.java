package cz.martlin.jmop.player.engine.dflt.handlers;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackStartedHandler;

public abstract class AbstractTrackExistenceCheckingHandler implements BeforeTrackStartedHandler {

	private final TracksSource tracks;

	public AbstractTrackExistenceCheckingHandler(TracksSource tracks) {
		super();
		this.tracks = tracks;
	}

	/////////////////////////////////////////////////////////////////////////////////////

		@Override
	public boolean beforeTrackStarted(BasePlayerEngine engine, Track track)  {
		if (exists(track)) {
			return beforeExistingTrackPlayed(engine, track);
		} else {
			beforeNonexistingTrackPlayed(engine, track);
			return false;
		}
	}

	protected boolean beforeExistingTrackPlayed(BasePlayerEngine engine, Track track) {
		return true;
	}

	protected abstract void beforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track) ;

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean exists(Track track)  {
		File file = tracks.trackFile(track);
		return file.exists();
	}

}