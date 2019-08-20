package cz.martlin.jmop.core.misc.ops;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;

public class Operations {
	private final OperationsManager manager;
	private final FormatsLocationsUtility flu;
	private final BaseRemoteSource remote;
	private final BaseLocalSource local;

	public Operations(OperationsManager manager, FormatsLocationsUtility flu, BaseRemoteSource remote,
			BaseLocalSource local) {
		super();
		this.manager = manager;
		this.flu = flu;
		this.remote = remote;
		this.local = local;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public void runSearch(Bundle bundle, String query, ConsumerWithException<List<Track>> resultHandler)
			throws JMOPSourceException {
		BaseRemoteSourceQuerier querier = remote.querier();
		BaseOperation<String, List<Track>> operation = querier.search(bundle, query);
		manager.start(operation, resultHandler);
	}

	public void runLoadNext(Track track, ConsumerWithException<Track> resultHandler) throws JMOPSourceException {
		BaseRemoteSourceQuerier querier = remote.querier();
		BaseOperation<Track, Track> operation = querier.loadNext(track);
		manager.start(operation, resultHandler);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void prepareFiles(Track track, ConsumerWithException<Track> resultHandler) throws JMOPSourceException {
		BaseOperationsChain<Track> chain = new PrepareTrackFilesOperationChain(flu, remote, local);
		manager.start(track, chain, resultHandler);
	}

}
