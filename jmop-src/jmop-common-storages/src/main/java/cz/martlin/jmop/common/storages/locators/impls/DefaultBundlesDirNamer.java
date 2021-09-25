package cz.martlin.jmop.common.storages.locators.impls;

import cz.martlin.jmop.common.storages.locators.BaseBundlesDirNamer;

/**
 * The default bundles dir namer. The bundle dir name is equal to the actual
 * bundle name.
 * 
 * @author martin
 *
 */
public class DefaultBundlesDirNamer implements BaseBundlesDirNamer {

	@Override
	public String bundleDirName(String bundleName) {
		return bundleName;
	}

}
