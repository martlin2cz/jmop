package cz.martlin.jmop.player.cli.repl.commands;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.command.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.cli.repl.mixin.BundleOrNoneMixin;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Mixin;

/**
 * The list ... command.
 * @author martin
 *
 */
@Command(name = "list", aliases = { "l" }, //
	description = "Lists all bundles, playlists and/or tracks.", //
	subcommands = { //
		CommandLine.HelpCommand.class, //
		ListCommand.ListBundlesCommand.class, //
		ListCommand.ListPlaylistsCommand.class, //
		ListCommand.ListTracksCommand.class, //
}) //
public class ListCommand extends AbstractCommand {

	public ListCommand(JMOPPlayer jmop) {
		super(jmop);
	}

	/**
	 * The list bundles command.
	 * @author martin
	 *
	 */
	@Command(name = "bundles", aliases = { "b" }, //
		description = "Lists all the bundles in the musicbase", //
		subcommands =  HelpCommand.class )
	public static class ListBundlesCommand extends AbstractRunnableCommand {
		
		public ListBundlesCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Set<Bundle> bundles = jmop.musicbase().bundles();
			printBundles(bundles);
		}
		
		/**
		 * Prints the bundles.
		 * 
		 * @param bundles
		 */
		private void printBundles(Set<Bundle> bundles) {
			PrintUtil.print("Bundles:");
			for (Bundle bundle : bundles) {
				PrintUtil.printBundleName(bundle);
			}
			PrintUtil.emptyLine();
		}
	}

	/**
	 * The list playlists command.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "playlists", aliases = { "p" }, //
		description = "Lists all the playlists in the bundle or in the whole musicbase", //
		subcommands =  HelpCommand.class )
	public static class ListPlaylistsCommand extends AbstractRunnableCommand {

		@Mixin
		private BundleOrNoneMixin bundle;

		public ListPlaylistsCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			Set<Playlist> playlists = jmop.musicbase().playlists(bundle);
			printPlaylists(bundle, playlists);
		}
		
		/**
		 * Prints the playlists.
		 * 
		 * @param bundle
		 * @param playlists
		 */
		private void printPlaylists(Bundle bundle, Set<Playlist> playlists) {
			if (bundle != null) {
			PrintUtil.print("Playlists in", bundle, ":");
			} else {
				PrintUtil.print("All playlists:");
			}
			for (Playlist playlist: playlists) {
				PrintUtil.printPlaylistName(playlist, bundle == null);
			}
			PrintUtil.emptyLine();
		}
	}

	/**
	 * The list tracks command.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "tracks", aliases = { "t" }, //
		description = "Lists all the tracks in the given bundle or in the whole musicbase", //
		subcommands =  HelpCommand.class )
	public static class ListTracksCommand extends AbstractRunnableCommand {

		@Mixin
		private BundleOrNoneMixin bundle;

		public ListTracksCommand(JMOPPlayer jmop) {
			super(jmop);
		}

		@Override
		protected void doRun()  {
			Bundle bundle = this.bundle.getBundle();
			Set<Track> tracks = jmop.musicbase().tracks(bundle);
			printTracks(bundle, tracks);
		}
	
		/**
		 * Prints the tracks.
		 * @param bundle
		 * @param tracks
		 */
		private void printTracks(Bundle bundle, Set<Track> tracks) {
			if (bundle != null) {
			PrintUtil.print("Tracks in", bundle.getName(), ":");
			} else {
				PrintUtil.print("All the tracks:");
			}
			for (Track track: tracks) {
				PrintUtil.printTrackTitle(track, bundle == null);
			}
			PrintUtil.emptyLine();
		}
	}

}
