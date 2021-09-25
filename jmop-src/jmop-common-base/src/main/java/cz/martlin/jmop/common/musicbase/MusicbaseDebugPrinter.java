package cz.martlin.jmop.common.musicbase;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;

public class MusicbaseDebugPrinter {

	public static void print(BaseMusicbaseLoading musicbase)  {
		for (Bundle bundle: musicbase.bundles()) {
			print(musicbase, bundle);
		}

	}

	private static void print(BaseMusicbaseLoading musicbase, Bundle bundle)  {
		String bundleName = bundle.getName();
		System.out.println("Bundle '" + bundleName + "':");
		print(bundle.getMetadata());

		for (Playlist playlist: musicbase.playlists(bundle)) {
			print(playlist);
		}
	}

	public static void print(Playlist playlist) {
		String playlistName = playlist.getName();
		System.out.println("	Playlist '" + playlistName + "':");
		print(playlist.getMetadata());

		Tracklist tracks = playlist.getTracks();
		for (Track track : tracks.getTracks()) {
			print(track);
		}
	}

	public static void print(Track track) {
		System.out.println("		Track(" + track.getIdentifier() + "): '" + track.getTitle() + "'");
		print(track.getMetadata());
	}

	private static void print(Metadata metadata) {
		System.out.println("			(" + metadata.getCreated().getTime() + ")"); // TODO
	}
}
