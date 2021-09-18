package cz.martlin.jmop.core.source.extprogram;

import java.util.function.Function;
import java.util.function.Supplier;

import cz.martlin.jmop.core.misc.ops.AbstractLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;

public class ExternalProcessLongOperation<InT, OutT> extends AbstractLongOperation<InT, OutT> {

	private final AbstractProcessEncapsulation process;
	private final Supplier<OutT> resultCreator;

	public ExternalProcessLongOperation(String name, InT input, AbstractProcessEncapsulation process,
			Function<InT, String> dataToString, Supplier<OutT> resultCreator) {
		
		super(name, input, dataToString);

		this.process = process;
		this.resultCreator = resultCreator;
	}

	@Override
	public OutT run(BaseProgressListener listener) throws Exception {
		process.run(listener);
		return resultCreator.get();
	}

	@Override
	public void reportProgress(BaseProgressListener listener, double progress) {
		process.reportProgress(listener, progress);
	}

	@Override
	public void terminate() {
		process.terminate();
	}
}
