package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Track;

public interface BeforeTrackPlayedHandler {

	boolean beforeTrackPlayed(Track track);

}
