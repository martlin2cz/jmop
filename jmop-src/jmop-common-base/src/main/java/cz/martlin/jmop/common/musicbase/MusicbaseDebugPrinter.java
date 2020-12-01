package cz.martlin.jmop.common.musicbase;

import java.util.List;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.Tracklist;

public class MusicbaseDebugPrinter {

	public static void print(BaseMusicbase musicbase) {
		List<String> bundleNames = musicbase.bundlesNames();

		for (String bundleName : bundleNames) {
			Bundle bundle = musicbase.getBundle(bundleName);
			print(musicbase, bundle);
		}

	}

	private static void print(BaseMusicbase musicbase, Bundle bundle) {
		String bundleName = bundle.getName();
		System.out.println("Bundle '" + bundleName + "':");
		print(bundle.getMetadata());

		List<String> playlistNames = musicbase.playlistsNames(bundle);
		for (String playlistName : playlistNames) {
			Playlist playlist = musicbase.getPlaylist(bundle, playlistName);
			print(musicbase, playlist);
		}
	}

	private static void print(BaseMusicbase musicbase, Playlist playlist) {
		String playlistName = playlist.getName();
		System.out.println("	Playlist '" + playlistName + "':");
		print(playlist.getMetadata());

		Tracklist tracks = playlist.getTracks();
		for (Track track : tracks.getTracks()) {
			print(track);
		}
	}

	private static void print(Track track) {
		System.out.println("		Track(" + track.getIdentifier() + "): '" + track.getTitle());
		print(track.getMetadata());
	}

	private static void print(Metadata metadata) {
		System.out.println("			(" + metadata.getCreated() + ")"); // TODO
	}
}
