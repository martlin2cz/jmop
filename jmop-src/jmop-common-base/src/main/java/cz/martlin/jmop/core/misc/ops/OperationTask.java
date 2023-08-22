package cz.martlin.jmop.core.misc.ops;

import javafx.concurrent.Task;

/**
 * JavaFX task executing the operation.
 * 
 * @author martin
 *
 * @param <InT>
 * @param <OutT>
 */
public class OperationTask<InT, OutT> extends Task<OutT> implements BaseProgressListener {

	private final BaseOperation<InT, OutT> operation;

	public OperationTask(BaseOperation<InT, OutT> operation) {
		super();
		this.operation = operation;
	}

	public BaseOperation<InT, OutT> getOperation() {
		return operation;
	}
	///////////////////////////////////////////////////////////////////////////

	@Override
	protected OutT call() throws Exception {
		prepare();

		OutT result = invoke();
		
		return result;
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

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if (operation instanceof BaseLongOperation) {
			BaseLongOperation<InT, OutT> longOperation = (BaseLongOperation<InT, OutT>) operation;
			longOperation.terminate();
		}

		return super.cancel(mayInterruptIfRunning);
	}

	///////////////////////////////////////////////////////////////////////////

	public String getName() {
		return operation.getName();
	}
	
	public String getInputAsString() {
		return operation.getInputDataAsString();
	}
	
	@Override
	public void reportProgressChanged(double progress) {
		this.updateProgress(progress, THE_100_PERCENT);
	}


}
