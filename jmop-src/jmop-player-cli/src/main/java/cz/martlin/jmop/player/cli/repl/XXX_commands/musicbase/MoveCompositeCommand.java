package cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase;

import cz.martlin.jmop.player.cli.repl.XXX_commands.CompositeCommand;
import picocli.CommandLine.Command;

@Command(name = "move", //
		subcommands = { //
				MovePlaylistCommand.class, //
				MoveTrackCommand.class, //
		})
public class MoveCompositeCommand extends CompositeCommand {

	public MoveCompositeCommand() {
	}

}
