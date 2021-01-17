package cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase;

import cz.martlin.jmop.player.cli.repl.XXX_commands.CompositeCommand;
import picocli.CommandLine.Command;

@Command(name = "rename", //
		subcommands = { //
				RenameBundleCommand.class, //
				RenamePlaylistCommand.class, //
				RenameTrackCommand.class, //
		})
public class RenameCompositeCommand extends CompositeCommand {

	public RenameCompositeCommand() {
	}

}
