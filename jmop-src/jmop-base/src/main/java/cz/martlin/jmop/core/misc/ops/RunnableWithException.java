package cz.martlin.jmop.core.misc.ops;

@FunctionalInterface
public interface RunnableWithException {
	public void run() throws Exception;
}
