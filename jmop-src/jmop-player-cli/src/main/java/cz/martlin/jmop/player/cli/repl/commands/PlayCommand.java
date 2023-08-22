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
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Parameters;

/**
 * The play ... command.
 * 
 * @author martin
 *
 */
@Command(name = "play", /* alias done by the P command */ //
	description = "Starts the playing of the specified bundle, playlist or track",
	subcommands = { //
		CommandLine.HelpCommand.class, //
		PlayCommand.PlayBundleCommand.class, //
		PlayCommand.PlayPlaylistCommand.class, //
		PlayCommand.PlayTrackCommand.class, //
}) //
public class PlayCommand extends AbstractRunnableCommand {

	/**
	 * The track index to play.
	 */
	@Parameters(arity = "0..1", paramLabel="TRACK_INDEX", //
			description = "Plays directly the track of the current playlist")
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

	/**
	 * The play bundle command.
	 * @author martin
	 *
	 */
	@Command(name = "bundle", aliases = { "b" }, //
		description = "Plays the given bundle (all its tracks)", //
		subcommands =  HelpCommand.class )
	public static class PlayBundleCommand extends AbstractRunnableCommand {

		@Mixin
		private BundleOrCurrentMixin bundle;

		public PlayBundleCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() {
			Bundle bundle = this.bundle.getBundle();
			if (jmop.musicbase().isEmpty(bundle)) {
				reject("This bundle has nothing to play");
			}
			jmop.playing().play(bundle);
		}
	}

	/**
	 * The play playlist command.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "playlist", aliases = { "p" }, //
		description = "Plays the given playlist", //
		subcommands =  HelpCommand.class )
	public static class PlayPlaylistCommand extends AbstractRunnableCommand {

		@Mixin
		private PlaylistMixin playlist;

		public PlayPlaylistCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() {
			Playlist playlist = this.playlist.getPlaylist();
			if (jmop.musicbase().isEmpty(playlist)) {
				reject("This playlist has nothing to play");
			}
			jmop.playing().play(playlist);
		}
	}
	
	/**
	 * The play track command.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "track", aliases = { "t" }, //
		description = "Plays just the provided track", //
		subcommands =  HelpCommand.class )
	public static class PlayTrackCommand extends AbstractRunnableCommand {

		@Mixin
		private TrackMixin track;

		public PlayTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() {
			Track track = this.track.getTrack();
//			if (!jmop.musicbase().file(track).exists()) {
//				reject("This track file does not exist");
//			}
			jmop.playing().play(track);
		}
	}

}
