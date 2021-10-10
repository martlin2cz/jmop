package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;

public class SkippingNonexistingHandler extends AbstractTrackExistenceCheckingHandler {

	public SkippingNonexistingHandler(TracksLocator tracks) {
		super(tracks);
	}

	@Override
	protected void doBeforeNonexistingTrackPlayed(BasePlayerEngine engine, Track track)  {
		if (engine.hasNext()) {
			engine.toNext();
		}
	}

}
