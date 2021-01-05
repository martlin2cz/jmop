package cz.martlin.jmop.common.data.misc;

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
	 */
	void played();
}
