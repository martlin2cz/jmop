package cz.martlin.jmop.core.misc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;

/**
 * The testing printer of the musicdata.
 * 
 * @see MusicbaseDebugPrinter
 * @author martin
 *
 */
public class TestingPrinter {
	
	public static String printTDs(List<TrackData> tracks) {
		return tracks.stream() //
				.map(TestingPrinter::print)
				.collect(Collectors.joining());
	}

	public static String print(TrackData track) {
		return "" //
				
				+ " TITLE: " + track.getTitle() + "\n"//
				+ " TIME:  " + DurationUtilities.toHumanString(track.getDuration()) + "\n"//
				+ " DESC:  " + shorten(track.getDescription()) + "\n"//
				+ " SRC:   " + track.getURI() != null ? track.getURI().toASCIIString() : "NULL" + "\n"//
				+ "\n"; //
	}
	
	public static String print(Track track) {

		return "" //
				
				+ " TITLE: " + track.getTitle() + "\n"//
				+ " TIME:  " + DurationUtilities.toHumanString(track.getDuration()) + "\n"//
				+ " DESC:  " + shorten(track.getDescription()) + "\n"//
				+ " FILE:  " + (track.getFile() != null ? track.getFile().getAbsolutePath() : "NULL") + "\n"//
				+ " SRC:   " + (track.getSource() != null ? track.getSource().toASCIIString() : "NULL") + "\n"//
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
				+ "META:   " + printMeta(playlist.getMetadata()) + "\n"//
				+ "TRACKS:" + "\n"//
				+ print(playlist.getTracks().getTracks()); //
	}

	public static String print(Bundle bundle) {
		return "" //
				+ "NAME:  " + bundle.getName() + "\n"//
				+ "META:  " + printMeta(bundle.getMetadata()) + "\n"//
				+ "TRACKS:" + "\n"; //
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

	private static String date2str(LocalDateTime dateTime) {
		if (dateTime == null) {
			return "---";
		}
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy H.mm.ss");
		
		return dateTime.format(format);
	}
}
