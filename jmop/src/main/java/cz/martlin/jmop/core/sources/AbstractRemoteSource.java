package cz.martlin.jmop.core.sources;

import java.net.URL;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface AbstractRemoteSource extends BaseSourceImpl {

	public URL urlOf(Track track) throws JMOPSourceException;
	
	@Override
	public Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException;

	public Track search(Bundle bundle, String query) throws JMOPSourceException;

	public Track getNextTrackOf(Track track) throws JMOPSourceException;

}
