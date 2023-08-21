package cz.martlin.jmop.common.storages.playlists;

import java.time.LocalDateTime;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableTextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableValueToTextMapper;
import javafx.util.Duration;

/**
 * The converters which constructs the mappers based on the simple methods like
 * {@link #trackIndexToText(TrackIndex)}. The values populated to theese methods
 * would be never null.
 * 
 * @author martin
 *
 */
public abstract class AbstractNonNullValueToAndFromStringConverters implements BaseValueToAndFromStringConverters {

	public AbstractNonNullValueToAndFromStringConverters() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public NullableValueToTextMapper<TrackIndex> trackIndexToTextMapper() {
		return NullableValueToTextMapper.checked(this::trackIndexToText);
	}

	@Override
	public NullableValueToTextMapper<LocalDateTime> dateToTextMapper() {
		return NullableValueToTextMapper.checked(this::dateToText);
	}

	@Override
	public NullableValueToTextMapper<Integer> numberToTextMapper() {
		return NullableValueToTextMapper.checked(this::numberToText);
	}

	@Override
	public NullableValueToTextMapper<Duration> durationToTextMapper() {
		return NullableValueToTextMapper.checked(this::durationToText);
	}

	@Override
	public NullableTextToValueMapper<TrackIndex> textToTrackIndexMapper() {
		return NullableTextToValueMapper.checked(this::textToTrackIndex);
	}

	@Override
	public NullableTextToValueMapper<LocalDateTime> textToDateMapper() {
		return NullableTextToValueMapper.checked(this::textToDate);
	}

	@Override
	public NullableTextToValueMapper<Integer> textToNumberMapper() {
		return NullableTextToValueMapper.checked(this::textToNumber);
	}

	@Override
	public NullableTextToValueMapper<Duration> textToDurationMapper() {
		return NullableTextToValueMapper.checked(this::textToDuration);
	}

	///////////////////////////////////////////////////////////////////////////

	public abstract Duration textToDuration(String text);

	public abstract int textToNumber(String text);

	public abstract LocalDateTime textToDate(String text);

	public abstract TrackIndex textToTrackIndex(String text);

	public abstract String durationToText(Duration duration);

	public abstract String numberToText(int value);

	public abstract String dateToText(LocalDateTime value);

	public abstract String trackIndexToText(TrackIndex value);

}