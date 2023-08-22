package cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.conv;

import java.time.LocalDateTime;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.ValueToAndFromStringMetaInfoManager;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableTextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableValueToTextMapper;
import javafx.util.Duration;

/**
 * The converters, which constructs the mappers based on the simple methods like
 * {@link #trackIndexOrNullToText(TrackIndex)}. Null values are possibly
 * arguments to the mappers, they have to take it into an account.
 * 
 * Component of {@link ValueToAndFromStringMetaInfoManager}.
 * 
 * @author martin
 *
 */
public abstract class AbstractNullableValueToAndFromStringConverters implements BaseValueToAndFromStringConverters {

	public AbstractNullableValueToAndFromStringConverters() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public NullableValueToTextMapper<TrackIndex> trackIndexToTextMapper() {
		return this::trackIndexOrNullToText;
	}

	@Override
	public NullableValueToTextMapper<LocalDateTime> dateToTextMapper() {
		return this::dateOrNullToText;
	}

	@Override
	public NullableValueToTextMapper<Integer> numberToTextMapper() {
		return this::numberOrNullToText;
	}

	@Override
	public NullableValueToTextMapper<Duration> durationToTextMapper() {
		return this::durationOrNullToText;
	}

	@Override
	public NullableTextToValueMapper<TrackIndex> textToTrackIndexMapper() {
		return this::textOrNullToTrackIndex;
	}

	@Override
	public NullableTextToValueMapper<LocalDateTime> textToDateMapper() {
		return this::textOrNullToDate;
	}

	@Override
	public NullableTextToValueMapper<Integer> textToNumberMapper() {
		return this::textOrNullToNumber;
	}

	@Override
	public NullableTextToValueMapper<Duration> textToDurationMapper() {
		return this::textOrNullToDuration;
	}

	///////////////////////////////////////////////////////////////////////////

	public abstract Duration textOrNullToDuration(String text);

	public abstract Integer textOrNullToNumber(String text);

	public abstract LocalDateTime textOrNullToDate(String text);

	public abstract TrackIndex textOrNullToTrackIndex(String text);

	public abstract String durationOrNullToText(Duration duration);

	public abstract String numberOrNullToText(Integer value);

	public abstract String dateOrNullToText(LocalDateTime value);

	public abstract String trackIndexOrNullToText(TrackIndex value);

}