package cz.martlin.jmop.player.cli.repl.helpers;

import java.util.Queue;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.stats.MusicbaseStats;
import cz.martlin.jmop.common.musicbase.stats.MusicbaseStats.ListStatsSpecifier;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

/**
 * Just helper which prints the stats.
 * 
 * @author martin
 *
 */
public class StatsPrinter {
	private static final int NORMAL_COUNT = 10;

	private final JMOPPlayer jmop;

	public StatsPrinter(JMOPPlayer jmop) {
		super();
		this.jmop = jmop;
	}

	/**
	 * Prints the stats.
	 * @param bundleOrNot for this bundle or globally?
	 * @param minimal just short summary or with lists?
	 * @param full only TOP x items or all?
	 */
	public void print(Bundle bundleOrNot, boolean minimal, boolean full) {
		MusicbaseStats stats = jmop.musicbase().getStats();

		if (minimal) {
			printMinimalStats(bundleOrNot, stats);
		} else {
			printStats(bundleOrNot, stats, full);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////

	private void printMinimalStats(Bundle bundleOrNot, MusicbaseStats stats) {
		PrintUtil.print("Total tracks time:", stats.totalDuration(bundleOrNot));

		PrintUtil.print("Total played time (approx.):", stats.totalPlayedTime(bundleOrNot));
		
		PrintUtil.print("Total played tracks:", stats.totalPlayedTracks(bundleOrNot));
		PrintUtil.emptyLine();

		Bundle mostPlayedBundle = stats.bundles(ListStatsSpecifier.MOST_PLAYED, 1).poll();
		PrintUtil.print("Most played bundle:", mostPlayedBundle);

		Bundle lastPlayedBundle = stats.bundles(ListStatsSpecifier.LAST_PLAYED, 1).poll();
		PrintUtil.print("Last played bundle:", lastPlayedBundle);

		Playlist mostPlayedPlaylist = stats.playlists(bundleOrNot, ListStatsSpecifier.MOST_PLAYED, 1).poll();
		PrintUtil.print("Most played playlist:", mostPlayedPlaylist, "(", mostPlayedPlaylist.getBundle(), ")");

		Playlist lastPlayedPlaylist = stats.playlists(bundleOrNot, ListStatsSpecifier.LAST_PLAYED, 1).poll();
		PrintUtil.print("Last played playlist:", lastPlayedPlaylist, "(", lastPlayedPlaylist.getBundle(), ")");

		Track mostPlayedTrack = stats.tracks(bundleOrNot, ListStatsSpecifier.MOST_PLAYED, 1).poll();
		PrintUtil.print("Most played track:", mostPlayedTrack, "(", mostPlayedTrack.getBundle(), ")");

		Track lastPlayedTrack = stats.tracks(bundleOrNot, ListStatsSpecifier.LAST_PLAYED, 1).poll();
		PrintUtil.print("Last played track:", lastPlayedTrack, "(", lastPlayedTrack.getBundle(), ")");
	}

	private void printStats(Bundle bundleOrNot, MusicbaseStats stats, boolean full) {
		Integer count = countToPrint(full);
		
		PrintUtil.print("Bundles count:", jmop.musicbase().bundles().size());
		PrintUtil.print("Playlists count:", jmop.musicbase().playlists(bundleOrNot).size());
		PrintUtil.print("Tracks count:", jmop.musicbase().tracks(bundleOrNot).size());
		
		PrintUtil.print("Total tracks time:", stats.totalDuration(bundleOrNot));

		PrintUtil.print("Total played tracks:", stats.totalPlayedTracks(bundleOrNot));
		PrintUtil.print("Total played time:", stats.totalPlayedTime(bundleOrNot));
		PrintUtil.emptyLine();
		
		Queue<Bundle> mostPlayedBundles = stats.bundles(ListStatsSpecifier.MOST_PLAYED, count);
		printHeadline(full, "Most played bundles:");
		for (Bundle bundle: mostPlayedBundles) {
			PrintUtil.print(bundle, ":", bundle.getMetadata().getNumberOfPlays(), "x");
		}
		PrintUtil.emptyLine();
		
		Queue<Bundle> lastPlayedBundles = stats.bundles(ListStatsSpecifier.LAST_PLAYED, count);
		printHeadline(full, "Last played bundles:");
		for (Bundle bundle: lastPlayedBundles) {
			PrintUtil.print(bundle, ":", bundle.getMetadata().getLastPlayed());
		}
		PrintUtil.emptyLine();
		
		Queue<Bundle> biggestBundles = stats.biggestBundlesByTracks(count);
		printHeadline(full, "Biggest bundles:");
		for (Bundle bundle: biggestBundles) {
			Set<Track> tracks = jmop.musicbase().tracks(bundle);
			PrintUtil.print(bundle, ":", tracks.size(), "tracks");
		}
		PrintUtil.emptyLine();
		
		Queue<Playlist> mostPlayedPlaylists = stats.playlists(bundleOrNot, ListStatsSpecifier.MOST_PLAYED, count);
		printHeadline(full, "Most played playlists:");
		for (Playlist playlist : mostPlayedPlaylists) {
			PrintUtil.print(playlist, "(", playlist.getBundle(), "):",  playlist.getMetadata().getNumberOfPlays(), "x");
		}
		PrintUtil.emptyLine();

		Queue<Playlist> lastPlayedPlaylists = stats.playlists(bundleOrNot, ListStatsSpecifier.LAST_PLAYED, count);
		printHeadline(full, "Last played playlists:");
		for (Playlist playlist : lastPlayedPlaylists) {
			PrintUtil.print(playlist, "(", playlist.getBundle(), "):",  playlist.getMetadata().getLastPlayed());
		}
		PrintUtil.emptyLine();
		
		Queue<Playlist> playlistsWithMostTracks = stats.longestPlaylistByNumberOfTracks(bundleOrNot, count);
		printHeadline(full, "Longest playlists (by tracks):");
		for (Playlist playlist : playlistsWithMostTracks) {
			PrintUtil.print(playlist, "(", playlist.getBundle(), "):",  playlist.getTracks().getTracks().size(), "tracks" );
		}
		PrintUtil.emptyLine();
		
		Queue<Playlist> longestPlaylists = stats.longestPlaylistByTotalDuration(bundleOrNot, count);
		printHeadline(full, "Longest playlists (by duration):");
		for (Playlist playlist : longestPlaylists) {
			PrintUtil.print(playlist, "(", playlist.getBundle(), "):", playlist.getTotalDuration());
		}
		PrintUtil.emptyLine();
		
		//TODO longest tracks
		
		Queue<Track> mostPlayedTracks = stats.tracks(bundleOrNot, ListStatsSpecifier.MOST_PLAYED, count);
		printHeadline(full, "Most played tracks:");
		for (Track track : mostPlayedTracks) {
			PrintUtil.print(track, "(", track.getBundle(), "):",  track.getMetadata().getNumberOfPlays(), "x");
		}
		PrintUtil.emptyLine();

		Queue<Track> lastPlayedTracks = stats.tracks(bundleOrNot, ListStatsSpecifier.LAST_PLAYED, count);
		printHeadline(full, "Last played tracks:");
		for (Track track : lastPlayedTracks) {
			PrintUtil.print(track, "(", track.getBundle(), "):",  track.getMetadata().getLastPlayed());
		}
		PrintUtil.emptyLine();
		
	}

	
	////////////////////////////////////////////////////////////////////////////
	
	private void printHeadline(boolean full, String message) {
		Integer count = countToPrint(full);
		
		if (count != null) {
			PrintUtil.print("Top", count, message);
		} else {
			PrintUtil.print(message);
		}
	}

	private Integer countToPrint(boolean full) {
		if (full) {
			return null;
		} else {
			return NORMAL_COUNT;
		}
	}

}
