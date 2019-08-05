package cz.martlin.jmop.core.misc.ops;

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
}
