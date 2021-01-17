package cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase;

import cz.martlin.jmop.player.cli.repl.XXX_commands.CompositeCommand;
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
