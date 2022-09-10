package cz.martlin.jmop.player.cli.repl.commands;

import java.io.File;
import java.net.URI;

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
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "create", aliases = { "c" }, //
	description = "Creates specified bundle, playlist or track in the musicbase", //
	subcommands = { //
		CommandLine.HelpCommand.class, //
		CreateCommand.CreateBundleCommand.class, //
		CreateCommand.CreatePlaylistCommand.class, //
		CreateCommand.CreateTrackCommand.class, //
}) //
public class CreateCommand extends AbstractCommand {

	public CreateCommand(JMOPPlayer jmop) {
		super(jmop);
	}

	@Command(name = "bundle", aliases = { "b" }, //
		description = "Creates bundle of the given name", //
		subcommands =  HelpCommand.class )
	public static class CreateBundleCommand extends AbstractRunnableCommand {

		@Parameters(arity = "1", paramLabel="NAME", //
				description = "The bundle name")
		private String name;

		public CreateBundleCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			jmop.musicbase().createNewBundle(name);
		}
	}

	// TODO move this to separate file
	@Command(name = "playlist", aliases = { "p" }, //
		description = "Creates playlist in the given bundle and of the given name", //
		subcommands =  HelpCommand.class )
	public static class CreatePlaylistCommand extends AbstractRunnableCommand {

		@Mixin(name = "bundle")
		private BundleOrCurrentMixin bundle;

		@Parameters(arity = "1", paramLabel="NAME", //
				description = "The playlist name")
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

	@Command(name = "track", aliases = { "t" }, //
		description = "Creates track in the given bundle with specified parameters", //
		subcommands =  HelpCommand.class )
	public static class CreateTrackCommand extends AbstractRunnableCommand {

		@Mixin(name = "bundle")
		private BundleOrCurrentMixin bundle;

		@Parameters(arity = "1", paramLabel="title", //
			description = "The title of the track")
		private String title;

		@Option(names = "description", required = false, //
			description = "The track description")
		private String description;

		@Option(names = "source", required = false, //
			description = "The track source")
		private URI source;

		@Option(names = "duration", required = true, //
			description = "The duration of the track (in format HH:MM:SS or MM:SS)")
		private Duration duration;

		@ArgGroup(exclusive = true, multiplicity = "1" //TODO or multiplicity=0..1 (i.e. optional?)
			/* description = "The track file specifier (use no-file for none)" */)
		private FileSpecifier file;
		
		public CreateTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			TrackData data = new TrackData(title, description, duration, source);
			TrackFileCreationWay trackFileHow = file.getHow();
			File trackFile = file.getFile();
			
			jmop.musicbase().createNewTrack(bundle, data, trackFileHow, trackFile);
		}
		
		public static class FileSpecifier {

			@Option(names="copy-file", description = "Copies the given file to the musicbase storage location")
			private File copyFile;
			
			@Option(names="move-file", description = "Moves the given file to the musicbase storage location")
			private File moveFile;
			
			@Option(names="link-file", description = "Creates the link in the musicbase storage, pointing to the given file")
			private File linkFile;
			
			@Option(names="set-file", description = "Just marks the given file as the track file, ignoring the musicbase storage")
			private File setFile;
			
			@Option(names="no-file", description = "Does not create or set the track file")
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
