package cz.martlin.jmop.player.cli.repl.commands.musicbase;

import cz.martlin.jmop.player.cli.repl.commands.CompositeCommand;
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
