package cz.martlin.jmop.core.operation.base;

/**
 * The most abstract operation. Operations is something which runs for some
 * input, reports changes and generates output, which is then returned.
 * 
 * @author martin
 * @see AbstractAtomicOperation
 * @see TwosetOperation
 * 
 * @param <IT>
 *            input type
 * @param <OT>
 *            output type
 */
@Deprecated
public interface BaseOperation<IT, OT> {
	/**
	 * Run this operation with given input reporting status into given handler.
	 * 
	 * @param input
	 *            the input
	 * @param handler
	 * @return the result
	 */
	public OT run(IT input, OperationChangeListener handler);
}