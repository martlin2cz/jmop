package cz.martlin.jmop.core.operation.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.gui.local.Msg;

/**
 * Operation, which is just simply atomic. This class shall be used as a base
 * class for all implementations.
 * 
 * @author martin
 *
 * @param <IT>
 *            input type
 * @param <OT>
 *            output type
 */
public abstract class AbstractAtomicOperation<IT, OT> implements BaseOperation<IT, OT> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final ErrorReporter reporter;

	private final String name;

	/**
	 * Creates instance.
	 * 
	 * @param reporter
	 *            error repoter for handling errors
	 * @param name
	 *            human name of this operation
	 */
	public AbstractAtomicOperation(ErrorReporter reporter, String name) {
		super();
		this.reporter = reporter;

		this.name = name;
	}

	/**
	 * Convert given input to string.
	 * 
	 * @param input
	 * @return
	 */
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

	/**
	 * Does the start of the operation. Updates the status, data, and progress
	 * and logs.
	 * 
	 * @param input
	 * @param handler
	 */
	protected void handleStart(IT input, OperationChangeListener handler) {
		handler.updateStatus(name);
		String data = inputDataAsString(input);
		handler.updateData(data);
		handler.updateProgress(0.0);

		LOG.info("Starting atomic operation " + name + " with " + data); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Runs the operation itself.
	 * 
	 * @param input
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	protected abstract OT runInternal(IT input, OperationChangeListener handler) throws Exception;

	/**
	 * Starts suboperation of given name.
	 * 
	 * @param sub
	 * @param handler
	 */
	protected void startSubOperation(String sub, OperationChangeListener handler) {
		handler.updateStatus(sub);
		handler.updateProgress(0.0);

		LOG.debug("Atomic operation " + name + " starting sub-operation " + sub); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Does the finish of the operation. Updates status to "completing",
	 * progress to 100% and logs.
	 * 
	 * @param handler
	 */
	protected void handleFinish(OperationChangeListener handler) {
		handler.updateStatus(name + " " +Msg.get("completing_")); //$NON-NLS-1$
		handler.updateProgress(OperationChangeListener.THE_100_PERCENT);

		LOG.info("Atomic operation " + name + " completed"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	/////////////////////////////////////////////////////////////////////////////////////

}