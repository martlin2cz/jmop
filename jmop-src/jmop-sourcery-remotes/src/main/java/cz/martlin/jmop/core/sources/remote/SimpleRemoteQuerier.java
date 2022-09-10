package cz.martlin.jmop.core.sources.remote;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Track;
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


	public SimpleRemoteQuerier() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public List<TrackData> search(String query) throws JMOPSourceryException {
		LOG.info("Performing search of " + query); //$NON-NLS-1$
		
		return loadSearchResult(query);
	}
	
	public List<TrackData> runLoadTracks(String... ids) throws JMOPSourceryException  {
		List<String> idsList = Arrays.asList(ids);
		LOG.info("Loading tracks " + idsList); //$NON-NLS-1$

		List<TrackData> tracks = loadTracks(idsList);
		return tracks;

	}


	@Override
	public TrackData loadNext(Track track) throws JMOPSourceryException {
		LOG.info("Loading next track of " + track.getTitle()); //$NON-NLS-1$

		String identifier = pickTrackIdentifier(track);
		return loadNextOf(identifier);
	}

	///////////////////////////////////////////////////////////////////////////

	

	/**
	 * Loads track(s). In fact creates load request, executes it and converts
	 * response to Track(s).
	 * 
	 * @param bundle
	 * @param ids
	 * @return
	 * @throws JMOPMusicbaseException 
	 * @
	 */
	private List<TrackData> loadTracks(List<String> ids) throws JMOPSourceryException {
		try {
			GtRqt request = createLoadRequest(ids);
			GtRst response = executeLoadRequest(request);
			return convertLoadResponse(response);
		} catch (Exception e) {
			throw new JMOPSourceryException("Cannot load track", e); //$NON-NLS-1$
		}
	}

	/**
	 * Searches. In fact creates search request, executes it and loads found tracks.
	 * 
	 * @param bundle
	 * @param query
	 * @return
	 * @throws JMOPMusicbaseException 
	 * @
	 */
	private List<TrackData> loadSearchResult(String query) throws JMOPSourceryException {
		try {
			List<String> ids = doTheSearchRequest(query);
			return loadTracks(ids);
		} catch (Exception e) {
			throw new JMOPSourceryException("Cannot load search result", e); //$NON-NLS-1$
		}
	}

	/**
	 * Executes the search request. Returns the track ids.
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	private List<String> doTheSearchRequest(String query) throws Exception {
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
	 * @throws JMOPMusicbaseException 
	 * @
	 */
	private TrackData loadNextOf(String id) throws JMOPSourceryException  {
		try {
			List<String> ids = doTheLoadNextRequest(id);
			List<TrackData> tracks = loadTracks(ids);
			return chooseNext(tracks, id);
		} catch (Exception e) {
			throw new JMOPSourceryException("Cannot load next track", e); //$NON-NLS-1$
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
	
	private String pickTrackIdentifier(Track track) throws JMOPSourceryException {
		URI source = track.getSource();
		
		try {
			return  extractIdentifier(source);
		} catch (Exception e) {
			throw new JMOPSourceryException("Cannot extract track identifier", e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Converts response of load track request into track.
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected abstract List<TrackData> convertLoadResponse(GtRst response) throws Exception;

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
	 * Extracts the identifier from the track source location.
	 * 
	 * @param source
	 * @return
	 * @throws JMOPSourceryException 
	 */
	protected abstract String extractIdentifier(URI source) throws Exception;
	
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
	protected abstract TrackData chooseNext(List<TrackData> tracks, String title) throws Exception;
}