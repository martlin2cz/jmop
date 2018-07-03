package cz.martlin.jmop.core.sources.remotes;

import java.net.MalformedURLException;
import java.net.URL;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public abstract class SimpleRemoteSource<GtRqt, GtRst, SeaRqt, SeaRst, GntRqt, GntRst> implements AbstractRemoteSource {

	public SimpleRemoteSource() {
		super();
	}

	@Override
	public URL urlOf(Track track) throws JMOPSourceException {
		TrackIdentifier identifier = track.getIdentifier();
		String id = identifier.getIdentifier();
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
	public Track getTrack(TrackIdentifier identifier) throws JMOPSourceException {
		String id = identifier.getIdentifier();

		Track track = loadTrack(id);
		return track;
	}

	@Override
	public Track search(String query) throws JMOPSourceException {
		Track track = loadSearchResult(query);
		return track;
	}

	@Override
	public Track getNextTrackOf(Track track) throws JMOPSourceException {
		TrackIdentifier identifier = track.getIdentifier();
		String id = identifier.getIdentifier();

		Track next = loadNextOf(id);
		return next;
	}

	///////////////////////////////////////////////////////////////////////////

	private Track loadTrack(String id) throws JMOPSourceException {
		try {
			GtRqt request = createLoadRequest(id);
			GtRst response = executeLoadRequest(request);
			return convertLoadResponse(response);
		} catch (Exception e) {
			// TODO
			throw new JMOPSourceException(e);
		}
	}

	private Track loadSearchResult(String query) throws JMOPSourceException {
		try {
			SeaRqt request = createSearchRequest(query);
			SeaRst response = executeSearchRequest(request);
			return convertSearchResponse(response);
		} catch (Exception e) {
			// TODO
			throw new JMOPSourceException(e);
		}
	}

	private Track loadNextOf(String id) throws JMOPSourceException {
		try {
			GntRqt searchListRelatedVideosRequest = createLoadNextRequest(id);
			GntRst response = executeLoadNextRequest(searchListRelatedVideosRequest);
			return convertLoadNextResponse(response);
		} catch (Exception e) {
			// TODO
			throw new JMOPSourceException(e);
		}
	}
	///////////////////////////////////////////////////////////////////////////

	protected abstract Track convertLoadResponse(GtRst response) throws Exception;

	protected abstract GtRst executeLoadRequest(GtRqt request) throws Exception;

	protected abstract GtRqt createLoadRequest(String id) throws Exception;

	protected abstract Track convertSearchResponse(SeaRst response) throws Exception;

	protected abstract SeaRst executeSearchRequest(SeaRqt request) throws Exception;

	protected abstract SeaRqt createSearchRequest(String query) throws Exception;

	protected abstract Track convertLoadNextResponse(GntRst response) throws Exception;

	protected abstract GntRst executeLoadNextRequest(GntRqt request) throws Exception;

	protected abstract GntRqt createLoadNextRequest(String id) throws Exception;

}