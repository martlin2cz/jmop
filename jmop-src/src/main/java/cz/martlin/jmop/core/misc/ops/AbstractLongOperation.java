package cz.martlin.jmop.core.misc.ops;

import java.util.function.Function;

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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getInputDataAsString() {
		return dataToString.apply(input);
	}

	@Override
	public void reportProgress(BaseProgressListener listener, double progress) {
		listener.reportProgressChanged(progress);

	}

}
