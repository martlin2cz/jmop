package cz.martlin.jmop.core.operation;

import java.io.PrintStream;

import cz.martlin.jmop.core.misc.ops.BaseProgressListener;

public class PrintingListener implements BaseProgressListener {

	private final PrintStream out;
	
	public PrintingListener(PrintStream out) {
		this.out = out;
	}

	@Override
	public void reportProgressChanged(double progress) {
		out.println("Done " + progress + "%");
	}

}
