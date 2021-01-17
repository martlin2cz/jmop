package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.mixin.BundleMixin;
import cz.martlin.jmop.player.cli.repl.mixin.PlaylistMixin;
import cz.martlin.jmop.player.cli.repl.mixin.TrackMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

@Command(name = "move", subcommands = { //
		MoveCommand.MovePlaylistCommand.class, //
		MoveCommand.MoveTrackCommand.class, //
}) //
public class MoveCommand extends AbstractCommand {

	public MoveCommand(JMOPPlayer jmop) {
		super(jmop);
	}


	@Command(name = "playlist")
	public static class MovePlaylistCommand extends AbstractRunnableCommand {

		@Mixin
		private PlaylistMixin playlist;

		@Mixin
		private BundleMixin newBundle;

		public MovePlaylistCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() throws JMOPMusicbaseException {
			Playlist playlist = this.playlist.getPlaylist();
			Bundle newBundle = this.newBundle.getBundle();
			
			jmop.musicbase().movePlaylist(playlist, newBundle);
		}
	}

	@Command(name = "track")
	public static class MoveTrackCommand extends AbstractRunnableCommand {

		@Mixin
		private TrackMixin track;

		@Mixin
		private BundleMixin newBundle;

		public MoveTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() throws JMOPMusicbaseException {
			Track track = this.track.getTrack();
			Bundle newBundle = this.newBundle.getBundle();
			
			jmop.musicbase().moveTrack(track, newBundle);
		}
	}

}
