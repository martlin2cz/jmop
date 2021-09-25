package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.commands.PrintCommands.PlaylistInfoCommand;
import cz.martlin.jmop.player.cli.repl.converters.CustomPlaylistTrackIndexOrCurrentConverter;
import cz.martlin.jmop.player.cli.repl.mixin.TrackOfBundleMixin;
import cz.martlin.jmop.player.engine.dflt.BaseDefaultEngineConfig;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

public class ModifyPlaylistCommands {
	
	@Command(name = "add")
	public static class AddTrackCommand extends AbstractRunnableCommand {
		
//		@Mixin
//		private PlaylistMixin playlist;
		
		@ParentCommand
		private PlaylistInfoCommand parent;
		
		
		@Mixin
		private TrackOfBundleMixin track;

		public AddTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Playlist playlist = this.parent.playlist.getPlaylist();
			rejectAllTracksPlaylist(this, playlist);
			
			Bundle bundle = playlist.getBundle();
			Track track = this.track.getTrack(jmop, bundle);
			
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
		private String beforeStr;
		
		@Mixin
		private TrackOfBundleMixin track;

		public InsertTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun() {
			Playlist playlist = this.parent.playlist.getPlaylist();
			rejectAllTracksPlaylist(this, playlist);
			
			Bundle bundle = playlist.getBundle();
			Track track = this.track.getTrack(jmop, bundle);
			
			TrackIndex before = CustomPlaylistTrackIndexOrCurrentConverter.convert(jmop, playlist, beforeStr);
			
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
		
		@Parameters(arity = "1")
		private String indexStr;

		public RemoveTrackCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Playlist playlist = this.parent.playlist.getPlaylist();
			rejectAllTracksPlaylist(this, playlist);
			
			TrackIndex index = CustomPlaylistTrackIndexOrCurrentConverter.convert(jmop, playlist, indexStr);
			
			PlaylistModifier mod = jmop.musicbase().modifyPlaylist(playlist);
			mod.remove(index);
		}
	}


	public static void rejectAllTracksPlaylist(AbstractRunnableCommand command, Playlist playlist) {
		JMOPPlayer jmop = command.getJMOP();
		BaseDefaultStorageConfig configuration = (BaseDefaultStorageConfig) jmop.config().getConfiguration();
		String allTracksPlaylistName = configuration.getAllTracksPlaylistName();
		
		if (playlist.getName().equals(allTracksPlaylistName)) {
			command.reject("Do not modify the all tracks playlist this way. Modify the whole bundle instead.");
		}
	}
}
