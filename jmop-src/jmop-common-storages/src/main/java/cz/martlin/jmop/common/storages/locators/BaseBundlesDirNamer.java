package cz.martlin.jmop.common.storages.locators;

import cz.martlin.jmop.common.storages.BundlesDirStorageComponent;

/**
 * The namer specifiing the name of the bundle dir name.
 * 
 * @author martin
 *
 */
public interface BaseBundlesDirNamer extends BundlesDirStorageComponent {

	String bundleDirName(String bundleName);

}
