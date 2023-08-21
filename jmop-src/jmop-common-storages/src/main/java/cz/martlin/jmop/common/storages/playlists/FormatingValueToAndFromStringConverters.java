package cz.martlin.jmop.common.storages.playlists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * An set of converters of the (atomic) metainfo values, which uses custom
 * formatting of the values. Apart from the
 * {@link SimpleValueToAndFromStringConverters}, this class provider some custom
 * formating and some null-values placeholders.
 * 
 * @author martin
 *
 */
public class FormatingValueToAndFromStringConverters extends AbstractNullableValueToAndFromStringConverters {

	private static final String NONE_STR = "none";
	private static final String NEVER_STR = "never";
	private static final DateTimeFormatter DATE_FORMAT = //
			DateTimeFormatter.ofPattern("dd.MM.yyyy H.mm.ss");

	@Override
	public String trackIndexOrNullToText(TrackIndex value) {
		if (value == null) {
			return NONE_STR;
		}
		return Integer.toString(value.getHuman());
	}

	@Override
	public String dateOrNullToText(LocalDateTime value) {
		if (value == null) {
			return NEVER_STR;
		}
		return value.format(DATE_FORMAT);
	}

	@Override
	public String numberOrNullToText(Integer value) {
		if (value == null) {
			return NONE_STR;
		}
		return Integer.toString(value);
	}

	@Override
	public String durationOrNullToText(Duration duration) {
		if (duration == null) {
			return NONE_STR;
		}
		return DurationUtilities.toHumanString(duration);
	}

	@Override
	public TrackIndex textOrNullToTrackIndex(String text) {
		if (text.equals(NONE_STR)) {
			return null;
		}
		return TrackIndex.ofHuman(Integer.parseInt(text));
	}

	@Override
	public LocalDateTime textOrNullToDate(String text) {
		if (text.equals(NEVER_STR)) {
			return null;
		}
		return LocalDateTime.parse(text, DATE_FORMAT);
		//return DATE_FORMAT.parse(text);
	}

	@Override
	public Integer textOrNullToNumber(String text) {
		if (text.equals(NONE_STR)) {
			return 0;
		}
		return Integer.parseInt(text);
	}

	@Override
	public Duration textOrNullToDuration(String text) {
		if (text.equals(NONE_STR)) {
			return null;
		}
		return DurationUtilities.parseHumanDuration(text);
	}

}
