package cz.martlin.jmop.core.operation.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.operation.base.AbstractAtomicOperation;
import cz.martlin.jmop.core.operation.base.OperationChangeListener;
import cz.martlin.jmop.core.operation.operations.TrackSearchOperation.SearchData;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.gui.local.Msg;

/**
 * Operation performing search by keyword(s).
 * 
 * @author martin
 *
 */
public class TrackSearchOperation extends AbstractAtomicOperation<SearchData, Track> {

	private final AbstractRemoteSource remote;

	public TrackSearchOperation(ErrorReporter reporter, BaseConfiguration config, AbstractRemoteSource remote) {
		super(reporter, Msg.get("TrackSearchOperation.Track_query")); //$NON-NLS-1$
		this.remote = remote;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected Track runInternal(SearchData input, OperationChangeListener handler) throws Exception {

		Bundle bundle = input.getBundle();
		String query = input.getQuery();

		return search(bundle, query, handler);
	}

	/**
	 * Runs the search.
	 * 
	 * @param bundle
	 * @param query
	 * @param handler
	 * @return
	 * @throws JMOPSourceException
	 */
	private Track search(Bundle bundle, String query, OperationChangeListener handler) throws JMOPSourceException {

		startSubOperation(Msg.get("TrackSearchOperation.Searching_"), handler); //$NON-NLS-1$

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
