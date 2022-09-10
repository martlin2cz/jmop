package cz.martlin.jmop.player.cli.repl.helpers;

import java.util.Map;
import java.util.Set;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

/**
 * Just a printer of the bundle, playlist and track.
 * 
 * @author martin
 *
 */
public class ElementsPrinter {

	private final JMOPPlayer jmop;

	public ElementsPrinter(JMOPPlayer jmop) {
		this.jmop = jmop;
	}

	/**
	 * Prints info about the given bundle.
	 * 
	 * @param bundle
	 */
	public void print(Bundle bundle) {
		PrintUtil.print("Bundle", bundle);
		printMetadata(bundle.getMetadata());

		Set<Playlist> playlists = jmop.musicbase().playlists(bundle);
		Set<Track> tracks = jmop.musicbase().tracks(bundle);

		PrintUtil.print(playlists.size(), "playlists,", //
				tracks.size(), "tracks"); //

		PrintUtil.emptyLine();
	}

	/**
	 * Prints info about the given playlist.
	 * @param bundle
	 */
	public void print(Playlist playlist) {
		PrintUtil.print("Playlist", playlist);
		PrintUtil.print("in bundle", playlist.getBundle());
		printMetadata(playlist.getMetadata());

		PrintUtil.print("Tracks", "(", playlist.getTracks().count(), ")");
		Map<TrackIndex, Track> tracks = playlist.getTracks().asIndexedMap();
		for (TrackIndex index : tracks.keySet()) {
			Track track = tracks.get(index);

			if (index.notEqual(playlist.getCurrentTrackIndex())) {
				PrintUtil.print(index, "", track, "(", track.getDuration(), ")");
			} else {
				PrintUtil.print(">", "", track, "(", track.getDuration(), ")");
			}
		}

		PrintUtil.emptyLine();
	}

	/**
	 * Prints info about the given track.
	 * 
	 * @param bundle
	 */
	public void print(Track track) {
		PrintUtil.print("Track", track);
		PrintUtil.print("in bundle", track.getBundle());
		printMetadata(track.getMetadata());
		PrintUtil.print("Duration:", track.getDuration());
		PrintUtil.print("Source:", track.getSource());
		PrintUtil.print("File:", track.getFile());
		PrintUtil.print("Description:");
		PrintUtil.print(track.getDescription());
		PrintUtil.emptyLine();
	}

	/**
	 * Prints the given metadata.
	 * 
	 * @param metadata
	 */
	private static void printMetadata(Metadata metadata) {
		PrintUtil.print("Created:", metadata.getCreated(), //
				", played:", metadata.getNumberOfPlays(), "x", //
				", last played:", metadata.getLastPlayed(), //
				", total time played:", metadata.getTotalTime());
	}

}
