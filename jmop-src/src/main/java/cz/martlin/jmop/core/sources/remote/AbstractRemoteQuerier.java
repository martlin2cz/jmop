package cz.martlin.jmop.core.sources.remote;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.SimpleShortOperation;

public abstract class AbstractRemoteQuerier implements BaseRemoteSourceQuerier {

	@Override
	public SimpleShortOperation<String, List<Track>> search(Bundle bundle, String query) throws JMOPSourceException {
		return new SimpleShortOperation<>("Querying", query, //
				(q) -> q, //
				(q) -> runSearch(bundle, q)); //
	}

	@Override
	public SimpleShortOperation<Track, Track> loadNext(Track track) throws JMOPSourceException {
		return new SimpleShortOperation<>("Loading next", track, //
				(t) -> trackToString(t), //
				(t) -> runLoadNext(t)); //
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public abstract List<Track> runSearch(Bundle bundle, String query) throws JMOPSourceException;

	public abstract Track runLoadNext(Track track) throws JMOPSourceException;

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public URL urlOfTrack(Track track) {
		String url = createUrlOfTrack(track);
		return stringToURL(url);
	}

	protected abstract String createUrlOfTrack(Track track);

	@Override
	public URL urlOfSearchResult(String query) {
		String url = createUrlOfSearchResult(query);
		return stringToURL(url);
	}

	protected abstract String createUrlOfSearchResult(String query);

/////////////////////////////////////////////////////////////////////////////////////

	private static String trackToString(Track track) {
		return track.getTitle(); // TODO + (duration)
	}

	private static URL stringToURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Invalid url: " + url, e);
		}
	}

	protected static String encodeURLdata(String url) {
		try {
			return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 is unknown", e);
		}
	}
}
