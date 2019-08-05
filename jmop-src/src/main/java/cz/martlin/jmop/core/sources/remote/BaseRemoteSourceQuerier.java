package cz.martlin.jmop.core.sources.remote;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.SimpleShortOperation;

public interface BaseRemoteSourceQuerier {
	public SimpleShortOperation<String, List<Track>> search(Bundle bundle, String query) throws JMOPSourceException;

	public SimpleShortOperation<Track, Track> loadNext(Track track) throws JMOPSourceException;
}
