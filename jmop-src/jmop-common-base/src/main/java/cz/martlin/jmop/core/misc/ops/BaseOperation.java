package cz.martlin.jmop.core.misc.ops;

/**
 * Base abstract operation. Doing something with some input, having some output.
 * 
 * @author martin
 *
 * @param <InT>
 * @param <OutT>
 */
public interface BaseOperation<InT, OutT> {
	/**
	 * Returns the name.
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns the input as string.
	 * @return
	 */
	public String getInputDataAsString();
	
	/**
	 * Runs the operation, reporting the progress.
	 * 
	 * @param listener
	 * @return
	 * @throws Exception
	 */
	public OutT run(BaseProgressListener listener) throws Exception;
	
}
