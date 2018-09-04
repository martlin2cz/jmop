package cz.martlin.jmop.core.sources.remotes;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;

public abstract class SimpleRemoteSource<GtRqt, GtRst, SeaRqt, SeaRst, GntRqt, GntRst> implements AbstractRemoteSource {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final InternetConnectionStatus connection;

	public SimpleRemoteSource(InternetConnectionStatus connection) {
		super();
		this.connection = connection;
	}

	@Override
	public URL urlOf(Track track) throws JMOPSourceException {
		LOG.info("Generating url of track " + track.getTitle());
		String id = track.getIdentifier();
		String url = urlOfTrack(id);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			// TODO exception
			throw new JMOPSourceException(e);
		}
	}

	protected abstract String urlOfTrack(String id);

	///////////////////////////////////////////////////////////////////////////

	@Override
	public Track getTrack(Bundle bundle, String identifier) throws JMOPSourceException {
		LOG.info("Loading track with id " + identifier);

		return loadTrack(bundle, identifier);
	}

	@Override
	public Track search(Bundle bundle, String query) throws JMOPSourceException {
		LOG.info("Performing search of " + query);

		Track track = loadSearchResult(bundle, query);
		return track;
	}

	@Override
	public Track getNextTrackOf(Track track) throws JMOPSourceException {
		LOG.info("Loading next track of " + track.getTitle());

		Bundle bundle = track.getBundle();
		String identifier = track.getIdentifier();

		Track next = loadNextOf(bundle, identifier);
		return next;
	}

	///////////////////////////////////////////////////////////////////////////

	private Track loadTrack(Bundle bundle, String id) throws JMOPSourceException {
		try {
			GtRqt request = createLoadRequest(id);
			GtRst response = executeLoadRequest(request);
			return convertLoadResponse(bundle, response);
		} catch (Exception e) {
			connection.markOffline();

			throw new JMOPSourceException("Cannot load track", e);
		}
	}

	private Track loadSearchResult(Bundle bundle, String query) throws JMOPSourceException {
		try {
			SeaRqt request = createSearchRequest(query);
			SeaRst response = executeSearchRequest(request);
			return convertSearchResponse(bundle, response);
		} catch (Exception e) {
			connection.markOffline();

			throw new JMOPSourceException("Cannot load search result", e);
		}
	}

	private Track loadNextOf(Bundle bundle, String id) throws JMOPSourceException {
		try {
			GntRqt request = createLoadNextRequest(id);
			GntRst response = executeLoadNextRequest(request);
			return convertLoadNextResponse(bundle, response);
		} catch (Exception e) {
			connection.markOffline();
			throw new JMOPSourceException("Cannot load next track", e);
		}
	}
	///////////////////////////////////////////////////////////////////////////

	protected abstract Track convertLoadResponse(Bundle bundle, GtRst response) throws Exception;

	protected abstract GtRst executeLoadRequest(GtRqt request) throws Exception;

	protected abstract GtRqt createLoadRequest(String id) throws Exception;

	protected abstract Track convertSearchResponse(Bundle bundle, SeaRst response) throws Exception;

	protected abstract SeaRst executeSearchRequest(SeaRqt request) throws Exception;

	protected abstract SeaRqt createSearchRequest(String query) throws Exception;

	protected abstract Track convertLoadNextResponse(Bundle bundle, GntRst response) throws Exception;

	protected abstract GntRst executeLoadNextRequest(GntRqt request) throws Exception;

	protected abstract GntRqt createLoadNextRequest(String id) throws Exception;

}