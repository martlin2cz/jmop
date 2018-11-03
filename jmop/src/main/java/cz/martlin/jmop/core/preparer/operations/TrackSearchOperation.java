package cz.martlin.jmop.core.preparer.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.preparer.operations.TrackSearchOperation.SearchData;
import cz.martlin.jmop.core.preparer.operations.base.AbstractAtomicOperation;
import cz.martlin.jmop.core.preparer.operations.base.OperationChangeListener;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;

public class TrackSearchOperation extends AbstractAtomicOperation<SearchData, Track> {

	private final AbstractRemoteSource remote;

	public TrackSearchOperation(BaseConfiguration config, AbstractRemoteSource remote) {
		super("Track query");
		this.remote = remote;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected Track runInternal(SearchData input, OperationChangeListener handler) throws Exception {

		Bundle bundle = input.getBundle();
		String query = input.getQuery();

		return search(bundle, query, handler);
	}

	private Track search(Bundle bundle, String query, OperationChangeListener handler) throws JMOPSourceException {

		startSubOperation("Searching ...", handler);

		Track track = remote.search(bundle, query);

		return track;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String inputDataAsString(SearchData input) {
		return input.getQuery();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public static class SearchData {

		private final Bundle bundle;
		private final String query;

		public SearchData(Bundle bundle, String query) {
			this.bundle = bundle;
			this.query = query;
		}

		public Bundle getBundle() {
			return bundle;
		}

		public String getQuery() {
			return query;
		}

	}

}
