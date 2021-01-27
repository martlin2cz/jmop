package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.mixin.BundleMixin;
import cz.martlin.jmop.player.cli.repl.mixin.PlaylistMixin;
import cz.martlin.jmop.player.cli.repl.mixin.TrackMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
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
			System.out.println("PrintCommand.StatusCommand.doRun()");
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
			System.out.println(bundle); //TODO FIXME implementme
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
			System.out.println(playlist); //TODO FIXME implementme
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
			System.out.println(track); //TODO FIXME implementme
		}
	}
}
