package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.local.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.BasePlaylistFilesLoaderStorer;

public class ElectronicBundlesSource implements BaseBundlesLocalSource {

	private final BaseFilesLocator locator;
	private final BaseFileSystemAccessor filesystem;
	private final BasePlaylistFilesLoaderStorer pfls;

	public ElectronicBundlesSource(BaseFilesLocator locator, BaseFileSystemAccessor filesystem,
			BasePlaylistFilesLoaderStorer pfls) {
		super();
		this.locator = locator;
		this.filesystem = filesystem;
		this.pfls = pfls;
	}

	///////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<Bundle> listBundles() {
		Set<File> bundlesDirectories = listBundleDirectories();
		List<Bundle> bundles = bundlesDirectories.stream() //
				.map((d) -> loadBundle(d)) //
				.collect(Collectors.toList()); //

		bundles.sort((b1, b2) -> b1.compareTo(b2));

		return bundles;
	}

	@Override
	public void createBundle(Bundle bundle) {
		File directory = locator.getDirectoryOfBundle(bundle);
		filesystem.createDirectory(directory);

		saveBundle(bundle);
	}

	@Override
	public void deleteBundle(Bundle bundle) {
		File directory = locator.getDirectoryOfBundle(bundle);
		filesystem.deleteDirectory(directory);
	}

	@Override
	public void renameBundle(Bundle oldBundle, Bundle newBundle) {
		File oldDirectory = locator.getDirectoryOfBundle(oldBundle);
		File newDirectory = locator.getDirectoryOfBundle(newBundle);

		filesystem.renameDirectory(oldDirectory, newDirectory);
		saveBundle(newBundle);
		// TODO save all the playlists inside TODO will be needed?
	}

	@Override
	public void saveBundleChanges(Bundle bundle) {
		saveBundle(bundle);
	}

	///////////////////////////////////////////////////////////////////////////

	private Set<File> listBundleDirectories() {
		File directory = locator.getRootDirectory();
		Predicate<File> matcher = (d) -> isBundleDirectory(d);
		return filesystem.listDirectoriesMatching(directory, matcher);
	}

	private boolean isBundleDirectory(File directory) {
		// TODO contains all tracks playlist?
		return true;
	}

	private Bundle loadBundle(File directory) {
		// TODO load bundle ...
		return null;
	}

	private void saveBundle(Bundle bundle) {
		// TODO save all tracks playlist

	}

}
