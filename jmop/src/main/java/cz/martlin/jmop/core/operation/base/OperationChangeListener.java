package cz.martlin.jmop.core.operation.base;

import cz.martlin.jmop.core.misc.ProgressListener;

public interface OperationChangeListener extends ProgressListener {

	public void updateStatus(String status);

	public void updateData(String data);

	public default void updateProgress(double percentage) {
		progressChanged(percentage);
	}
}
