package cz.martlin.jmop.player.cli.misc;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

public class PrintUtil {

	public static final String PATH_SEPARATOR = "/";

	public static void print(String string) {
		System.out.println(string);
	}

	public static void emptyLine() {
		System.out.println();
	}

	public static void printBundleName(Bundle bundle) {
		String name = bundle.getName();
		System.out.println(name);
	}

	public static void printPlaylistName(Playlist playlist, boolean bundledPath) {
		if (bundledPath) {
			String name = playlist.getName();
			String bundleName = playlist.getBundle().getName();
			System.out.println(bundleName + PATH_SEPARATOR + name);
		} else {
			String name = playlist.getName();
			System.out.println(name);
		}
	}

	public static void printTrackTitle(Track track, boolean bundledPath) {
		if (bundledPath) {
			String title = track.getTitle();
			String bundleName = track.getBundle().getName();
			System.out.println(bundleName + PATH_SEPARATOR + title);
		} else {
			String title = track.getTitle();
			System.out.println(title);
		}
	}

}
