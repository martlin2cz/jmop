package cz.martlin.jmop.core.sources.remote;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.ShortOperation;

public abstract class AbstractRemoteQuerier implements BaseRemoteSourceQuerier {

	@Override
	public ShortOperation search(Bundle bundle, String query, int page) throws JMOPSourceException {
		return new ShortOperation("Querying", query, //
				() -> runSearch(bundle, query, page));
	}

	@Override
	public ShortOperation loadNext(Track track) throws JMOPSourceException {
		return new ShortOperation("Loading next", trackToString(track), //
				() -> runLoadNext(track));
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public abstract List<Track> runSearch(Bundle bundle, String query, int page) throws JMOPSourceException;

	public abstract Track runLoadNext(Track track) throws JMOPSourceException;

/////////////////////////////////////////////////////////////////////////////////////

	private static String trackToString(Track track) {
		return track.getTitle(); //TODO + (duration)
	}
}
