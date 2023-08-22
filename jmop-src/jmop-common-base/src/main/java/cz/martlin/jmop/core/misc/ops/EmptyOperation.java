package cz.martlin.jmop.core.misc.ops;

/**
 * Operation doing nothing.
 * @author martin
 *
 * @param <T>
 */
public class EmptyOperation<T> implements BaseOperation<T, T> {

	private final T input;

	public EmptyOperation(T input) {
		super();
		this.input = input;
	}

	@Override
	public String getName() {
		return "-";
	}

	@Override
	public String getInputDataAsString() {
		return "-";
	}

	@Override
	public T run(BaseProgressListener listener) throws Exception {
		return input;
	}

	@Override
	public String toString() {
		return "EmptyOperation [input=" + input + "]";
	}
	
	

}
