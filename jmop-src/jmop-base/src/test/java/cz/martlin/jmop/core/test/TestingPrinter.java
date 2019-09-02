package cz.martlin.jmop.core.test;

import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;

public class TestingPrinter {

	public static String print(Track track) {
		return "" //
				+ " ID:    " + track.getIdentifier() + "\n"//
				+ " TITLE: " + track.getTitle() + "\n"//
				+ " TIME:  " + DurationUtilities.toHumanString(track.getDuration()) + "\n"//
				+ " DESC:  " + shorten(track.getDescription()) + "\n"//
				+ "\n"; //
	}

	public static String print(List<Track> tracks) {
		return tracks.stream().map((t) -> print(t)).collect(Collectors.joining("\n"));
	}

	////////////////////////////////////////////////////////////////////////////

	private static String shorten(String text) {
		if (text.length() > 80) {
			return text.substring(0, 80) + "(...)";
		} else {
			return text;
		}
	}
}
