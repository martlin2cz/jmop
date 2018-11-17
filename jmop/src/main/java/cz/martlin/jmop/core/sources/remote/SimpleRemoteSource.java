package cz.martlin.jmop.core.sources.remote;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;

/**
 * The general base remote source. Assumes working with some internal
 * representation.
 * 
 * <strong>If some of requests implemented here fails, it marks
 * {@link InternetConnectionStatus} as offline.</strong>
 * 
 * @author martin
 *
 * @param <GtRqt>
 *            request type of get track
 * @param <GtRst>
 *            response type of get track
 * @param <SeaRqt>
 *            request type of track search
 * @param <SeaRst>
 *            response type of track search
 * @param <GntRqt>
 *            request type of next track load
 * @param <GntRst>
 *            response type of next track load
 */
public abstract class SimpleRemoteSource<GtRqt, GtRst, SeaRqt, SeaRst, GntRqt, GntRst> implements AbstractRemoteSource {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final InternetConnectionStatus connection;

	public SimpleRemoteSource(InternetConnectionStatus connection) {
		super();
		this.connection = connection;
	}

	@Override
	public URL urlOf(Track track) throws JMOPSourceException {
		LOG.info("Generating url of track " + track.getTitle()); //$NON-NLS-1$
		String id = track.getIdentifier();
		String url = urlOfTrack(id);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new JMOPSourceException("URL of track corrupted", e); //$NON-NLS-1$
		}
	}

	/**
	 * Returns url of track as String.
	 * 
	 * @param id
	 * @return
	 */
	protected abstract String urlOfTrack(String id);

	///////////////////////////////////////////////////////////////////////////

	@Override
	public Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException {
		LOG.info("Loading track with id " + identifier); //$NON-NLS-1$

		return loadTrack(bundle, identifier);
	}

	@Override
	public Track search(Bundle bundle, String query) throws JMOPSourceException {
		LOG.info("Performing search of " + query); //$NON-NLS-1$

		Track track = loadSearchResult(bundle, query);
		return track;
	}

	@Override
	public Track getNextTrackOf(Track track) throws JMOPSourceException {
		LOG.info("Loading next track of " + track.getTitle()); //$NON-NLS-1$

		Bundle bundle = track.getBundle();
		String identifier = track.getIdentifier();

		Track next = loadNextOf(bundle, identifier);
		return next;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Loads track. In fact creates load request, executes it and converts
	 * response to Track.
	 * 
	 * @param bundle
	 * @param id
	 * @return
	 * @throws JMOPSourceException
	 */
	private Track loadTrack(Bundle bundle, String id) throws JMOPSourceException {
		try {
			GtRqt request = createLoadRequest(id);
			GtRst response = executeLoadRequest(request);
			return convertLoadResponse(bundle, response);
		} catch (Exception e) {
			connection.markOffline();

			throw new JMOPSourceException("Cannot load track", e); //$NON-NLS-1$
		}
	}

	/**
	 * Searches. In fact creates search request, executes it and converts
	 * response to Track.
	 * 
	 * @param bundle
	 * @param query
	 * @return
	 * @throws JMOPSourceException
	 */
	private Track loadSearchResult(Bundle bundle, String query) throws JMOPSourceException {
		try {
			SeaRqt request = createSearchRequest(query);
			SeaRst response = executeSearchRequest(request);
			return convertSearchResponse(bundle, response);
		} catch (Exception e) {
			connection.markOffline();

			throw new JMOPSourceException("Cannot load search result", e); //$NON-NLS-1$
		}
	}

	/**
	 * Loads next track. In fact creates "load next" request, executes it and
	 * converts response to Track.
	 * 
	 * @param bundle
	 * @param id
	 * @return
	 * @throws JMOPSourceException
	 */
	private Track loadNextOf(Bundle bundle, String id) throws JMOPSourceException {
		try {
			GntRqt request = createLoadNextRequest(id);
			GntRst response = executeLoadNextRequest(request);
			return convertLoadNextResponse(bundle, response);
		} catch (Exception e) {
			connection.markOffline();
			throw new JMOPSourceException("Cannot load next track", e); //$NON-NLS-1$
		}
	}
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Converts response of load track request into track.
	 * 
	 * @param bundle
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected abstract Track convertLoadResponse(Bundle bundle, GtRst response) throws Exception;

	/**
	 * Executes the load track request.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected abstract GtRst executeLoadRequest(GtRqt request) throws Exception;

	/**
	 * Creates the load track request.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected abstract GtRqt createLoadRequest(String id) throws Exception;

	/**
	 * Converts response of search request into track.
	 * 
	 * @param bundle
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected abstract Track convertSearchResponse(Bundle bundle, SeaRst response) throws Exception;

	/**
	 * Executes the search request.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected abstract SeaRst executeSearchRequest(SeaRqt request) throws Exception;

	/**
	 * Creates the search request.
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	protected abstract SeaRqt createSearchRequest(String query) throws Exception;

	/**
	 * Converts response of load next request into track.
	 * 
	 * @param bundle
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected abstract Track convertLoadNextResponse(Bundle bundle, GntRst response) throws Exception;

	/**
	 * Executes the load next request.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected abstract GntRst executeLoadNextRequest(GntRqt request) throws Exception;

	/**
	 * Creates the load next request.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected abstract GntRqt createLoadNextRequest(String id) throws Exception;

}