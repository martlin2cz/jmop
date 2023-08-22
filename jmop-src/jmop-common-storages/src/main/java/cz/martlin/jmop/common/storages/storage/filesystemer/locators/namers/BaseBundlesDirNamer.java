package cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers;

import cz.martlin.jmop.common.storages.components.BundlesDirStorageComponent;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BundlesDirDataFilesLocator;

/**
 * The namer specifiing the name of the bundle dir name.
 * 
 * It's component of {@link BundlesDirDataFilesLocator}.
 * 
 * @author martin
 *
 */
public interface BaseBundlesDirNamer extends BundlesDirStorageComponent {

	/**
	 * Returns the name of the bundle dir of the given bundle.
	 * 
	 * @param bundleName
	 * @return
	 */
	String bundleDirName(String bundleName);

}
