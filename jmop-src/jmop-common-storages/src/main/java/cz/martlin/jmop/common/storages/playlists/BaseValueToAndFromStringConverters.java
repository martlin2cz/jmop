package cz.martlin.jmop.common.storages.playlists;

import java.time.LocalDateTime;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import javafx.util.Duration;

/**
 * An set of methods performing conversion to and from of the various
 * in-jmop-used types, like the TrackIndex or Duration.
 * 
 * @author martin
 *
 */
public interface BaseValueToAndFromStringConverters {

	String trackIndexToText(TrackIndex value);

	String dateToText(LocalDateTime value);

	String numberToText(int value);

	String durationToText(Duration value);

	TrackIndex textToTrackIndex(String text);

	LocalDateTime textToDate(String text);

	int textToNumber(String text);

	Duration textToDuration(String text);

}