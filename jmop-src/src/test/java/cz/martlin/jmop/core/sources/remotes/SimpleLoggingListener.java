package cz.martlin.jmop.core.sources.remotes;

import java.io.PrintStream;

import cz.martlin.jmop.core.misc.ProgressListener;

public class SimpleLoggingListener implements ProgressListener {

	private final PrintStream to;

	public SimpleLoggingListener(PrintStream to) {
		super();
		this.to = to;
	}

	@Override
	public void progressChanged(double value) {
		to.println(value);
	}

}
