package cz.martlin.jmop.sourcery.remote;

import java.net.URL;
import java.util.List;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Track;

/**
 * The querier.
 * @author martin
 *
 */
public interface BaseRemoteSourceQuerier {

	/**
	 * Searches the tracks for the given query.
	 * @param query
	 * @return
	 * @throws JMOPSourceryException
	 */
	List<TrackData> search(String query) throws JMOPSourceryException;

	/**
	 * Loads next of the given track.
	 * @param track
	 * @return
	 * @throws JMOPSourceryException
	 */
	TrackData loadNext(Track track) throws JMOPSourceryException;

	/**
	 * Obtains URL of the track.
	 * @param track
	 * @return
	 */
	URL urlOfTrack(Track track);
}
