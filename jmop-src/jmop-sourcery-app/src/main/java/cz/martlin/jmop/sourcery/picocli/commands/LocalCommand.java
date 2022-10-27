package cz.martlin.jmop.sourcery.picocli.commands;

import picocli.CommandLine.Command;

@Command(name = "local", subcommands = 
	ImportFromDirectoryOrFileCommand.class
)
public class LocalCommand{

	public LocalCommand() {
		super();
	}
}
