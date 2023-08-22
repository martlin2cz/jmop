package cz.martlin.jmop.core.misc.ops;

/**
 * Abstract common short operation.
 * 
 * @author martin
 *
 * @param <InT>
 * @param <OutT>
 */
public abstract class AbstractShortOperation<InT, OutT> implements BaseShortOperation<InT, OutT> {

	private final String name;
	private final InT input;

	public AbstractShortOperation(String name, InT input) {
		super();
		this.name = name;
		this.input = input;
	}

	@Override
	public String getName() {
		return name;
	}

	///////////////////////////////////////////////////////

	@Override
	public String getInputDataAsString() {
		return getInputDataAsString(input);
	}

	public abstract String getInputDataAsString(InT input);

	///////////////////////////////////////////////////////

	@Override
	public OutT run(BaseProgressListener listener) throws Exception {
		return run(input, listener);
	}

	protected abstract OutT run(InT input, BaseProgressListener listener) throws Exception;

	///////////////////////////////////////////////////////

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
		AbstractShortOperation<?,?> other = (AbstractShortOperation<?,?>) obj;
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
		return "AbstractShortOperation [name=" + name + ", input=" + input + "]";
	}

}
