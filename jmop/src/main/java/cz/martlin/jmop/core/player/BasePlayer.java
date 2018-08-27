package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.util.Duration;

public interface BasePlayer {
	
	public void setHandler(TrackPlayedHandler handler);
	@Deprecated
	public boolean supports(TrackFileFormat format);
	public TrackFileFormat getPlayableFormat();
	public ReadOnlyObjectProperty<Duration> currentTimeProperty();
	
	public void startPlayling(Track track) throws JMOPSourceException;
	public void pause();
	public void resume();
	public void stop();
	public void seek(Duration to);
	
	
}
