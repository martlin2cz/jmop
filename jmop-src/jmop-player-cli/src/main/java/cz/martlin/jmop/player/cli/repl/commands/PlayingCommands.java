package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import javafx.util.Duration;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Parameters;

public class PlayingCommands {

	@Command(name = "pause", /* alias done by the P command */ //
		description = "Pauses the playing", //
		subcommands =  HelpCommand.class )
	public static class PauseCommand extends AbstractRunnableCommand {

		public PauseCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			if (!jmop.status().isPlaying()) {
				reject("Not playing");
			}
			
			jmop.playing().pause();
		}
	}

	@Command(name = "resume", /* aslias done by the P command */ //
		description = "Resumes the plaing", //
		subcommands =  HelpCommand.class )
	public static class ResumeCommand extends AbstractRunnableCommand {

		public ResumeCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			if (!jmop.status().isPaused()) {
				reject("Not paused");
			}
			
			jmop.playing().resume();
		}
	}
	
	@Command(name = "stop", aliases = { "s" }, //
		description = "Stops the playing", //
		subcommands =  HelpCommand.class )
	public static class StopCommand extends AbstractRunnableCommand {

		public StopCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			if (!jmop.status().isPlayingSomeTrack()) {
				reject("Not playing");
			}
			
			jmop.playing().stop();
		}
	}
	
	@Command(name = "seek", /* TODO: alias via the P command? */ //
		description = "Seeks (goes to specified duration)", //
		subcommands =  HelpCommand.class )
	public static class SeekCommand extends AbstractRunnableCommand {

		@Parameters(arity = "1", paramLabel="DURATION", //
				description = "The time to seek to")
		private Duration duration;
		
		public SeekCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			if (!jmop.status().isPlayingSomeTrack()) {
				reject("Not playing");
			}
			
			jmop.playing().seek(duration);
		}
	}
	
	@Command(name = "next", aliases = { "n" }, //
		description = "Goes to next track in the playlist", //
		subcommands =  HelpCommand.class )
	public static class NextCommand extends AbstractRunnableCommand {

		public NextCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			if (!jmop.status().hasNext()) {
				reject("No next track");
			}
			
			jmop.playing().toNext();	
		}
	}
	
	@Command(name = "previous", aliases = { "N" }, //
		description = "Goes to previous track in the playlist", //
		subcommands =  HelpCommand.class )
	public static class PreviousCommand extends AbstractRunnableCommand {

		public PreviousCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			if (!jmop.status().hasPrevious()) {
				reject("No previous track");
			}

			jmop.playing().toPrevious();	
		}
	}

}
