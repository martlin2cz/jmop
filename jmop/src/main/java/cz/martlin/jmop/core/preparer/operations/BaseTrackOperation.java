package cz.martlin.jmop.core.preparer.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.misc.TextualStatusUpdateListener;

public abstract class BaseTrackOperation<IT, OT> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final String name;

	public BaseTrackOperation(String name) {
		super();

		this.name = name;
	}

	public String getName() {
		return name;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public OT run(IT input, ProgressListener progressListener, TextualStatusUpdateListener statusListener) {
		try {
			return runInternal(input, progressListener, statusListener);
		} catch (Exception e) {
			e.printStackTrace(); // TODO handle error
			return null;
		}
	}

	protected abstract OT runInternal(IT input, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) throws Exception;

	/////////////////////////////////////////////////////////////////////////////////////

	protected void startSubTask(String subtask, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) {
		
		LOG.info(name + ": " + subtask);

		progressListener.progressChanged(0.0);
		statusListener.statusChaned(subtask);
	}

}