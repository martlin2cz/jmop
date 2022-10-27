package cz.martlin.jmop.sourcery.remote;

import java.net.URL;
import java.util.List;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

public interface BaseRemoteSourceQuerier {

	List<TrackData> search(String query) throws JMOPSourceryException;

	TrackData loadNext(Track track) throws JMOPSourceryException;

	URL urlOfTrack(Track track);
}
