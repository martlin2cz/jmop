package cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers;

import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundleDataLocator;

/**
 * The bundle data file namer. Computes name of the file with the bundle data.
 * 
 * It's component of {@link BaseBundleDataLocator}.
 * 
 * @author martin
 *
 */
public interface BaseBundleDataFileNamer {

	/**
	 * Returns name of the file of the given bundle.
	 * @param bundleName
	 * @return
	 */
	String bundleDataFileName(String bundleName);

	/**
	 * Is the given file data file of some bundle?
	 * @param fileName
	 * @return
	 */
	boolean isBundleDataFile(String fileName);

}