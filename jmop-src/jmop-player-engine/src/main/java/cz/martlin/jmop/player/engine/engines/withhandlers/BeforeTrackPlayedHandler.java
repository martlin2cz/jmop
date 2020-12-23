package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;

public interface BeforeTrackPlayedHandler {

	boolean beforeTrackPlayed(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException;

}
