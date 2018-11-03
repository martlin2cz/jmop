package cz.martlin.jmop.core.preparer.operations.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public abstract class AbstractAtomicOperation<IT, OT> implements BaseOperation<IT, OT> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final ErrorReporter reporter;
	
	private final String name;
	
	public AbstractAtomicOperation(ErrorReporter reporter, String name) {
		super();
		this.reporter =reporter;
		
		this.name = name;
	}

	public abstract String inputDataAsString(IT input);

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public OT run(IT input, OperationChangeListener handler) {
		handleStart(input, handler);

		OT result;
		try {
			result = runInternal(input, handler);
		} catch (JMOPSourceException e) {
			reporter.report(e);
			result = null;
		} catch (Exception e) {
			reporter.internal(e);
			result = null;
		}

		handleFinish(handler);

		return result;
	}

	protected void handleStart(IT input, OperationChangeListener handler) {
		handler.updateStatus(name);
		String data = inputDataAsString(input);
		handler.updateData(data);
		handler.updateProgress(0.0);

		LOG.info("Starting atomic operation " + name + " with " + data);
	}

	protected abstract OT runInternal(IT input, OperationChangeListener handler) throws Exception;

	protected void startSubOperation(String sub, OperationChangeListener handler) {
		handler.updateStatus(sub);
		handler.updateProgress(0.0);

		LOG.debug("Atomic operation " + name + " starting sub-operation " + sub);
	}

	protected void handleFinish(OperationChangeListener handler) {
		handler.updateStatus(name + " (completing)");
		handler.updateProgress(OperationChangeListener.THE_100_PERCENT);

		LOG.info("Atomic operation " + name + " completed");
	}
	/////////////////////////////////////////////////////////////////////////////////////

}