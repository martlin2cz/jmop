package cz.martlin.jmop.common.storages.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;

/**
 * The locator of the bundle data file. Also provides some the convience method.
 * 
 * @author martin
 *
 */
public interface BaseBundleDataLocator {

	public File bundleDataFile(String bundleName);

	public default File bundleDataFile(Bundle bundle) {
		String bundleName = bundle.getName();
		return bundleDataFile(bundleName);
	}

	public boolean isBundleDataFile(File file);

}
