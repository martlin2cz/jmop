package cz.martlin.jmop.core.sources.remote;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.SimpleShortOperation;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSourceQuerier;

public abstract class AbstractRemoteQuerier implements BaseRemoteSourceQuerier {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

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

	private static String trackToString(Track track) {
		return track.toHumanString();
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
