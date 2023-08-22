package cz.martlin.jmop.common.storages.storage.filesystemer.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;

/**
 * An general track locator interface. Provides path to the track file. Also
 * provides some convience methods.
 * 
 * It's part of the various components of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public interface BaseTrackFileLocator {

	/**
	 * Returns the track data file of the given track.
	 * 
	 * @param bundleName
	 * @param trackTitle
	 * @return
	 */
	public File trackFile(String bundleName, String trackTitle);

	/**
	 * Returns the track data file of the given track.
	 * 
	 * @param bundle
	 * @param trackTitle
	 * @return
	 */
	public default File trackFile(Bundle bundle, String trackTitle) {
		String bundleName = bundle.getName();
		return trackFile(bundleName, trackTitle);
	}

	/**
	 * Returns the track data file of the given track.
	 * 
	 * @param bundle
	 * @param track
	 * @return
	 */
	public default File trackFile(Bundle bundle, Track track) {
		String trackTitle = track.getTitle();
		return trackFile(bundle, trackTitle);
	}

	/**
	 * Returns the track data file of the given track.
	 * 
	 * @param track
	 * @return
	 */
	public default File trackFile(Track track) {
		String bundleName = track.getBundle().getName();
		String trackTitle = track.getTitle();
		return trackFile(bundleName, trackTitle);
	}
}
