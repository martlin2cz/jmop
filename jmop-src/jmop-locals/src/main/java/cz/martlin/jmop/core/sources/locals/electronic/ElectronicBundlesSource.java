package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.local.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.BaseBundleFilesLoaderStorer;

public class ElectronicBundlesSource implements BaseBundlesLocalSource {

	private final BaseFilesLocator locator;
	private final BaseFileSystemAccessor filesystem;
	private final BaseBundleFilesLoaderStorer bfls;
	private final String allTracksPlaylistName;

	public ElectronicBundlesSource(BaseConfiguration config, BaseFilesLocator locator,
			BaseFileSystemAccessor filesystem, BaseBundleFilesLoaderStorer bfls) {
		super();
		this.locator = locator;
		this.filesystem = filesystem;
		this.bfls = bfls;
		this.allTracksPlaylistName = config.getAllTracksPlaylistName();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public List<Bundle> listBundles() throws JMOPSourceException {
		Set<File> bundlesDirectories = listBundleDirectories();
		List<Bundle> bundles = bundlesDirectories.stream() //
				.map((d) -> loadBundleOrHandleError(d)) //
				.filter((b) -> b != null) //
				.collect(Collectors.toList()); //

		bundles.sort((b1, b2) -> b1.compareTo(b2));

		return bundles;
	}

	@Override
	public void createBundle(Bundle bundle) throws JMOPSourceException {
		File directory = directoryOfBundle(bundle);
		filesystem.createDirectory(directory);

		saveBundle(bundle);
	}

	@Override
	public void deleteBundle(Bundle bundle) throws JMOPSourceException {
		File directory = directoryOfBundle(bundle);
		filesystem.deleteDirectory(directory);
	}

	@Override
	public void renameBundle(Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		File oldDirectory = directoryOfBundle(oldBundle);
		File newDirectory = directoryOfBundle(newBundle);

		filesystem.renameDirectory(oldDirectory, newDirectory);
		saveBundle(newBundle);
		// TODO save all the playlists inside TODO will be needed?
	}

	@Override
	public void saveBundleChanges(Bundle bundle) throws JMOPSourceException {
		saveBundle(bundle);
	}

	///////////////////////////////////////////////////////////////////////////
	private File directoryOfBundle(Bundle bundle) {
		String bundleName = bundle.getName();
		File directory = locator.getDirectoryOfBundle(bundleName);
		return directory;
	}

	private Set<File> listBundleDirectories() throws JMOPSourceException {
		File directory = locator.getRootDirectory();
		Predicate<File> matcher = (d) -> isBundleDirectoryOrHandleError(d);
		return filesystem.listDirectoriesMatching(directory, matcher);
	}

	private boolean isBundleDirectoryOrHandleError(File directory) {
		try {
			return isBundleDirectory(directory);
		} catch (JMOPSourceException e) {
			// TODO handle error
			return false;
		}
	}

	private boolean isBundleDirectory(File directory) throws JMOPSourceException {
		File file = allTracksPlaylistFile(directory);
		return filesystem.existsFile(file);
	}

	private Bundle loadBundleOrHandleError(File dir) {
		try {
			return loadBundle(dir);
		} catch (JMOPSourceException e) {
			// TODO handle error
			return null;
		}
	}

	private Bundle loadBundle(File dir) throws JMOPSourceException {
		File allTracksPlaylistFile = allTracksPlaylistFile(dir);
		return bfls.loadBundle(allTracksPlaylistFile);
	}

	private File allTracksPlaylistFile(File bundleDir) {
		String bundleDirectoryName = bundleDir.getName();
		String playlistName = allTracksPlaylistName;
		return locator.getFileOfPlaylist(bundleDirectoryName, playlistName);
	}

	private void saveBundle(Bundle bundle) throws JMOPSourceException {
		File bundleDir = directoryOfBundle(bundle);
		File file = allTracksPlaylistFile(bundleDir);
		bfls.saveBundle(bundle, file);
	}

}
