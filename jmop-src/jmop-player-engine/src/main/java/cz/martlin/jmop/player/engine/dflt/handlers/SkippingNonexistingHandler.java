package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.BasePlayerEngine;

public class SkippingNonexistingHandler extends AbstractTrackExistenceCheckingHandler {

	public SkippingNonexistingHandler() {
		super();
	}

	@Override
	protected void doBeforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track) {
		if (engine.hasNext()) {
			engine.toNext();
		}
	}

}
