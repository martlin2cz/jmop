package cz.martlin.jmop.common.storages.builders;

import java.io.File;
import java.util.Objects;

import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.BundleDataFile;
import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.builders.MusicdataManipulatorBuilder.PlaylistFileFormat;
import cz.martlin.jmop.common.storages.configs.AllTracksPlaylistStorageConfig;
import cz.martlin.jmop.common.storages.configs.BaseStorageConfiguration;
import cz.martlin.jmop.common.storages.dflt.AllTracksPlaylistStorage;
import cz.martlin.jmop.common.storages.filesystemer.BaseMusicbaseFilesystemer;
import cz.martlin.jmop.common.storages.filesystemer.BundlesDirsFilesystemer;
import cz.martlin.jmop.common.storages.filesystemer.FileSystemedStorage;
import cz.martlin.jmop.common.storages.filesystemer.OneDiredFilesystemer;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.fs.DefaultFileSystemAccessor;
import cz.martlin.jmop.common.storages.loader.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.musicdatasaver.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.utils.LoggingMusicbaseStorage;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * An builder of the storage instance.
 * 
 * @author martin
 *
 */
public class StorageBuilder implements Builder<BaseMusicbaseStorage> {
	private final LoaderBuilder loaderBuilder = new LoaderBuilder();
	private final SaverBuilder saverBuilder = new SaverBuilder();
	private final LocatorsBuilder locatorsBuilder = new LocatorsBuilder();
	private final MusicdataManipulatorBuilder musicdataBuilder = new MusicdataManipulatorBuilder();

	public BaseMusicbaseStorage create(DirsLayout dirsLayout,
			BundleDataFile bundleDataFile, boolean failsave, PlaylistFileFormat playlistFileFormat,
			BaseErrorReporter reporter, File root, BaseStorageConfiguration config, TrackFileFormat trackSaveFormat, BaseInMemoryMusicbase inmemory) {
		
		Objects.requireNonNull(root, "The root dir is not specified");
		
		BaseFileSystemAccessor fs = new DefaultFileSystemAccessor();
		BaseMusicdataFileManipulator manipulator = musicdataBuilder.create(playlistFileFormat, failsave, reporter, fs);
		String playlistFileExtension = manipulator.fileExtension();
		
		Locators locators = locatorsBuilder.build(root, dirsLayout, bundleDataFile, config, playlistFileExtension);
		BaseMusicdataSaver saver = saverBuilder.create(manipulator, locators, inmemory);

		BaseMusicbaseFilesystemer filesystemer = createFilesystemer(dirsLayout, locators, fs);
		
		BaseMusicdataLoader loader = loaderBuilder.create(dirsLayout, root, failsave, locators, manipulator, fs, reporter);
		TracksLocator tracksLocator = locators.tracksFilesLocator;
		
		FileSystemedStorage fss = new FileSystemedStorage(filesystemer, null, saver, loader, tracksLocator);
		
		BaseMusicbaseStorage atpWrappedOrNot;
		if (bundleDataFile == BundleDataFile.ALL_TRACKS_PLAYLIST) {
			String allTracksPlaylistName = ((AllTracksPlaylistStorageConfig) config).getAllTracksPlaylistName();
			atpWrappedOrNot = new AllTracksPlaylistStorage(fss, inmemory, allTracksPlaylistName);
		} else {
			atpWrappedOrNot = fss;
		}
		
		return new LoggingMusicbaseStorage(atpWrappedOrNot);		
	}

	private BaseMusicbaseFilesystemer createFilesystemer(DirsLayout dirsLayout, Locators locators,
			BaseFileSystemAccessor fs) {
		switch (dirsLayout) {
		case ALL_IN_ONE_DIR:
			return new OneDiredFilesystemer();

		case BUNDLES_DIR:
			return new BundlesDirsFilesystemer(fs, locators.bundlesDirLocatorOrNull, locators.playlistsLocator,
					locators.tracksFilesLocator);

		default:
			throw new IllegalArgumentException();

		}
	}

	public static enum DirsLayout {
		BUNDLES_DIR, ALL_IN_ONE_DIR;
	}

}
