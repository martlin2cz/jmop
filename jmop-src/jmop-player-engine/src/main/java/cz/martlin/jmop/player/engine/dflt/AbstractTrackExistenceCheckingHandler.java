package cz.martlin.jmop.player.engine.dflt;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.withhandlers.BeforeTrackPlayedHandler;

public abstract class AbstractTrackExistenceCheckingHandler implements BeforeTrackPlayedHandler {

	private final TracksSource tracks;

	public AbstractTrackExistenceCheckingHandler(TracksSource tracks) {
		super();
		this.tracks = tracks;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean beforeTrackPlayed(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException {
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

	protected abstract void beforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException;

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean exists(Track track) throws JMOPMusicbaseException {
		try {
			File file = tracks.trackFile(track);
			return file.exists();
		} catch (JMOPMusicbaseException e) {
			throw new JMOPMusicbaseException("Cannot check existence of track file", e);
		}
	}

}