package cz.martlin.jmop.core.misc;

import java.util.concurrent.TimeUnit;

import javafx.util.Duration;

/**
 * Utilities for working with the {@link Duration} class (like converting to
 * hooman-string).
 * 
 * @author martin
 *
 */
public class DurationUtilities {

	/**
	 * Creates instace of duration with given hours, minutes and seconds.
	 * 
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static Duration createDuration(int hours, int minutes, int seconds) {
		Duration hoursDuration = Duration.hours(hours);
		Duration minutesDuration = Duration.minutes(minutes);
		Duration secondsDuration = Duration.seconds(seconds);

		// TODO well, this is probably not the most efficient way ...
		return hoursDuration.add(minutesDuration).add(secondsDuration);
	}

	/**
	 * Parses the duration in the youtube API format (like "PT4H2M6S").
	 * 
	 * @param string
	 * @return
	 */
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

	/**
	 * Converts given duration to string representation of miliseconds count.
	 * 
	 * @param duration
	 * @return
	 */
	public static String toMilis(Duration duration) {
		return Double.toString(duration.toMillis());
	}

	/**
	 * Parses given string containing miliseconds count into Duration instance.
	 * 
	 * @param string
	 * @return
	 */
	public static Duration parseMilisDuration(String string) {
		double millis = Double.parseDouble(string);
		return new Duration(millis);
	}

	/**
	 * Converts to human string, like 3:15 or 1:59:59.
	 * 
	 * @param duration
	 * @return
	 */
	public static String toHumanString(Duration duration) {
		long remaining = (long) duration.toMillis();
		int hours = (int) TimeUnit.MILLISECONDS.toHours(remaining);

		remaining -= TimeUnit.HOURS.toMillis(hours);
		int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(remaining);

		remaining -= TimeUnit.MINUTES.toMillis(minutes);
		int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(remaining);

		if (hours == 0) {
			return String.format("%d:%02d", minutes, seconds);
		} else {
			return String.format("%d:%02d:%02d", hours, minutes, seconds);
		}
	}

}
