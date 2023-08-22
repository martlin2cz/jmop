package cz.martlin.jmop.core.misc.ops;

import java.util.function.Consumer;

/**
 * Just the functional interface {@link Consumer} but throwing exception
 * @author martin
 *
 * @param <T>
 */
@FunctionalInterface
public interface ConsumerWithException<T> {

	public void consume(T arg0) throws Exception;
}
