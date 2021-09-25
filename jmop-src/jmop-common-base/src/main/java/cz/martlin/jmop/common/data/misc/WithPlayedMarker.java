package cz.martlin.jmop.common.data.misc;

import javafx.util.Duration;

/**
 * Indicates that this class can be played and, furthermore, such information
 * may get recorded.
 * 
 * @author martin
 *
 */
public interface WithPlayedMarker {

	/**
	 * Indicates that this class have been played and the object may get updated
	 * correspondingly.
	 * 
	 * @param time the time how long it was played. 
	 * 			If not, use the {@link Duration#ZERO}, no nulls please.
	 */
	void played(Duration time);
}
