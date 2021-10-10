package cz.martlin.jmop.player.engine.dflt.handlers;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackStartedHandler;

public abstract class AbstractTrackExistenceCheckingHandler implements BeforeTrackStartedHandler {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	private final TracksLocator tracks;

	public AbstractTrackExistenceCheckingHandler(TracksLocator tracks) {
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

	protected void beforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track) {
		File file = tracks.trackFile(track);
		LOG.warn("The track " + track.getTitle() + " file (" + file + ") does not exist");
		//TODO or use some erorr reporter instead?
		
		doBeforeNonexistingTrackPlayed(engine, track);
	}

	protected abstract void doBeforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track);

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean exists(Track track)  {
		File file = tracks.trackFile(track);
		return file.exists();
	}

}