package cz.martlin.jmop.core.misc;

import java.util.concurrent.TimeUnit;

import javafx.util.Duration;

public class DurationUtilities {

	public static Duration createDuration(int hours, int minutes, int seconds) {
		Duration hoursDuration = Duration.hours(hours);
		Duration minutesDuration = Duration.minutes(minutes);
		Duration secondsDuration = Duration.seconds(seconds);

		// TODO well, this is probably not the most efficient way ...
		return hoursDuration.add(minutesDuration).add(secondsDuration);
	}

	public static Duration parseYoutubeDuration(String string) {
		String[] parts = string.split("(PT|H|M|S)");
		int hours, minutes, seconds;

		if (parts.length == 2) {
			hours = 0;
			minutes = 0;
			seconds = Integer.parseInt(parts[1]);
		} else if (parts.length == 3) {
			hours = 0;
			minutes = Integer.parseInt(parts[1]);
			seconds = Integer.parseInt(parts[2]);
		} else if (parts.length == 4) {
			hours = Integer.parseInt(parts[1]);
			minutes = Integer.parseInt(parts[2]);
			seconds = Integer.parseInt(parts[3]);
		} else {
			throw new UnsupportedOperationException("Unknown format od duration: " + string);
		}

		return createDuration(hours, minutes, seconds);
	}

	public static String toMilis(Duration duration) {
		return Double.toString(duration.toMillis());
	}

	public static Duration parseMilisDuration(String string) {
		double millis = Double.parseDouble(string);
		return new Duration(millis);
	}

	public static String toHumanString(Duration duration) {
		long remaining = (long) duration.toMillis();
		int hours = (int) TimeUnit.MILLISECONDS.toHours(remaining);
		
		remaining -= TimeUnit.HOURS.toMillis(hours);
		int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(remaining);
		
		remaining -= TimeUnit.MINUTES.toMillis(minutes);
		int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(remaining);
		
		// TODO optionally add leading zeros
		return hours + ":" + minutes + ":" + seconds;
	}

}
