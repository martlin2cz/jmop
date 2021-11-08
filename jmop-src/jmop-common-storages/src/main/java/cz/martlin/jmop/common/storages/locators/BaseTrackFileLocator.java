package cz.martlin.jmop.common.storages.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;

/**
 * An general track locator interface. Provides path to the track file. Also
 * provides some convience methods.
 * 
 * @author martin
 *
 */
public interface BaseTrackFileLocator {

	public File trackFile(String bundleName, String trackTitle);

	public default File trackFile(Bundle bundle, String trackTitle) {
		String bundleName = bundle.getName();
		return trackFile(bundleName, trackTitle);
	}
	
	public default File trackFile(Bundle bundle, Track track) {
		String trackTitle = track.getTitle();
		return trackFile(bundle, trackTitle);
	}

	public default File trackFile(Track track) {
		String bundleName = track.getBundle().getName();
		String trackTitle = track.getTitle();
		return trackFile(bundleName, trackTitle);
	}
}
