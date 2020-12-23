package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;

public class StoppingOnNonexistingHandler extends AbstractTrackExistenceCheckingHandler {

	public StoppingOnNonexistingHandler(TracksSource tracks) {
		super(tracks);
	}

	@Override
	protected void beforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException {
		engine.stop();
	}

}
