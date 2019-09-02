package cz.martlin.jmop.core.misc.ops;

import java.util.function.Function;

/**
 * The short operation. Its implementation is just simple - it simply runs the
 * specified action.
 * 
 * @author martin
 *
 */
public class SimpleShortOperation<InT, OutT> extends AbstractShortOperation<InT, OutT> {

	private final Function<InT, String> dataToString;
	private final FunctionWithException<InT, OutT> run;

	public SimpleShortOperation(String name, InT input, Function<InT, String> dataToString,
			FunctionWithException<InT, OutT> run) {
		super(name, input);
		this.dataToString = dataToString;
		this.run = run;
	}

	@Override
	public String getInputDataAsString(InT input) {
		return dataToString.apply(input);
	}

	@Override
	protected OutT run(InT input, BaseProgressListener listener) throws Exception {
		return run.run(input);
	}

}
