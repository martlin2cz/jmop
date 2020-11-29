package cz.martlin.jmop.core.misc.ops;

import cz.martlin.jmop.core.misc.JMOPSourceException;

public abstract class AbstractTriPhasedOperationsChain<T> implements BaseOperationsChain<T> {

	public AbstractTriPhasedOperationsChain() {
	}

	@Override
	public BaseOperation<T, T> createOperation(int index, T input) throws JMOPSourceException {
		switch (index) {
		case 0:
			return createFirstOperation(input);
		case 1:
			return createSecondOperation(input);
		case 2:
			return createThirdOperation(input);
		case 3:
			return null;
		default:
			throw new IllegalArgumentException("index: " + index);
		}
	}

	protected abstract BaseOperation<T, T> createFirstOperation(T input) throws JMOPSourceException;

	protected abstract BaseOperation<T, T> createSecondOperation(T input) throws JMOPSourceException;

	protected abstract BaseOperation<T, T> createThirdOperation(T input) throws JMOPSourceException;

}
