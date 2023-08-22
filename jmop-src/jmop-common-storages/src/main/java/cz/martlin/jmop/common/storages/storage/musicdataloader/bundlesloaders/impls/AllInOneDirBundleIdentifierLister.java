package cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.impls;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.storages.components.AllInOneDirStorageComponent;
import cz.martlin.jmop.common.storages.filesystem.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.BaseBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.ByIdentifierBundlesLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The bundles identifiers lister for the bundles dirs. Lists all the bundle data files in the all-in-one-dir-storage.
 * 
 * It's component of {@link ByIdentifierBundlesLoader}.
 * 
 * @author martin
 *
 */
public class AllInOneDirBundleIdentifierLister //
		implements BaseBundlesIdentifiersLister<String>, AllInOneDirStorageComponent {

	private final File root;
	private final BaseFileSystemAccessor fs;
	private final BaseBundleDataLocator locator;

	public AllInOneDirBundleIdentifierLister(File root, BaseFileSystemAccessor fs, BaseBundleDataLocator locator) {
		super();
		this.root = root;
		this.fs = fs;
		this.locator = locator;
	}

	@Override
	public Set<String> loadBundlesIdentifiers() throws JMOPPersistenceException {
		return fs.listFiles(root) //
				.filter(f -> locator.isBundleDataFile(f)) //
				.map(f -> f.getName()) //
				.collect(Collectors.toSet()); //
	}

}
