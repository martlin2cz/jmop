package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Command;

@Command(name = "jmop-cli", //
		subcommands = { //
				ListCommand.class, //
				CreateCommand.class, //
				RenameCommand.class, //
				MoveCommand.class, //
				RemoveCommand.class, //
				PrintCommands.StatusCommand.class, //
				PrintCommands.BarCommand.class, //
				PrintCommands.BundleInfoCommand.class, //
				PrintCommands.PlaylistInfoCommand.class, //
				PrintCommands.TrackInfoCommand.class, //
				PlayCommand.class, //
				PlayingCommands.PauseCommand.class, //
				PlayingCommands.ResumeCommand.class, //
				PlayingCommands.StopCommand.class, //
				PlayingCommands.SeekCommand.class, //
				PlayingCommands.NextCommand.class, //
				PlayingCommands.PreviousCommand.class, //
		}) //
public class JMOPReplRootCommand extends AbstractCommand {

	public JMOPReplRootCommand(JMOPPlayer jmop) {
		super(jmop);
	}

}
