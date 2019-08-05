package cz.martlin.jmop.core.operation.base;

import cz.martlin.jmop.core.misc.XXX_ProgressListener;
import javafx.concurrent.Task;

/**
 * The Task (possibly backound task) for running of operation. Update of status
 * and progress of the operation are properly delegated into this task's
 * {@link #updateMessage(String)} and {@link #updateProgress(double, double)}
 * methods.
 * 
 * @author martin
 *
 * @param <IT>
 *            input type
 * @param <OT>
 *            output type
 */
public class TrackOperationTask<IT, OT> extends Task<OT> {

	private final OperationWrapper<IT, OT> operation;
	private IT data;

	/**
	 * Creates instance
	 * 
	 * @param operation
	 *            the operation (wrapped)
	 * @param data
	 *            the input data
	 */
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

	/**
	 * Binds operation's wrapper's properties to listeners.
	 */
	private void bindProperties() {
		operation.statusProperty().addListener((observable, oldVal, newVal) -> statusChanged(newVal));
		operation.progressProperty().addListener((observable, oldVal, newVal) -> progressChanged(newVal));
	}

	/**
	 * Updates the status message.
	 * 
	 * @param status
	 */
	private void statusChanged(String status) {
		updateMessage(status);
	}

	/**
	 * Updates the progress.
	 * 
	 * @param progress
	 */
	private void progressChanged(Number progress) {
		updateProgress(progress.doubleValue(), XXX_ProgressListener.THE_100_PERCENT);
	}

}
