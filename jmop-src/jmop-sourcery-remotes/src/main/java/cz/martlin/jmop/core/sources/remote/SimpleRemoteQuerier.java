package cz.martlin.jmop.core.sources.remote;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

/**
 * The general base remote source querier. Assumes working with some internal
 * representation.
 * 
 * <strong>If some of requests implemented here fails, it marks
 * {@link InternetConnectionStatus} as offline.</strong>
 * 
 * @author martin
 *
 * @param <GtRqt>  request type of get track
 * @param <GtRst>  response type of get track
 * @param <SeaRqt> request type of track search
 * @param <SeaRst> response type of track search
 * @param <GntRqt> request type of next track load
 * @param <GntRst> response type of next track load
 */
public abstract class SimpleRemoteQuerier<GtRqt, GtRst, SeaRqt, SeaRst, GntRqt, GntRst> extends AbstractRemoteQuerier {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final InternetConnectionStatus connection;

	public SimpleRemoteQuerier(InternetConnectionStatus connection) {
		super();
		this.connection = connection;
	}

	///////////////////////////////////////////////////////////////////////////

	public List<Track> runLoadTracks(Bundle bundle, String... ids)  {
		List<String> idsList = Arrays.asList(ids);
		LOG.info("Loading tracks " + idsList); //$NON-NLS-1$

		List<Track> tracks = loadTracks(bundle, idsList);
		return tracks;

	}

	@Override
	public List<Track> runSearch(Bundle bundle, String query)  {
		LOG.info("Performing search of " + query); //$NON-NLS-1$

		List<Track> tracks = loadSearchResult(bundle, query);
		return tracks;
	}

	@Override
	public Track runLoadNext(Track track)  {
		LOG.info("Loading next track of " + track.getTitle()); //$NON-NLS-1$

		Bundle bundle = track.getBundle();
		String identifier = track.getIdentifier();

		Track next = loadNextOf(bundle, identifier);
		return next;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Loads track(s). In fact creates load request, executes it and converts
	 * response to Track(s).
	 * 
	 * @param bundle
	 * @param ids
	 * @return
	 * @
	 */
	private List<Track> loadTracks(Bundle bundle, List<String> ids)  {
		try {
			GtRqt request = createLoadRequest(ids);
			GtRst response = executeLoadRequest(request);
			return convertLoadResponse(bundle, response);
		} catch (Exception e) {
			connection.markOffline();

			throw new JMOPMusicbaseException("Cannot load track", e); //$NON-NLS-1$
		}
	}

	/**
	 * Searches. In fact creates search request, executes it and loads found tracks.
	 * 
	 * @param bundle
	 * @param query
	 * @return
	 * @
	 */
	private List<Track> loadSearchResult(Bundle bundle, String query)  {
		try {
			List<String> ids = doTheSearchRequest(bundle, query);
			return loadTracks(bundle, ids);
		} catch (Exception e) {
			connection.markOffline();

			throw new JMOPMusicbaseException("Cannot load search result", e); //$NON-NLS-1$
		}
	}

	/**
	 * Executes the search request. Returns the track ids.
	 * 
	 * @param bundle
	 * @param query
	 * @return
	 * @throws Exception
	 */
	private List<String> doTheSearchRequest(Bundle bundle, String query) throws Exception {
		SeaRqt request = createSearchRequest(query);
		SeaRst response = executeSearchRequest(request);
		List<String> ids = convertSearchResponse(response);
		return ids;
	}

	/**
	 * Loads next track. In fact creates "load next" request, executes it and
	 * converts response to Track.
	 * 
	 * @param bundle
	 * @param id
	 * @return
	 * @
	 */
	private Track loadNextOf(Bundle bundle, String id)  {
		try {
			List<String> ids = doTheLoadNextRequest(id);
			List<Track> tracks = loadTracks(bundle, ids);
			return chooseNext(tracks, id);
		} catch (Exception e) {
			connection.markOffline();
			throw new JMOPMusicbaseException("Cannot load next track", e); //$NON-NLS-1$
		}
	}

	/**
	 * Perfroms the load next request.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private List<String> doTheLoadNextRequest(String id) throws Exception {
		GntRqt request = createLoadNextRequest(id);
		GntRst response = executeLoadNextRequest(request);
		List<String> ids = convertLoadNextResponse(response);
		return ids;
	}
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Converts response of load track request into track.
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected abstract List<Track> convertLoadResponse(Bundle bundle, GtRst response) throws Exception;

	/**
	 * Executes the load track request.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected abstract GtRst executeLoadRequest(GtRqt request) throws Exception;

	/**
	 * Creates the load tracks request.
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	protected abstract GtRqt createLoadRequest(List<String> ids) throws Exception;

	/**
	 * Converts response of search request into IDs.
	 * 
	 * @param bundle
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected abstract List<String> convertSearchResponse(SeaRst response) throws Exception;

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
	protected abstract List<String> convertLoadNextResponse(GntRst response) throws Exception;

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

	/**
	 * Chooses the next track based on given tracks list.
	 * 
	 * @param tracks
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected abstract Track chooseNext(List<Track> tracks, String id) throws Exception;
}