package cz.martlin.jmop.core.operation.base;

import cz.martlin.jmop.core.misc.ProgressListener;

/**
 * Listener for changes within the operation computation. Operation can using
 * this class change its status, data and progress.
 * 
 * @author martin
 *
 */
public interface OperationChangeListener extends ProgressListener {
	/**
	 * Status of operation updated to given value.
	 * 
	 * @param status
	 */
	public void updateStatus(String status);

	/**
	 * Data of operation updated to given value.
	 * 
	 * @param data
	 */
	public void updateData(String data);

	/**
	 * The progress of operation update to given value. Same as
	 * {@link #progressChanged(double)}.
	 * 
	 * @param percentage
	 */
	public default void updateProgress(double percentage) {
		progressChanged(percentage);
	}
}
