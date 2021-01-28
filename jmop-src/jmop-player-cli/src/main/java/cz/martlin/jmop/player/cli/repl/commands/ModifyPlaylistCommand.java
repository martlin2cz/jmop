package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.commands.PrintCommands.PlaylistInfoCommand;
import cz.martlin.jmop.player.cli.repl.mixin.TrackMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

public class ModifyPlaylistCommand {
	
	@Command(name = "add")
	public static class AddTrackCommand extends AbstractRunnableCommand {
		
//		@Mixin
//		private PlaylistMixin playlist;
		
		@ParentCommand
		private PlaylistInfoCommand parent;
		
		
		@Mixin
		private TrackMixin track;

		public AddTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Playlist playlist = this.parent.playlist.getPlaylist();
			Track track = this.track.getTrack();
			
			PlaylistModifier mod = jmop.musicbase().modifyPlaylist(playlist);
			mod.append(track);
		}
	}
	
	@Command(name = "insert")
	public static class InsertTrackCommand extends AbstractRunnableCommand {
		
//		@Mixin
//		private PlaylistMixin playlist;
		
		@ParentCommand
		private PlaylistInfoCommand parent;
		
		@Parameters(arity = "1")
		private TrackIndex before;
		
		@Mixin
		private TrackMixin track;

		public InsertTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Playlist playlist = this.parent.playlist.getPlaylist();
			Track track = this.track.getTrack();
			
			PlaylistModifier mod = jmop.musicbase().modifyPlaylist(playlist);
			mod.insertBefore(track, before);
		}
	}


	@Command(name = "remove")
	public static class RemoveTrackCommand extends AbstractRunnableCommand {
		
//		@Mixin
//		private PlaylistMixin playlist;
		
		@ParentCommand
		private PlaylistInfoCommand parent;
		
		@Mixin
		private TrackMixin track;

		public RemoveTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Playlist playlist = this.parent.playlist.getPlaylist();
			Track track = this.track.getTrack();
			
			PlaylistModifier mod = jmop.musicbase().modifyPlaylist(playlist);
			mod.removeAll(track);
		}
	}
}
