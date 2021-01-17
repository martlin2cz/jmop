package cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase;

import cz.martlin.jmop.player.cli.repl.XXX_commands.CompositeCommand;
import picocli.CommandLine.Command;

@Command(name = "create", //
		subcommands = { //
				CreateBundleCommand.class, //
				CreatePlaylistCommand.class, //
				CreateTrackCommand.class, //
		})
public class CreateCompositeCommand extends CompositeCommand {

	public CreateCompositeCommand() {
	}
}
