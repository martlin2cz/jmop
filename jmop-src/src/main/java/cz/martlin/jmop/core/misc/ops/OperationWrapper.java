package cz.martlin.jmop.core.misc.ops;

import java.util.function.Consumer;

/**
 * use {@link OperationJob} instead.
 * @author martin
 *
 * @param <InT>
 * @param <OutT>
 */
@Deprecated
public class OperationWrapper<InT, OutT> {
	private final BaseOperation<InT, OutT> operation;
	private final BaseProgressListener listener;
	private final Consumer<OutT> onComplete;

	public OperationWrapper(BaseOperation<InT, OutT> operation, BaseProgressListener listener,
			Consumer<OutT> onComplete) {
		super();
		this.operation = operation;
		this.listener = listener;
		this.onComplete = onComplete;
	}
	
	public BaseOperation<InT, OutT> getOperation() {
		return operation;
	}

	public void run() {
		try {
			OutT result = operation.run(listener);
			onComplete.accept(result);
		} catch (Exception e) {
			// TODO handle error
			e.printStackTrace();
		}
	}

	public void terminateIfSupported() {
		if (operation instanceof BaseLongOperation) {
			BaseLongOperation<InT, OutT> longOperation = (BaseLongOperation<InT, OutT>) operation;
			longOperation.terminate();
		}
	}

}
