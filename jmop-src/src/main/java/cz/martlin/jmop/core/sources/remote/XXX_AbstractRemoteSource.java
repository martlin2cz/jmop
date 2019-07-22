package cz.martlin.jmop.core.sources.remote;

import java.net.URL;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.BaseSourceImpl;

/**
 * Base remote source. Specifies methods for loading of track by ID, searching
 * of track by keywords and inferring next track.
 * 
 * @author martin
 *
 */
@Deprecated
public interface XXX_AbstractRemoteSource extends BaseSourceImpl {

	/**
	 * Returns the URL to download the given track.
	 * 
	 * @param track
	 * @return
	 * @throws JMOPSourceException
	 */
	public URL urlOf(Track track) throws JMOPSourceException;

	@Override
	public Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException;

	/**
	 * Performs search for the given query. Returns first matching track.
	 * 
	 * @param bundle
	 * @param query
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track search(Bundle bundle, String query) throws JMOPSourceException;

	/**
	 * Loads the most simillar track ("which shall be nextly played after") to
	 * the given track.
	 * 
	 * @param track
	 * @return
	 * @throws JMOPSourceException
	 */
	public Track getNextTrackOf(Track track) throws JMOPSourceException;

}
