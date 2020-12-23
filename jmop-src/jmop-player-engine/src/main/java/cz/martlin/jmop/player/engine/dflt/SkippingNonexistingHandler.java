package cz.martlin.jmop.player.engine.dflt;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.engines.withhandlers.BeforeTrackPlayedHandler;

public class SkippingNonexistingHandler implements BeforeTrackPlayedHandler {

	@Override
	public boolean beforeTrackPlayed(Track track) {
		// TODO: if does not exist, trigger #toNext on the provided engine
		return false;
	}

}
