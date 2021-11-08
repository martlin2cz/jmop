package cz.martlin.jmop.common.musicbase;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;

/**
 * @deprecated Use the file directly from the track.
 * @author martin
 *
 */
@Deprecated
public interface TracksLocator {

	/**
	 * @deprecated Replaced by the {@link Track#getFile()}.
	 * @author martin
	 *
	 */
	public default File trackFile(Track track) {
		return track.getFile();
	}
}
