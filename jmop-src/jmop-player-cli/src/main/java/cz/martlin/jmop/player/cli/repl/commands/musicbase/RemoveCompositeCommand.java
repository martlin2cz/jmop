package cz.martlin.jmop.player.cli.repl.commands.musicbase;

import cz.martlin.jmop.player.cli.repl.commands.CompositeCommand;
import picocli.CommandLine.Command;

@Command(name = "remove", //
		subcommands = { //
				RemoveBundleCommand.class, //
				RemovePlaylistCommand.class, //
				RemoveTrackCommand.class, //
		})
public class RemoveCompositeCommand extends CompositeCommand {

	public RemoveCompositeCommand() {
	}

}
