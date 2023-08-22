package cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.conv;

import java.time.LocalDateTime;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.ValueToAndFromStringMetaInfoManager;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableTextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableValueToTextMapper;
import javafx.util.Duration;

/**
 * An set of mappers performing conversion to and from of the various
 * in-jmop-used types (like the TrackIndex or Duration) to text.
 * 
 * Use {@link AbstractNonNullValueToAndFromStringConverters} or
 * {@link AbstractNullableValueToAndFromStringConverters}
 * 
 * Component of {@link ValueToAndFromStringMetaInfoManager}.
 * 
 * @author martin
 *
 */
public interface BaseValueToAndFromStringConverters {

	/////////////////////////////////////////////////////////////////
	
	NullableValueToTextMapper<TrackIndex> trackIndexToTextMapper();

	NullableValueToTextMapper<LocalDateTime> dateToTextMapper();

	NullableValueToTextMapper<Integer> numberToTextMapper();

	NullableValueToTextMapper<Duration> durationToTextMapper();

	/////////////////////////////////////////////////////////////////
	
	NullableTextToValueMapper<TrackIndex> textToTrackIndexMapper();

	NullableTextToValueMapper<LocalDateTime> textToDateMapper();

	NullableTextToValueMapper<Integer> textToNumberMapper();

	NullableTextToValueMapper<Duration> textToDurationMapper();
	
	/////////////////////////////////////////////////////////////////
}