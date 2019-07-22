package cz.martlin.jmop.core.operation.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.operation.base.AbstractAtomicOperation;
import cz.martlin.jmop.core.operation.base.OperationChangeListener;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.XXX_AbstractRemoteSource;
import cz.martlin.jmop.gui.local.Msg;

/**
 * Operation loading next track of some track.
 * 
 * @author martin
 *
 */
public class NextTrackOperation extends AbstractAtomicOperation<Track, Track> {
	private final XXX_AbstractRemoteSource remote;

	public NextTrackOperation(ErrorReporter reporter, BaseConfiguration config, XXX_AbstractRemoteSource remote) {
		super(reporter, Msg.get("Next_track")); //$NON-NLS-1$
		this.remote = remote;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected Track runInternal(Track input, OperationChangeListener handler) throws Exception {

		return loadNext(input, handler);
	}

	/**
	 * Loads next track of given track.
	 * 
	 * @param track
	 * @param handler
	 * @return
	 * @throws JMOPSourceException
	 */
	private Track loadNext(Track track, OperationChangeListener handler) throws JMOPSourceException {

		startSubOperation(Msg.get("Loading_next"), handler); //$NON-NLS-1$

		Track next = remote.getNextTrackOf(track);

		return next;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String inputDataAsString(Track input) {
		return TrackFilesLoadOperation.trackToString(input);
	}
}
