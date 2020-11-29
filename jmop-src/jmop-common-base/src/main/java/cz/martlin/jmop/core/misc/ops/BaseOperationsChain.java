package cz.martlin.jmop.core.misc.ops;

import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseOperationsChain<T> {

	public abstract BaseOperation<T, T> createOperation(int index, T input) throws JMOPSourceException;
}
