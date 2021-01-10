package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.player.cli.repl.commands.musicbase.CreateCompositeCommand;
import cz.martlin.jmop.player.cli.repl.commands.musicbase.ListBundlesCommand;
import cz.martlin.jmop.player.cli.repl.commands.musicbase.ListPlaylistsCommand;
import cz.martlin.jmop.player.cli.repl.commands.musicbase.ListTracksCommand;
import cz.martlin.jmop.player.cli.repl.commands.musicbase.MoveCompositeCommand;
import cz.martlin.jmop.player.cli.repl.commands.musicbase.RemoveCompositeCommand;
import cz.martlin.jmop.player.cli.repl.commands.musicbase.RenameCompositeCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.NextCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.PauseCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.PlayCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.PlaylistCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.PreviousCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.ResumeCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.SeekCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.StatusCommand;
import cz.martlin.jmop.player.cli.repl.commands.playing.StopCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

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
