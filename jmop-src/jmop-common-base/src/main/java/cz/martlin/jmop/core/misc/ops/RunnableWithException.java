package cz.martlin.jmop.core.misc.ops;

/**
 * Like {@link Runnable} but with exception.
 * 
 * @author martin
 *
 */
@FunctionalInterface
public interface RunnableWithException {
	public void run() throws Exception;
}
