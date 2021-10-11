package cz.martlin.jmop.player.cli.repl.misc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

public class PrintUtil {

	public static final String PATH_SEPARATOR = "/";
	
	private static final DateTimeFormatter DATE_FORMAT = //
			DateTimeFormatter.ofPattern("d.M.yy H:mm:ss");

	public static void emptyLine() {
		System.out.println();
	}

	public static void print(Object... items) {
		for (Object item : items) {
			if (item instanceof Bundle) {
				Bundle bundle = (Bundle) item;
				System.out.print(bundle.getName());
				
			} else if (item instanceof Playlist) {
				Playlist playlist = (Playlist) item;
				System.out.print(playlist.getName());
				
			} else if (item instanceof Track) {
				Track track = (Track) item;
				System.out.print(track.getTitle());
				
			} else if (item instanceof TrackIndex) {
				TrackIndex index = (TrackIndex) item;
				System.out.print(index.getHuman() + ".");
						
			} else if (item instanceof Duration) {
				Duration duration = (Duration) item;
				System.out.print(DurationUtilities.toHumanString(duration));
			
			} else if (item instanceof Integer) {
				Integer num = (Integer) item;
				System.out.print(num);
			
			} else if (item instanceof LocalDateTime) {
				LocalDateTime date = (LocalDateTime) item;
				String formatted = date.format(DATE_FORMAT);
				System.out.print(formatted);
				
			} else if (item instanceof File) {
				File file = (File) item;
				if (file.exists()) {
					System.out.print(file.getPath());
				} else {
					System.out.print("none");
				}
				
			} else if (item instanceof String) {
				String string = (String) item;
				System.out.print(string);
			
			} else if (item == null) {
				System.out.print("---");
				
			} else {
				throw new UnsupportedOperationException(Objects.toString(item));
			}
			
			System.out.print(" ");
		}
		
		System.out.println();
	}

	///////////////////////////////////////////////////////////////////////////

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
///////////////////////////////////////////////////////////////////////////

}
