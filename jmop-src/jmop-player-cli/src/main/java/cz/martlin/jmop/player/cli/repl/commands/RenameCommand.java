package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.mixin.BundleOrCurrentMixin;
import cz.martlin.jmop.player.cli.repl.mixin.PlaylistMixin;
import cz.martlin.jmop.player.cli.repl.mixin.TrackMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Parameters;

@Command(name = "rename", subcommands = { //
		CommandLine.HelpCommand.class, //
		RenameCommand.RenameBundleCommand.class, //
		RenameCommand.RenamePlaylistCommand.class, //
		RenameCommand.RenameTrackCommand.class, //
}) //
public class RenameCommand extends AbstractCommand {

	public RenameCommand(JMOPPlayer jmop) {
		super(jmop);
	}

	@Command(name = "bundle")
	public static class RenameBundleCommand extends AbstractRunnableCommand {

		@Mixin()
		private BundleOrCurrentMixin bundle;
		
		@Parameters(arity = "1")
		private String newName;

		public RenameBundleCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			jmop.musicbase().renameBundle(bundle, newName);
		}
	}

	@Command(name = "playlist")
	public static class RenamePlaylistCommand extends AbstractRunnableCommand {

		@Mixin
		private PlaylistMixin playlist;

		@Parameters(arity = "1")
		private String newName;

		public RenamePlaylistCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Playlist playlist = this.playlist.getPlaylist();

			jmop.musicbase().renamePlaylist(playlist, newName);
		}
	}

	@Command(name = "track")
	public static class RenameTrackCommand extends AbstractRunnableCommand {

		@Mixin
		private TrackMixin track;

		@Parameters(arity = "1")
		private String newTitle;

		public RenameTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Track track = this.track.getTrack();
			
			jmop.musicbase().renameTrack(track, newTitle);
		}
	}

}
