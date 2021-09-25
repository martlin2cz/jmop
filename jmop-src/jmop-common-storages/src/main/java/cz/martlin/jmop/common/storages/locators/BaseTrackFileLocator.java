package cz.martlin.jmop.common.storages.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;

/**
 * An general track locator interface. Provides path to the track file. Also
 * provides some convience methods.
 * 
 * @author martin
 *
 */
public interface BaseTrackFileLocator extends TracksLocator {

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
		Bundle bundle = track.getBundle();
		String title = track.getTitle();
		return trackFile(bundle, title);
	}
}
