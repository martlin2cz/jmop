package cz.martlin.jmop.core.misc.ops;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseOperationsChain<T> {

	public abstract BaseOperation<T, T> createOperation(int index, T input) ;
}
