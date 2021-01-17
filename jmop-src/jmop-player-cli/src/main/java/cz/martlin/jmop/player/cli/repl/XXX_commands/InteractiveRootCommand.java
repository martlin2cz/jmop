package cz.martlin.jmop.player.cli.repl.XXX_commands;

import cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase.CreateCompositeCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase.ListBundlesCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase.ListPlaylistsCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase.ListTracksCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase.MoveCompositeCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase.RemoveCompositeCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase.RenameCompositeCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.NextCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.PauseCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.PlayCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.PlaylistCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.PreviousCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.ResumeCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.SeekCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.StatusCommand;
import cz.martlin.jmop.player.cli.repl.XXX_commands.playing.StopCommand;
import cz.martlin.jmop.player.cli.repl.commands.JMOPReplRootCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
/**
 * 
 * @author martin
 * @deprecated replaced by {@link JMOPReplRootCommand}
 */
@Deprecated
@Command(name = "jmop-cli", //
		subcommands = { //
				ListBundlesCommand.class, //
				ListPlaylistsCommand.class, //
				ListTracksCommand.class, //
				PlaylistCommand.class, //
				StatusCommand.class, //
				PlayCommand.class, //
				PauseCommand.class, //
				ResumeCommand.class, //
				StopCommand.class, //
				SeekCommand.class, //
				NextCommand.class, //
				PreviousCommand.class, //
				CreateCompositeCommand.class, //
				RenameCompositeCommand.class, //
				MoveCompositeCommand.class, //
				RemoveCompositeCommand.class, //
		})
public class InteractiveRootCommand extends CompositeCommand {

	private final JMOPPlayer jmop;

	public InteractiveRootCommand(JMOPPlayer jmop) {
		super();
		this.jmop = jmop;
	}

	public JMOPPlayer fascade() {
		return jmop;
	}
}
