package cz.martlin.jmop.core.misc.ops;

/**
 * The long operation is quite more complicated than the short one. Moreover the
 * run method, it has to also report its progress and also be able to
 * get terminated.
 * 
 * @author martin
 *
 */
public interface BaseLongOperation extends BaseOperation {

	@Override
	public void run() throws Exception;

	void reportProgress(double progress);
	
	public void terminate();
}
