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

@Command(name = "remove", subcommands = { //
		CommandLine.HelpCommand.class, //
		RemoveCommand.RemoveBundleCommand.class, //
		RemoveCommand.RemovePlaylistCommand.class, //
		RemoveCommand.RemoveTrackCommand.class, //
}) //
public class RemoveCommand extends AbstractCommand {

	public RemoveCommand(JMOPPlayer jmop) {
		super(jmop);
	}

	@Command(name = "bundle")
	public static class RemoveBundleCommand extends AbstractRunnableCommand {

		@Mixin()
		private BundleOrCurrentMixin bundle;
		
		public RemoveBundleCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			
			jmop.musicbase().removeBundle(bundle);
		}
	}

	@Command(name = "playlist")
	public static class RemovePlaylistCommand extends AbstractRunnableCommand {

		@Mixin
		private PlaylistMixin playlist;

		public RemovePlaylistCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Playlist playlist = this.playlist.getPlaylist();

			jmop.musicbase().removePlaylist(playlist);
		}
	}

	@Command(name = "track")
	public static class RemoveTrackCommand extends AbstractRunnableCommand {

		@Mixin
		private TrackMixin track;

		public RemoveTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Track track = this.track.getTrack();
			
			jmop.musicbase().removeTrack(track);
		}
	}

}
