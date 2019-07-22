package cz.martlin.jmop.core.sources.remote;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.ShortOperation;

public interface BaseRemoteSourceQuerier {
	public ShortOperation search(Bundle bundle, String query, int page) throws JMOPSourceException;
	public ShortOperation loadNext(Track track) throws JMOPSourceException;
}
