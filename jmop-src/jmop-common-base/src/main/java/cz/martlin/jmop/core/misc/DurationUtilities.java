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

	private static final String DURATION_SEPARATOR = ":"; //$NON-NLS-1$
	private static final int TIME_UNIT_MULTIPLICATOR = 60;

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
		String[] parts = string.split("(PT|H|M|S)"); //$NON-NLS-1$
		int hours, minutes, seconds;

		if (string.matches("PT(\\d+)S")) { //$NON-NLS-1$
			hours = 0;
			minutes = 0;
			seconds = Integer.parseInt(parts[1]);
		} else if (string.matches("PT(\\d+)M(\\d+)S")) { //$NON-NLS-1$
			hours = 0;
			minutes = Integer.parseInt(parts[1]);
			seconds = Integer.parseInt(parts[2]);
		} else if (string.matches("PT(\\d+)H(\\d+)S")) { //$NON-NLS-1$
			hours = Integer.parseInt(parts[1]);
			minutes = 0;
			seconds = Integer.parseInt(parts[2]);
		} else if (string.matches("PT(\\d+)H(\\d+)M(\\d+)S")) { //$NON-NLS-1$
			hours = Integer.parseInt(parts[1]);
			minutes = Integer.parseInt(parts[2]);
			seconds = Integer.parseInt(parts[3]);
		} else {
			throw new UnsupportedOperationException("Unknown format od duration: " + string); //$NON-NLS-1$
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
		if (duration != null) {
			return Integer.toString((int) duration.toMillis());
		} else {
			return Integer.toString(0);
		}
	}

	/**
	 * Parses given string containing miliseconds count into Duration instance.
	 * 
	 * @param string
	 * @return
	 */
	public static Duration parseMilisDuration(String string) {
		int millis = Integer.parseInt(string);
		return new Duration(millis);
	}

	/**
	 * Converts to human string, like 3:15 or 1:59:59.
	 * 
	 * @param duration
	 * @return
	 */
	public static String toHumanString(Duration duration) {
		if (duration == null) {
			return "unknown";
		}
		
		long remaining = (long) duration.toMillis();
		int hours = (int) TimeUnit.MILLISECONDS.toHours(remaining);

		remaining -= TimeUnit.HOURS.toMillis(hours);
		int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(remaining);

		remaining -= TimeUnit.MINUTES.toMillis(minutes);
		int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(remaining);

		if (hours == 0) {
			return String.format("%d:%02d", minutes, seconds); //$NON-NLS-1$
		} else {
			return String.format("%d:%02d:%02d", hours, minutes, seconds); //$NON-NLS-1$
		}
	}

	/**
	 * Parses string duration in human format into integer (number of seconds).
	 * 
	 * @param durationStr
	 * @return
	 */
	public static Duration parseHumanDuration(String durationStr) { 
		String[] parts = durationStr.split(DURATION_SEPARATOR);

		int sum = 0;
		for (String part : parts) {
			sum *= TIME_UNIT_MULTIPLICATOR;
			int value = Integer.parseInt(part);
			sum += value;
		}

		int milis = (int) TimeUnit.SECONDS.toMillis(sum);
		return new Duration(milis);
	}

}
