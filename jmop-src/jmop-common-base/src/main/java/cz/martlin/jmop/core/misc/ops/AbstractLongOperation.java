package cz.martlin.jmop.core.misc.ops;

import java.util.function.Function;

/**
 * Abstract common superclass of the long operation.
 * 
 * @author martin
 *
 * @param <InT>
 * @param <OutT>
 */
public abstract class AbstractLongOperation<InT, OutT> implements BaseLongOperation<InT, OutT> {

	private final String name;
	private final InT input;

	private final Function<InT, String> dataToString;

	public AbstractLongOperation(String name, InT input, Function<InT, String> dataToString) {
		super();
		this.name = name;
		this.input = input;
		this.dataToString = dataToString;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String getName() {
		return name;
	}

	public InT getInput() {
		return input;
	}

	@Override
	public String getInputDataAsString() {
		return dataToString.apply(input);
	}

	@Override
	public void reportProgress(BaseProgressListener listener, double progress) {
		listener.reportProgressChanged(progress);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((input == null) ? 0 : input.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractLongOperation<?,?> other = (AbstractLongOperation<?,?>) obj;
		if (input == null) {
			if (other.input != null)
				return false;
		} else if (!input.equals(other.input))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractLongOperation [name=" + name + ", input=" + input + "]";
	}

}
