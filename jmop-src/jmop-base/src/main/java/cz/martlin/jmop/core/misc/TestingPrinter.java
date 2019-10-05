package cz.martlin.jmop.core.misc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Metadata;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;

public class TestingPrinter {

	public static String print(Track track) {

		return "" //
				+ " ID:    " + track.getIdentifier() + "\n"//
				+ " TITLE: " + track.getTitle() + "\n"//
				+ " TIME:  " + DurationUtilities.toHumanString(track.getDuration()) + "\n"//
				+ " DESC:  " + shorten(track.getDescription()) + "\n"//
				+ " META:  " + printMeta(track.getMetadata()) + "\n"//
				+ "\n"; //
	}

	public static String print(List<Track> tracks) {
		return tracks.stream().map((t) -> print(t)).collect(Collectors.joining("\n"));
	}

	public static String print(Playlist playlist) {
		return "" //
				+ "NAME:   " + playlist.getName() + "\n"//
				+ "BUNDLE: " + playlist.getBundle().getName() + "\n"//
				+ "CURR:   " + playlist.getCurrentTrackIndex() + "\n"//
				+ "LOCK:   " + playlist.isLocked() + "\n"//
				+ "META:   " + printMeta(playlist.getMetadata()) + "\n"//
				+ "TRACKS:" + "\n"//
				+ print(playlist.getTracks().getTracks()); //
	}

	public static String print(Bundle bundle) {
		return "" //
				+ "NAME:  " + bundle.getName() + "\n"//
				+ "KIND:  " + bundle.getKind() + "\n"//
				+ "META:  " + printMeta(bundle.getMetadata()) + "\n"//
				+ "TRACKS:" + "\n"//
				+ print(bundle.tracks().getTracks()); //
	}

	private static String printMeta(Metadata metadata) {
		return "crea:" + date2str(metadata.getCreated()) //
				+ ", plays:" + Integer.toString(metadata.getNumberOfPlays()) //
				+ ", last:" + date2str(metadata.getLastPlayed()); //
	}

	////////////////////////////////////////////////////////////////////////////

	private static String shorten(String text) {
		if (text.length() > 80) {
			return text.substring(0, 80) + "(...)";
		} else {
			return text;
		}
	}

	private static String date2str(Calendar cal) {
		SimpleDateFormat format = new SimpleDateFormat("dd.mm.yy HH:MM");

		Date date = cal.getTime();
		return format.format(date);
	}
}
