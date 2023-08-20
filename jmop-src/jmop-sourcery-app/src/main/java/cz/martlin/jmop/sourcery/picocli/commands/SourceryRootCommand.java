package cz.martlin.jmop.sourcery.picocli.commands;

import picocli.CommandLine.Command;

@Command(name = "sourcery", 
	subcommands = { LocalCommand.class, RemoteCommand.class })
public class SourceryRootCommand {

}
