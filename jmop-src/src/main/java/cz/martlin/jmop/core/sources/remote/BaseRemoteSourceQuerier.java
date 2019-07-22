package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.ShortOperation;

public interface BaseRemoteSourceQuerier {
	public ShortOperation search(Bundle bundle, String query) throws JMOPSourceException;

	public ShortOperation loadNext(Track track) throws JMOPSourceException;
}
