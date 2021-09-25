package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import javafx.util.Duration;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

public class PlayingCommands {

	@Command(name = "pause")
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

	@Command(name = "resume")
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
	
	@Command(name = "stop")
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
	
	@Command(name = "seek")
	public static class SeekCommand extends AbstractRunnableCommand {

		@Parameters(arity = "1")
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
	
	@Command(name = "next")
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
	
	@Command(name = "previous")
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
