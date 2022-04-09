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
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

public class PrintCommands {

	@Command(name = "status", aliases = { "st" }, //
		description = "Prints the current player status", //
		subcommands =  HelpCommand.class )
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

	@Command(name = "bar", aliases = { "B" }, //
		description = "Prints the track play progressbar", //
		subcommands =  HelpCommand.class )
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

	@Command(name = "bundle", aliases = { "b" }, //
		description = "Prints the info about the current or provided bundle", //
		subcommands =  HelpCommand.class )
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

	@Command(name = "playlist", scope = ScopeType.INHERIT, aliases = { "P" },
		description = "Prints info about the (current or provided) playlist or alters it", //
		subcommands = { //
			CommandLine.HelpCommand.class, //
			ModifyPlaylistCommands.AddTrackCommand.class, //
			ModifyPlaylistCommands.InsertTrackCommand.class, //
			ModifyPlaylistCommands.RemoveTrackCommand.class, //
	}) //
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


	@Command(name = "track", aliases = { "t" }, //
		description = "Prints the info about the given or current track", //
		subcommands =  HelpCommand.class )
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

	@Command(name = "statistics", aliases = { "stats" }, //
		description = "Prints various statistics about the playing and musicbase", //
		subcommands =  HelpCommand.class )
	public static class StatsCommand extends AbstractRunnableCommand {

		private final StatsPrinter printer;

		@Option(names = { "minimal", "short" }, description = "Prints just the basic statistics")
		private boolean minimal;
		
		@Option(names = { "full", "long" }, description = "Prints complete statistics of all bundles, playlists and tracks")
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
