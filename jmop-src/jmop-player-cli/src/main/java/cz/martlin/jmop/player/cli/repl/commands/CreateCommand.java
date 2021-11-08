package cz.martlin.jmop.player.cli.repl.commands;

import java.io.File;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.mixin.BundleOrCurrentMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import javafx.util.Duration;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
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

		@ArgGroup(exclusive = true, multiplicity = "1") //TODO or multiplicity=0..1 (i.e. optional?)
		private FileSpecifier file;
		
		public CreateTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			TrackData data = new TrackData(identifier, title, description, duration);
			TrackFileCreationWay trackFileHow = file.getHow();
			File trackFile = file.getFile();
			
			jmop.musicbase().createNewTrack(bundle, data, trackFileHow, trackFile);
		}
		
		public static class FileSpecifier {

			@Option(names="copy-file")
			private File copyFile;
			
			@Option(names="move-file")
			private File moveFile;
			
			@Option(names="link-file")
			private File linkFile;
			
			@Option(names="set-file")
			private File setFile;
			
			@Option(names="no-file")
			private boolean noFile;
			
			public TrackFileCreationWay getHow() {
				if (copyFile != null) {
					return TrackFileCreationWay.COPY_FILE;
				}
				if (moveFile != null) {
					return TrackFileCreationWay.MOVE_FILE;
				}
				if (linkFile != null) {
					return TrackFileCreationWay.LINK_FILE;
				}
				if (setFile != null) {
					return TrackFileCreationWay.JUST_SET;
				}
				if (noFile) {
					return TrackFileCreationWay.NO_FILE;
				}
				return TrackFileCreationWay.NO_FILE;
			}

			public File getFile() {
				if (copyFile != null) {
					return copyFile;
				}
				if (moveFile != null) {
					return moveFile;
				}
				if (linkFile != null) {
					return linkFile;
				}
				if (setFile != null) {
					return setFile;
				}
				if (noFile) {
					return null;
				}
				return null;
			}
		}

	}

}
