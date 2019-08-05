package cz.martlin.jmop.core.sources.remote;

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

	private static String trackToString(Track track) {
		return track.getTitle(); //TODO + (duration)
	}
}
