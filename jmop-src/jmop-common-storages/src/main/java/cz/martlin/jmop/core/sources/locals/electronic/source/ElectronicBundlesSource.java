package cz.martlin.jmop.core.sources.locals.electronic.source;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.ConsumerWithException;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.base.BaseBundleFilesLoaderStorer;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFilesLocator;

public class ElectronicBundlesSource implements BaseBundlesLocalSource {

	private static final TrackFileLocation ALL_TRACKS_PLAYLIST_FILE_LOCATION = //
			ElectronicPlaylistsSource.LOCATION_OF_PLAYLIST_FILES;

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
		createDirectoriesOfBundle(bundle);

		saveBundle(bundle);
	}

	@Override
	public void deleteBundle(Bundle bundle) throws JMOPSourceException {
		deleteDirectoriesOfBundle(bundle);
	}

	@Override
	public void renameBundle(Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		renameDirectoriesOfBundle(oldBundle, newBundle);
		saveBundle(newBundle);
		// TODO save all the playlists inside TODO will be needed?
	}

	@Override
	public void saveBundleChanges(Bundle bundle) throws JMOPSourceException {
		saveBundle(bundle);
	}

	///////////////////////////////////////////////////////////////////////////

	private void createDirectoriesOfBundle(Bundle bundle) throws JMOPSourceException {
		doWithAllLocations((location) -> {
			File directory = directoryOfBundle(bundle, location);

			boolean exists = filesystem.existsDirectory(directory);
			if (!exists) {
				filesystem.createDirectory(directory);
			}
		});
	}

	private void deleteDirectoriesOfBundle(Bundle bundle) throws JMOPSourceException {
		doWithAllLocations((location) -> {
			File directory = directoryOfBundle(bundle, location);

			boolean exists = filesystem.existsDirectory(directory);
			if (exists) {
				filesystem.deleteDirectory(directory);
			}
		});
	}

	private void renameDirectoriesOfBundle(Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		doWithAllLocations((location) -> {
			File oldDirectory = directoryOfBundle(oldBundle, location);
			File newDirectory = directoryOfBundle(newBundle, location);

			boolean existsOld = filesystem.existsDirectory(oldDirectory);
			boolean existsNew = filesystem.existsDirectory(newDirectory);
			if (existsOld && !existsNew) {
				filesystem.renameDirectory(oldDirectory, newDirectory);
			}
		});
	}

	///////////////////////////////////////////////////////////////////////////

	private void doWithAllLocations(ConsumerWithException<TrackFileLocation> run) throws JMOPSourceException {
		for (TrackFileLocation location : TrackFileLocation.values()) {
			try {
				run.consume(location);
			} catch (Exception e) {
				throw new JMOPSourceException(e);
			}
		}
	}

	private File directoryOfBundle(Bundle bundle, TrackFileLocation location) {
		String bundleName = bundle.getName();
		File directory = locator.getDirectoryOfBundle(bundleName, location);
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
		TrackFileLocation location = ALL_TRACKS_PLAYLIST_FILE_LOCATION;

		File bundleDir = directoryOfBundle(bundle, location);
		File file = allTracksPlaylistFile(bundleDir);

		bfls.saveBundle(bundle, file);
	}

}
