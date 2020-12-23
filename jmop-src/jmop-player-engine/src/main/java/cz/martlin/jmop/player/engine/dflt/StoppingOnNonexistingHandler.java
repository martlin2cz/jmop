package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.engines.withhandlers.BeforeTrackPlayedHandler;

public class StoppingOnNonexistingHandler implements BeforeTrackPlayedHandler {

	@Override
	public boolean beforeTrackPlayed(Track track) {
		// TODO: if track not exists, stop.
		return false;
	}

}
