package cz.martlin.jmop.core.misc.ops;

/**
 * Chain of testing counting operations.
 * 
 * @author martin
 *
 */
public class TestingCountingOperationsChain implements BaseOperationsChain<Integer> {

	private final String name;
	private final int count;

	public TestingCountingOperationsChain(String name, int count) {
		super();
		this.name = name;
		this.count = count;
	}

	@Override
	public BaseOperation<Integer, Integer> createOperation(int index, Integer input)  {
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
