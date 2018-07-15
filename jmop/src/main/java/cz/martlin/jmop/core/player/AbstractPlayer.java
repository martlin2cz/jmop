package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;

public interface AbstractPlayer {
	public void play(Track track);
	public void stop();
	//TODO pause and adjust volume?
}
