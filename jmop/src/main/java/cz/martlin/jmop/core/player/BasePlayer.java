package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public interface BasePlayer {
	
	public void setHandler(TrackPlayedHandler handler);
	public boolean supports(TrackFileFormat format);
	
	public void startPlayling(Track track) throws JMOPSourceException;
	public void pause();
	public void resume();
	public void stop();
	
	
	
	
}
