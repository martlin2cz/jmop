package cz.martlin.jmop.core.misc.ops;

public interface ConsumerWithException<T> {

	public void consume(T arg0) throws Exception;
}
