package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public interface BaseRemoteSource extends BaseSourceImpl {

	@Override
	public Track getTrack(TrackIdentifier id) throws JMOPSourceException;

	public Track getNextOf(Track current) throws JMOPSourceException;



}
