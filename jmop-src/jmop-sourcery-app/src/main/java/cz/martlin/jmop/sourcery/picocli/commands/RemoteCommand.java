package cz.martlin.jmop.sourcery.picocli.commands;

import picocli.CommandLine.Command;

@Command(name = "remote", subcommands = { //
		RemoteAddCommand.class, //
		ScanAndDownloadCommand.class //
})
public class RemoteCommand {

}
