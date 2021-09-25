package cz.martlin.jmop.common.storages.playlists;

import java.time.LocalDateTime;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * The simpliest set of converters of the (atomic) metainfo values. Provides no
 * custom formatting or null-value indicators ("none", "-" or so).
 * 
 * @author martin
 *
 */
public class SimpleValueToAndFromStringConverters implements BaseValueToAndFromStringConverters {

	@Override
	public String trackIndexToText(TrackIndex value) {
		return Integer.toString(value.getHuman());
	}

	@Override
	public String dateToText(LocalDateTime value) {
		return value.toString();
	}

	@Override
	public String numberToText(int value) {
		return Integer.toString(value);
	}

	@Override
	public String durationToText(Duration duration) {
		return DurationUtilities.toHumanString(duration);
	}

	@Override
	public TrackIndex textToTrackIndex(String text) {
		return TrackIndex.ofHuman(Integer.parseInt(text));
	}

	@Override
	public LocalDateTime textToDate(String text) {
		return LocalDateTime.parse(text);
	}

	@Override
	public int textToNumber(String text) {
		return Integer.parseInt(text);
	}

	@Override
	public Duration textToDuration(String text) {
		return DurationUtilities.parseHumanDuration(text);
	}

}
