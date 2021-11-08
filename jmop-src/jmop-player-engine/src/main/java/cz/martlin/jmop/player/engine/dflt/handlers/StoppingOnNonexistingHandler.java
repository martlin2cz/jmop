package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.BasePlayerEngine;

public class StoppingOnNonexistingHandler extends AbstractTrackExistenceCheckingHandler {

	public StoppingOnNonexistingHandler() {
		super();
	}

	@Override
	protected void doBeforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track)  {
		engine.stop();
	}

}
