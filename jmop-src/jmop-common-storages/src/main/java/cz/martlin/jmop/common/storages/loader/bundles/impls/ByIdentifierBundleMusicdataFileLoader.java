package cz.martlin.jmop.common.storages.loader.bundles.impls;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An {@link BaseBundlesByIdentifierLoader} loading by the
 * {@link BaseMusicdataFileManipulator}.
 * 
 * @author martin
 *
 */
public class ByIdentifierBundleMusicdataFileLoader implements BaseBundlesByIdentifierLoader<String> {

	private final BaseBundleDataLocator locator;
	private final BaseMusicdataFileManipulator manipulator;

	public ByIdentifierBundleMusicdataFileLoader(BaseBundleDataLocator locator,
			BaseMusicdataFileManipulator manipulator) {
		super();
		this.locator = locator;
		this.manipulator = manipulator;
	}

	@Override
	public Bundle loadBundle(String bundleName) throws JMOPPersistenceException {
		File file = locator.bundleDataFile(bundleName);
		return manipulator.loadBundleData(file);
	}

}
