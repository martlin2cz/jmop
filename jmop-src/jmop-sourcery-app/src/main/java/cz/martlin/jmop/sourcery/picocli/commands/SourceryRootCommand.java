package cz.martlin.jmop.sourcery.picocli.commands;

import java.io.File;

import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "sourcery", 
	subcommands = { LocalCommand.class, RemoteCommand.class })
public class SourceryRootCommand {

	@Option(names = { "-mb", "--musicbase" }, required = true)
	private File root;
	
	public SourceryRootCommand() {
		super();
		
		JMOPSourceryProvider.get().setRoot(() -> root);
	}
	
	
	
}
