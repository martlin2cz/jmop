package cz.martlin.jmop.core.misc.ops;

/**
 * The operations chain. Contains several individual, yet chained operations.
 * 
 * @author martin
 *
 * @param <T>
 */
public interface BaseOperationsChain<T> {

	public abstract BaseOperation<T, T> createOperation(int index, T input) ;
}
