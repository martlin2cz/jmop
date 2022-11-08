package cz.martlin.jmop.sourcery.picocli.commands.structural;

import cz.martlin.jmop.sourcery.picocli.commands.impls.RemoteAddCommand;
import cz.martlin.jmop.sourcery.picocli.commands.impls.ScanAndDownloadCommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(name = "remote", //
	description = "Imports from or via the particular remote music services.", //
	subcommands = { //
		RemoteAddCommand.class, //
		ScanAndDownloadCommand.class, //
		HelpCommand.class //
	})
public class RemoteCommand {

}
