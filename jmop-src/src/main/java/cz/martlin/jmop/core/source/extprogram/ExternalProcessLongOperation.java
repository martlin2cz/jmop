package cz.martlin.jmop.core.source.extprogram;

import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;

public class ExternalProcessLongOperation<InT, OutT> implements BaseLongOperation<InT, OutT> {

	private final String name;
	private final String input;
	private final AbstractProcessEncapsulation process;
	
	public ExternalProcessLongOperation(String name, String input, AbstractProcessEncapsulation process) {
		super();
		this.name = name;
		this.input = input;
		this.process = process;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getInputDataAsString() {
		return input;
	}

	@Override
	public OutT run(BaseProgressListener listener) throws Exception {
		process.run(listener);
		return (OutT) (Object) 42; //FIXME
	}

	@Override
	public void reportProgress(double progress) {
		process.reportProgress(listener, progress);
	}

	@Override
	public void terminate() {
		process.terminate();
	}
}
