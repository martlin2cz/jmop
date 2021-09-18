package cz.martlin.jmop.core.sources.remotes;

import java.io.PrintStream;

import cz.martlin.jmop.core.misc.XXX_ProgressListener;

public class SimpleLoggingListener implements XXX_ProgressListener {

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
