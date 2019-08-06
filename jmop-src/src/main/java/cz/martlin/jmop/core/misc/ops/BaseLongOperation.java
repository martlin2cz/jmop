package cz.martlin.jmop.core.misc.ops;

/**
 * The long operation is quite more complicated than the short one. Moreover the
 * run method, it has to also report its progress and also be able to
 * get terminated.
 * 
 * @author martin
 *
 */
public interface BaseLongOperation<InT, OutT> extends BaseOperation<InT, OutT> {

	@Override
	public OutT run(BaseProgressListener listener) throws Exception;

	void reportProgress(BaseProgressListener listener, double progress);
	
	public void terminate();
}
