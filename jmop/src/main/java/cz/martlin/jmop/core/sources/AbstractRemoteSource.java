package cz.martlin.jmop.core.sources;

import java.net.URL;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public interface AbstractRemoteSource extends BaseSourceImpl {

	public URL urlOf(Track track) throws JMOPSourceException;
	
	@Override
	public Track getTrack(TrackIdentifier id) throws JMOPSourceException;

	public Track search(String query) throws JMOPSourceException;

	public Track getNextTrackOf(Track track) throws JMOPSourceException;

}
