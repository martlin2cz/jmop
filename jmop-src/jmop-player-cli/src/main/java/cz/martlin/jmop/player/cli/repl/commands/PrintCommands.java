package cz.martlin.jmop.player.cli.repl.commands;

import java.util.Map;
import java.util.Set;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.cli.repl.mixin.BundleMixin;
import cz.martlin.jmop.player.cli.repl.mixin.PlaylistMixin;
import cz.martlin.jmop.player.cli.repl.mixin.TrackMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.ScopeType;

public class PrintCommands {

	@Command(name = "status")
	public static class StatusCommand extends AbstractRunnableCommand {
		
		public StatusCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle currentBundle = jmop.playing().currentBundle();
			
			if (currentBundle == null) {
				PrintUtil.print("Nothing beeing played");
				return;
			} else {
				printPlaying();
			}
		}

		private void printPlaying() {
			Bundle currentBundle = jmop.playing().currentBundle();
			Playlist currentPlaylist = jmop.playing().currentPlaylist();
			Track currentTrack = jmop.playing().currentTrack();
			
			PrintUtil.print("Playing", currentPlaylist, "playlist from the", currentBundle, "bundle");
			
			if (currentTrack != null) {
				PrintUtil.print("Current track:", currentTrack.getTitle());	
			} else {
				PrintUtil.print("No current track.");
			}
			
			PlayerStatus currentStatus = jmop.playing().currentStatus();
			PrintUtil.print("The player is " + currentStatus);
			
			if (currentTrack != null) {
				Duration currentTime = jmop.playing().currentDuration();
				PrintUtil.print("Current time is", currentTime, "out of", currentTrack.getDuration());
			}
		}
	}
	
	@Command(name = "bundle")
	public static class BundleInfoCommand extends AbstractRunnableCommand {
		
		@Mixin
		private BundleMixin bundle;
		
		public BundleInfoCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			print(bundle);
		}

		private void print(Bundle bundle) {
			PrintUtil.print("Bundle", bundle);
			printMetadata(bundle.getMetadata());
			
			Set<Playlist> playlists = jmop.musicbase().playlists(bundle);
			Set<Track> tracks = jmop.musicbase().tracks(bundle);
			
			PrintUtil.print(playlists.size(), "playlists,", //
					tracks.size(), "tracks"); //
			
			PrintUtil.emptyLine();
		}
	}

	@Command(name = "playlist", scope = ScopeType.INHERIT, subcommands = {
			ModifyPlaylistCommand.AddTrackCommand.class, //
			ModifyPlaylistCommand.InsertTrackCommand.class, //
			ModifyPlaylistCommand.RemoveTrackCommand.class, //
	})
	public static class PlaylistInfoCommand extends AbstractRunnableCommand {
		
		@Mixin 
		protected PlaylistMixin playlist;

		public PlaylistInfoCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Playlist playlist = this.playlist.getPlaylist();
			print(playlist);
		}
		
		private void print(Playlist playlist) {
			PrintUtil.print("Playlist", playlist);
			printMetadata(playlist.getMetadata());
				
			Map<TrackIndex, Track> tracks = playlist.getTracks().asIndexedMap();
			for (TrackIndex index: tracks.keySet()) {
				Track track = tracks.get(index);
				
				if (index.notEqual(playlist.getCurrentTrackIndex())) {
					PrintUtil.print(index, "", track, "(", track.getDuration(), ")");
				} else {
					PrintUtil.print(">", "", track, "(", track.getDuration(), ")");
				}
			}
			
			PrintUtil.emptyLine();
		}
	}

	@Command(name = "track")
	public static class TrackInfoCommand extends AbstractRunnableCommand {
		
		@Mixin
		private TrackMixin track;

		public TrackInfoCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Track track = this.track.getTrack();
			print(track);
		}
		
		private void print(Track track) {
			PrintUtil.print("Track", track);
			printMetadata(track.getMetadata());
			PrintUtil.print("Duration:", track.getDuration());
			PrintUtil.print("ID:", track.getIdentifier());
			PrintUtil.print("Description:", track.getDescription());
			PrintUtil.emptyLine();
		}
	}
	

	private static void printMetadata(Metadata metadata) {
		PrintUtil.print("Created:", metadata.getCreated(), //
				", played:", metadata.getNumberOfPlays(), "x", //
				", last played:", metadata.getLastPlayed());
	}
}
