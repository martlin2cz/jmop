package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.helpers.BarPrinter;
import cz.martlin.jmop.player.cli.repl.helpers.ElementsPrinter;
import cz.martlin.jmop.player.cli.repl.helpers.StatsPrinter;
import cz.martlin.jmop.player.cli.repl.helpers.StatusPrinter;
import cz.martlin.jmop.player.cli.repl.mixin.BundleOrCurrentMixin;
import cz.martlin.jmop.player.cli.repl.mixin.PlaylistMixin;
import cz.martlin.jmop.player.cli.repl.mixin.TrackMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

public class PrintCommands {

	@Command(name = "status")
	public static class StatusCommand extends AbstractRunnableCommand {

		private final StatusPrinter printer;
		
		public StatusCommand(JMOPPlayer jmop) {
			super(jmop);
			this.printer = new StatusPrinter(jmop);
		}

		@Override
		protected void doRun() {
			if (!jmop.status().isPlayingSomePlaylist()) {
				reject("Nothing beeing played");
			}

			printer.printPlaying();
		}

	}

	@Command(name = "bar")
	public static class BarCommand extends AbstractRunnableCommand {
		private final BarPrinter printer;

		public BarCommand(JMOPPlayer jmop) {
			super(jmop);
			
			this.printer = new BarPrinter(jmop);
		}

		@Override
		protected void doRun() {
			if (!jmop.status().isPlayingSomePlaylist()) {
				reject("Nothing beeing played");
			}

			printer.printBar();
		}

	}

	@Command(name = "bundle")
	public static class BundleInfoCommand extends AbstractRunnableCommand {

		private final ElementsPrinter printer;
		
		@Mixin
		private BundleOrCurrentMixin bundle;

		public BundleInfoCommand(JMOPPlayer jmop) {
			super(jmop);
			
			this.printer = new ElementsPrinter(jmop);
		}

		@Override
		protected void doRun() {
			Bundle bundle = this.bundle.getBundle();
			printer.print(bundle);
		}

	}

	@Command(name = "playlist", scope = ScopeType.INHERIT, subcommands = { //
			ModifyPlaylistCommands.AddTrackCommand.class, //
			ModifyPlaylistCommands.InsertTrackCommand.class, //
			ModifyPlaylistCommands.RemoveTrackCommand.class, //
	})
	public static class PlaylistInfoCommand extends AbstractRunnableCommand {

		private final ElementsPrinter printer;
		
		@Mixin
		protected PlaylistMixin playlist;

		public PlaylistInfoCommand(JMOPPlayer jmop) {
			super(jmop);
			
			this.printer = new ElementsPrinter(jmop);
		}

		@Override
		protected void doRun() {
			Playlist playlist = this.playlist.getPlaylist();
			printer.print(playlist);
		}
	}


	@Command(name = "track")
	public static class TrackInfoCommand extends AbstractRunnableCommand {

		private final ElementsPrinter printer;
		
		@Mixin
		private TrackMixin track;

		public TrackInfoCommand(JMOPPlayer jmop) {
			super(jmop);
			
			this.printer = new ElementsPrinter(jmop);
		}

		@Override
		protected void doRun() {
			Track track = this.track.getTrack();
			printer.print(track);
		}

	}

	@Command(name = "stats")
	public static class StatsCommand extends AbstractRunnableCommand {

		private final StatsPrinter printer;

		@Option(names = { "minimal", "short" })
		private boolean minimal;
		
		@Option(names = { "full", "long" })
		private boolean full;
		

		public StatsCommand(JMOPPlayer jmop) {
			super(jmop);
			this.printer = new StatsPrinter(jmop);
		}

		@Override
		protected void doRun() {
			//TODO ensure ether minimal OR full are given, no both at the same time
			
			Bundle bundle = null; //TODO optionally specify the bundle
			printer.print(bundle, this.minimal, this.full);
		}
	}

}
