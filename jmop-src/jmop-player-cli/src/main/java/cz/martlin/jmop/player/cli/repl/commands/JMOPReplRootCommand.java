package cz.martlin.jmop.player.cli.repl.commands; 

import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * The JMOP root command.
 * Does nothing, just contains bundle of sub-commands.
 * 
 * @author martin
 *
 */
@Command(name = "jmop-cli", //
		description = "The JMOP player command-line interface (CLI). Supports playing of bundles, playlists and tracks, and also simple manipulation with the musicbase.", // 
		sortOptions = false, //
		subcommands = { //
				CommandLine.HelpCommand.class, //
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
				PrintCommands.StatsCommand.class, //
				PlayCommand.class, //
				PlayingCommands.PauseCommand.class, //
				PlayingCommands.ResumeCommand.class, //
				PlayingCommands.StopCommand.class, //
				PlayingCommands.SeekCommand.class, //
				PlayingCommands.NextCommand.class, //
				PlayingCommands.PreviousCommand.class, //
				TheCommandP.class, //
		}) //
public class JMOPReplRootCommand extends AbstractCommand {

	public JMOPReplRootCommand(JMOPPlayer jmop) {
		super(jmop);
	}

}
