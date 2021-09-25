package cz.martlin.jmop.common.storages.loader.bundles.impls;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.storages.BundlesDirStorageComponent;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

/**
 * The {@link BaseBundlesIdentifiersLister} for the bundles dirs. Lists names of
 * the dirs, which are bundles dirs.
 * 
 * @author martin
 *
 */
public class BundlesDirBundleIdentifierLister //
		implements BaseBundlesIdentifiersLister<String>, BundlesDirStorageComponent {

	private final File root;
	private final BaseFileSystemAccessor fs;
	private final BaseBundleDataLocator locator;

	public BundlesDirBundleIdentifierLister(File root, BaseFileSystemAccessor fs, BaseBundleDataLocator locator) {
		super();
		this.root = root;
		this.fs = fs;
		this.locator = locator;
	}

	@Override
	public Set<String> loadBundlesIdentifiers() throws JMOPPersistenceException {
		return fs.listDirectories(root) //
				.filter((d) -> hasBundleDataFile(d))
				.map((d) -> d.getName()) //
				.collect(Collectors.toSet());
	}

	private boolean hasBundleDataFile(File dir) {
		try {
			return fs.listFiles(dir) //
					.anyMatch((f) -> locator.isBundleDataFile(f));
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not check existence of the bundle data file", e);
		}
	}

}
