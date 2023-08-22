package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.mixin.BundleOrCurrentMixin;
import cz.martlin.jmop.player.cli.repl.mixin.PlaylistMixin;
import cz.martlin.jmop.player.cli.repl.mixin.TrackMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Mixin;

/**
 * The remove ... commands.
 * 
 * @author martin
 *
 */
@Command(name = "delete", aliases = { "d" }, //
	description = "Deletes the given bundle, playlist or track", //
	subcommands = { //
		CommandLine.HelpCommand.class, //
		RemoveCommand.RemoveBundleCommand.class, //
		RemoveCommand.RemovePlaylistCommand.class, //
		RemoveCommand.RemoveTrackCommand.class, //
}) //
//TODO rename the class (and all its sub-commands) to DeleteCommand
public class RemoveCommand extends AbstractCommand {

	public RemoveCommand(JMOPPlayer jmop) {
		super(jmop);
	}
	
	/**
	 * The remove bundle command.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "bundle", aliases = { "b" }, //
		description = "Deletes the given bundle", //
		subcommands =  HelpCommand.class )
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

	/**
	 * The remove playlist command.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "playlist", aliases = { "p" }, //
		description = "Deletes the given playlist", //
		subcommands =  HelpCommand.class )
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

	/**
	 * The remove track command.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "track", aliases = { "t" }, //
		description = "Deletes the given track. Removes it from all playlists", //
		subcommands =  HelpCommand.class )
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
