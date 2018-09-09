package cz.martlin.jmop.core.preparer;

import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.misc.TextualStatusUpdateListener;
import cz.martlin.jmop.core.preparer.operations.BaseTrackOperation;
import javafx.concurrent.Task;

public class PreparationInstanceTask<IT, OT> extends Task<OT> implements ProgressListener, TextualStatusUpdateListener {

	private final BaseTrackOperation<IT, OT> operation;
	private IT data;

	public PreparationInstanceTask(BaseTrackOperation<IT, OT> operation, IT data) {
		this.operation = operation;
		this.data = data;
	}

	@Override
	protected OT call() throws Exception {
		return operation.run(data, this, this);
	}

	@Override
	public void statusChaned(String status) {
		updateMessage(status);
	}

	@Override
	public void progressChanged(double percentage) {
		updateProgress(percentage, THE_100_PERCENT);
	}

}
