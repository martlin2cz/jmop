package cz.martlin.jmop.core.sources.remote;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSourceQuerier;

/**
 * The abstract common remote querier superclass.
 * 
 * @author martin
 *
 */
public abstract class AbstractRemoteQuerier implements BaseRemoteSourceQuerier {

//	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public AbstractRemoteQuerier() {
		super();
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public URL urlOfTrack(Track track) {
		String url = createUrlOfTrack(track);
		return stringToURL(url);
	}

	protected abstract String createUrlOfTrack(Track track);

/////////////////////////////////////////////////////////////////////////////////////


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
