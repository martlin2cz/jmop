package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public interface BaseSourceImpl {
	
	
	public Track getTrack(TrackIdentifier id) throws JMOPSourceException;
	
}