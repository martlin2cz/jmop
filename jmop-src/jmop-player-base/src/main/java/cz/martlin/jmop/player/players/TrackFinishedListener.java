package cz.martlin.jmop.player.players;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface TrackFinishedListener {
	public void trackOver(Track track) throws JMOPMusicbaseException;
}
