package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;

public interface AbstractPlayer {
	public void startPlayling(Track track);
	public void pause();
	public void resume();
	public void stop();
}
