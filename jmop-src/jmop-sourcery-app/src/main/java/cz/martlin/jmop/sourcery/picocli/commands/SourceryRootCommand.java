package cz.martlin.jmop.sourcery.picocli.commands;

import java.io.File;

import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;

@Command(name = "sourcery", //
	description = "The sourcery, a tool for importing the music data (tracks, playlist and bundles) from either the computer location or via the remote services.", //
	subcommands = { // 
			LocalCommand.class, //
			RemoteCommand.class, //
			HelpCommand.class // 
	})
public class SourceryRootCommand {

	@Option(names = { "-mb", "--musicbase" }, required = true, //
		description = "The path to the musicbase storage directory.")
	private File root;
	
	public SourceryRootCommand() {
		super();
		
		JMOPSourceryProvider.get().setRoot(() -> root);
	}
	
	
	
}
