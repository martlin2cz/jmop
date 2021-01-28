package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.mixin.BundleOrCurrentMixin;
import cz.martlin.jmop.player.cli.repl.mixin.PlaylistMixin;
import cz.martlin.jmop.player.cli.repl.mixin.TrackMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Parameters;

@Command(name = "play", subcommands = { //
		PlayCommand.PlayBundleCommand.class, //
		PlayCommand.PlayPlaylistCommand.class, //
		PlayCommand.PlayTrackCommand.class, //
}) //
public class PlayCommand extends AbstractRunnableCommand {

	@Parameters(arity = "0..1")
	private TrackIndex index;

	public PlayCommand(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	protected void doRun() {
		if (index == null) {
			jmop.playing().play();
		} else {
			jmop.playing().play(index);
		}
	}

	@Command(name = "bundle")
	public static class PlayBundleCommand extends AbstractRunnableCommand {

		@Mixin
		private BundleOrCurrentMixin bundle;

		public PlayBundleCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() {
			Bundle bundle = this.bundle.getBundle();
			jmop.playing().play(bundle);
		}
	}

	@Command(name = "playlist")
	public static class PlayPlaylistCommand extends AbstractRunnableCommand {

		@Mixin
		private PlaylistMixin playlist;

		public PlayPlaylistCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() {
			Playlist playlist = this.playlist.getPlaylist();
			jmop.playing().play(playlist);
		}
	}

	@Command(name = "track")
	public static class PlayTrackCommand extends AbstractRunnableCommand {

		@Mixin
		private TrackMixin track;

		public PlayTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() {
			Track track = this.track.getTrack();
			jmop.playing().play(track);
		}
	}

}
