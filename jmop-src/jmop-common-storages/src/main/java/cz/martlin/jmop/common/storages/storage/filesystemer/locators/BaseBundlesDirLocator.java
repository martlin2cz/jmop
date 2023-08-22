package cz.martlin.jmop.common.storages.storage.filesystemer.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.components.BaseLocator;
import cz.martlin.jmop.common.storages.components.BundlesDirStorageComponent;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;

/**
 * The bundles dir locator. Firstly, it joins all the bundle data, playlist and
 * tracks locators. Secondly it adds some extra bundle-dir-specific methods.
 * 
 * It's part of the various components of the {@link FileSystemedStorage}.
 * 
 * @author martin
 */
public interface BaseBundlesDirLocator extends BaseLocator, BundlesDirStorageComponent {

	/**
	 * Returns bundle dir file of the given bundle.
	 * 
	 * @param bundleName
	 * @return
	 */
	public File bundleDir(String bundleName);

	/**
	 * Returns bundle dir file of the given bundle.
	 * 
	 * @param bundle
	 * @return
	 */
	public default File bundleDir(Bundle bundle) {
		String bundleName = bundle.getName();
		return bundleDir(bundleName);
	}

}
