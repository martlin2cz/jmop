package cz.martlin.jmop.core.misc.ops;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class OperationsManager {

	private final CurrentOperations currents;

	public OperationsManager() {
		this.currents = new CurrentOperations();
	}

	protected CurrentOperations getOperations() {
		return currents;
	}

/////////////////////////////////////////////////////////////////////////////////////////////

	public <InT, OutT> void start(BaseOperation<InT, OutT> operation, ConsumerWithException<OutT> onComplete) {
		OperationTask<InT, OutT> task = prepareTask(operation, onComplete);

		currents.add(task);

		run(task);
	}

	public <T> void start(T input, BaseOperationsChain<T> chain, ConsumerWithException<T> onChainComplete)
			throws JMOPSourceException {
		startNextStepOfChain(chain, 0, input, onChainComplete);
	}

	private <T> void startNextStepOfChain(BaseOperationsChain<T> chain, int index, T input,
			ConsumerWithException<T> onChainComplete) throws JMOPSourceException {
		BaseOperation<T, T> operation = chain.createOperation(index, input);
		
		//TODO chaeck interrupted
		if (operation != null) {
			start(operation, (r) -> startNextStepOfChain(chain, index + 1, r, onChainComplete));
		} else {
			handleChainResult(input, onChainComplete);
		}
	}

	private <T> void handleChainResult(T input, ConsumerWithException<T> onChainComplete) throws JMOPSourceException {
		try {
			onChainComplete.consume(input);
		} catch (Exception e) {
			throw new JMOPSourceException("Failure during the result handle", e);
			// TODO separate internal and app error?
		}
	}

	private <InT, OutT> OperationTask<InT, OutT> prepareTask(BaseOperation<InT, OutT> operation,
			ConsumerWithException<OutT> onComplete) {
		OperationTask<InT, OutT> task = new OperationTask<InT, OutT>(operation);

		task.setOnSucceeded(new OperationTaskOnSucceededHandler<>(task, onComplete));
		task.setOnFailed(new OperationTaskOnFailedHandler<>(task));
		return task;
	}

	private <InT, OutT> void run(OperationTask<InT, OutT> task) {
		String name = task.getName();
		Thread thread = new Thread(task, name);
		thread.start();
	}

/////////////////////////////////////////////////////////////////////////////////////////////

	public void stopAll() {
		// TODO stop all running
	}

/////////////////////////////////////////////////////////////////////////////////////////////

	public class OperationTaskOnFailedHandler<InT, OutT> implements EventHandler<WorkerStateEvent> {

		private final OperationTask<InT, OutT> task;

		public OperationTaskOnFailedHandler(OperationTask<InT, OutT> task) {
			this.task = task;
		}

		@Override
		public void handle(WorkerStateEvent event) {
			currents.remove(task);
			Throwable e = task.getException();
			System.err.println("Operation failed: " + e); // FIXME handle error
		}

	}

	public class OperationTaskOnSucceededHandler<InT, OutT> implements EventHandler<WorkerStateEvent> {
		private final OperationTask<InT, OutT> task;
		private final ConsumerWithException<OutT> onComplete;

		public OperationTaskOnSucceededHandler(OperationTask<InT, OutT> task, ConsumerWithException<OutT> onComplete) {
			super();
			this.task = task;
			this.onComplete = onComplete;
		}

		@Override
		public void handle(WorkerStateEvent event) {
			// TODO task set title to "Completing"
			try {
				OutT result = task.get();
				onComplete.consume(result);
			} catch (Exception e) {
				e.printStackTrace(); // FIXME handle error
			} finally {
				currents.remove(task);
			}
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////

}
