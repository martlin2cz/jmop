package cz.martlin.jmop.core.misc.ops;

import javafx.concurrent.Task;

public class OperationJob<InT, OutT> extends Task<OutT> implements BaseProgressListener {

	private final BaseOperation<InT, OutT> operation;

	public OperationJob(BaseOperation<InT, OutT> operation) {
		super();
		this.operation = operation;
	}

	///////////////////////////////////////////////////////////////////////////
	
	@Override
	protected OutT call() throws Exception {
		prepare();

		return invoke();
	}

	private void prepare() {
		String title = operation.getName();
		updateTitle(title);

		String message = operation.getInputDataAsString();
		updateMessage(message);
	}

	private OutT invoke() throws Exception {
		return operation.run(this);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void reportProgressChanged(double progress) {
		this.updateProgress(progress, THE_100_PERCENT);
	}

}
