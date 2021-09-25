package cz.martlin.jmop.common.storages.locators.impls;

import cz.martlin.jmop.common.storages.locators.BaseBundleDataFileNamer;
import cz.martlin.jmop.common.storages.simples.SimpleStorageComponent;

/**
 * The bundle data file namer saving the bundle data into the simple text file
 * named "bundle.txt".
 * 
 * @author martin
 *
 */
public class SimpleBundleDataFileNamer implements BaseBundleDataFileNamer, SimpleStorageComponent {

	public SimpleBundleDataFileNamer() {
		super();
	}

	@Override
	public String bundleDataFileName(String bundleName) {
		return "bundle.txt";
	}
	
	@Override
	public boolean isBundleDataFile(String fileName) {
		return "bundle.txt".equals(fileName);
	}

}
