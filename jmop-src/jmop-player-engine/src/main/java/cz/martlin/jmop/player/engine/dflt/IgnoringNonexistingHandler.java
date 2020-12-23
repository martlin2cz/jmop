package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.engines.withhandlers.BeforeTrackPlayedHandler;

public class IgnoringNonexistingHandler implements BeforeTrackPlayedHandler {

	@Override
	public boolean beforeTrackPlayed(Track track) {
		// TODO: if does not exist, do nothing
		return false;
	}

}
