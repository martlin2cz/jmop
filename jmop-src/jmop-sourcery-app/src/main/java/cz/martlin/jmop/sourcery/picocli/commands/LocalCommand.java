package cz.martlin.jmop.sourcery.picocli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(name = "local", //
	description = "Imports from the local place, i.e. this computer.", //
	subcommands = { //
		ImportFromDirectoryOrFileCommand.class, //
		HelpCommand.class
	})
public class LocalCommand{

	public LocalCommand() {
		super();
	}
}
