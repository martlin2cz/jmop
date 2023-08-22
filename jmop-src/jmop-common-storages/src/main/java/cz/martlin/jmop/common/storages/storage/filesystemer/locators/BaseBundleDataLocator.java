package cz.martlin.jmop.common.storages.storage.filesystemer.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;

/**
 * The locator of the bundle data file. Also provides some the convience method.
 * 
 * It's part of the various components of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public interface BaseBundleDataLocator {

	/**
	 * Returns file of the bundle data file.
	 * 
	 * @param bundleName
	 * @return
	 */
	public File bundleDataFile(String bundleName);

	/**
	 * Returns file of the bundle data file.
	 * @param bundle
	 * @return
	 */
	public default File bundleDataFile(Bundle bundle) {
		String bundleName = bundle.getName();
		return bundleDataFile(bundleName);
	}
	
	/**
	 * Is the given file data file of some bundle?
	 * @param fileName
	 * @return
	 */
	public boolean isBundleDataFile(File file);

}
