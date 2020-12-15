package cz.martlin.jmop.core.misc.ops;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;
import cz.martlin.jmop.core.sources.local.misc.flu.FormatsLocationsUtility;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;

public class Operations implements BaseOperations {
	private final OperationsManager manager;
	private final FormatsLocationsUtility flu;
	private final BaseRemoteSource remote;
	private final XXX_BaseLocalSource local;

	public Operations(OperationsManager manager, FormatsLocationsUtility flu, BaseRemoteSource remote,
			XXX_BaseLocalSource local) {
		super();
		this.manager = manager;
		this.flu = flu;
		this.remote = remote;
		this.local = local;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void runSearch(Bundle bundle, String query, ConsumerWithException<List<Track>> resultHandler)
			throws JMOPMusicbaseException {
		BaseRemoteSourceQuerier querier = remote.querier();
		BaseOperation<String, List<Track>> operation = querier.search(bundle, query);
		manager.start(operation, resultHandler);
	}

	@Override
	public void runLoadNext(Track track, ConsumerWithException<Track> resultHandler) throws JMOPMusicbaseException {
		BaseRemoteSourceQuerier querier = remote.querier();
		BaseOperation<Track, Track> operation = querier.loadNext(track);
		manager.start(operation, resultHandler);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void prepareFiles(Track track, ConsumerWithException<Track> resultHandler) throws JMOPMusicbaseException {
		BaseOperationsChain<Track> chain = new PrepareTrackFilesOperationChain(flu, remote, local);
		manager.start(track, chain, resultHandler);
	}

}
