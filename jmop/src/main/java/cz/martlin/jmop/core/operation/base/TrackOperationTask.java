package cz.martlin.jmop.core.operation.base;

import cz.martlin.jmop.core.misc.ProgressListener;
import javafx.concurrent.Task;

public class TrackOperationTask<IT, OT> extends Task<OT> {

	private final OperationWrapper<IT, OT> operation;
	private IT data;

	public TrackOperationTask(OperationWrapper<IT, OT> operation, IT data) {
		this.operation = operation;
		this.data = data;
	}

	@Override
	protected OT call() throws Exception {

		bindProperties();

		OT result = operation.run(data);

		return result;
	}

	private void bindProperties() {
		operation.statusProperty().addListener((observable, oldVal, newVal) -> statusChanged(newVal));
		operation.progressProperty().addListener((observable, oldVal, newVal) -> progressChanged(newVal));
	}

	private void statusChanged(String status) {
		updateMessage(status);
	}

	private void progressChanged(Number progress) {
		updateProgress(progress.doubleValue(), ProgressListener.THE_100_PERCENT);
	}

}
