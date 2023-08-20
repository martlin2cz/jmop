package cz.martlin.jmop.sourcery.picocli.commands;

import picocli.CommandLine.Command;

@Command(name = "local")
public class LocalCommand implements Runnable {

	public LocalCommand() {
		super();
	}
	
	@Override
	public void run() {
		throw new UnsupportedOperationException("Local sources not supported yet");
	}
}
