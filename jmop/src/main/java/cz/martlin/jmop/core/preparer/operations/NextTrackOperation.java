package cz.martlin.jmop.core.preparer.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.preparer.operations.base.AbstractAtomicOperation;
import cz.martlin.jmop.core.preparer.operations.base.OperationChangeListener;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;

public class NextTrackOperation extends AbstractAtomicOperation<Track, Track> {
	private final AbstractRemoteSource remote;

	public NextTrackOperation(ErrorReporter reporter, BaseConfiguration config, AbstractRemoteSource remote) {
		super(reporter, "Next track");
		this.remote = remote;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected Track runInternal(Track input, OperationChangeListener handler) throws Exception {

		return loadNext(input, handler);
	}

	private Track loadNext(Track track, OperationChangeListener handler) throws JMOPSourceException {

		startSubOperation("Loading next ...", handler);

		Track next = remote.getNextTrackOf(track);

		return next;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String inputDataAsString(Track input) {
		return TrackFilesLoadOperation.trackToString(input);
	}
}
