package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.player.engine.BasePlayerEngine;

public class IgnoringNonexistingHandler extends AbstractTrackExistenceCheckingHandler {

	public IgnoringNonexistingHandler(TracksSource tracks) {
		super(tracks);
	}

	@Override
	protected void beforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track) {
		// ignore, like I said
	}
}
