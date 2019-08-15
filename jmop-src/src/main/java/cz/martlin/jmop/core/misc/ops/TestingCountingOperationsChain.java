package cz.martlin.jmop.core.misc.ops;

import cz.martlin.jmop.core.misc.JMOPSourceException;

public class TestingCountingOperationsChain implements BaseOperationsChain<Integer> {

	private final String name;
	private final int count;

	public TestingCountingOperationsChain(String name, int count) {
		super();
		this.name = name;
		this.count = count;
	}

	@Override
	public BaseOperation<Integer, Integer> createOperation(int index, Integer input) throws JMOPSourceException {
		System.out.println("Obtaining operation " + index + " with input " + input);
		
		if (index == 0) {
			return new EmptyOperation<>(index);
			
		} else if (index < count) {
			return new TestingCountingLongOperation(name + index, index);
			
		} else {
			return null;
		}
	}
}
