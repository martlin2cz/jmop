package cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers;

import cz.martlin.jmop.common.storages.storage.filesystemer.locators.AbstractCommonLocator;

/**
 * Track file name computer. Computes track file name.
 *
 * It's component of the {@link AbstractCommonLocator}.
 *
 * @author martin
 *
 */
public interface BaseTrackFileNamer {

	/**
	 * Computes the track file name of the given track.
	 * 
	 * @param bundleName
	 * @param trackTitle
	 * @return
	 */
	String trackFileName(String bundleName, String trackTitle);

}