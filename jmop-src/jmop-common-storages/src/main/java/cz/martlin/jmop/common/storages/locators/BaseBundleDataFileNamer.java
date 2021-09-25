package cz.martlin.jmop.common.storages.locators;

public interface BaseBundleDataFileNamer {

	String bundleDataFileName(String bundleName);

	boolean isBundleDataFile(String fileName);

}