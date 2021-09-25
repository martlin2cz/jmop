package cz.martlin.jmop.player.cli.repl.commands;

import java.io.File;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.mixin.BundleOrCurrentMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import javafx.util.Duration;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "create", subcommands = { //
		CommandLine.HelpCommand.class, //
		CreateCommand.CreateBundleCommand.class, //
		CreateCommand.CreatePlaylistCommand.class, //
		CreateCommand.CreateTrackCommand.class, //
}) //
public class CreateCommand extends AbstractCommand {

	public CreateCommand(JMOPPlayer jmop) {
		super(jmop);
	}

	@Command(name = "bundle")
	public static class CreateBundleCommand extends AbstractRunnableCommand {

		@Parameters(arity = "1")
		private String name;

		public CreateBundleCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			jmop.musicbase().createNewBundle(name);
		}
	}

	@Command(name = "playlist")
	public static class CreatePlaylistCommand extends AbstractRunnableCommand {

		@Mixin
		private BundleOrCurrentMixin bundle;

		@Parameters(arity = "1")
		private String name;

		public CreatePlaylistCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();

			jmop.musicbase().createNewPlaylist(bundle, name);
		}
	}

	@Command(name = "track")
	public static class CreateTrackCommand extends AbstractRunnableCommand {

		@Mixin
		private BundleOrCurrentMixin bundle;

		@Parameters(arity = "1")
		private String title;

		@Option(names = "description", required = false)
		private String description;

		@Option(names = "identifier", required = false)
		private String identifier;

		@Option(names = "duration", required = true)
		private Duration duration;

		@Option(names = "file", required = false)
		private File sourceFile;
		
		public CreateTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			TrackData data = new TrackData(identifier, title, description, duration);

			jmop.musicbase().createNewTrack(bundle, data, sourceFile);
		}
	}

}
