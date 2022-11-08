package cz.martlin.jmop.sourcery.picocli.commands.structural;

import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(name = "local", //
	description = "Loads/adds/imports the musicbase data from the local place (this computer).", //
	subcommands = { //
		ImportCommand.class, //
		HelpCommand.class
	})
public class LocalCommand{

	public LocalCommand() {
		super();
	}
}
