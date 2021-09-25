package cz.martlin.jmop.common.storages.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.BundlesDirStorageComponent;

/**
 * The bundles dir locator. Firstly, it joins all the bundle data, playlist and
 * tracks locators. Secondly it adds some extra bundle-dir-specific methods.
 * 
 * @author martin
 */
public interface BaseBundlesDirLocator extends BaseLocator, BundlesDirStorageComponent {

	public File bundleDir(String bundleName);

	public default File bundleDir(Bundle bundle) {
		String bundleName = bundle.getName();
		return bundleDir(bundleName);
	}

//	public boolean isBundleDir(File dir);
//
//	public boolean isPlaylistFile(File file);
//
//	public boolean isTrackFile(File file);
//


}
