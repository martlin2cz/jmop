package cz.martlin.jmop.common.storages.builder;

import java.io.File;
import java.util.Objects;

import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.storages.builder.LocatorsBuilder.BundleDataFile;
import cz.martlin.jmop.common.storages.builder.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.builder.MusicdataManipulatorBuilder.PlaylistFileFormat;
import cz.martlin.jmop.common.storages.components.BaseStorageConfiguration;
import cz.martlin.jmop.common.storages.config.AllTracksPlaylistStorageConfig;
import cz.martlin.jmop.common.storages.filesystem.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.filesystem.DefaultFileSystemAccessor;
import cz.martlin.jmop.common.storages.storage.AllTracksPlaylistStorage;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;
import cz.martlin.jmop.common.storages.storage.LoggingMusicbaseStorage;
import cz.martlin.jmop.common.storages.storage.filesystemer.BaseMusicbaseFilesystemer;
import cz.martlin.jmop.common.storages.storage.filesystemer.BundlesDirsFilesystemer;
import cz.martlin.jmop.common.storages.storage.filesystemer.FailsaveFilesystemer;
import cz.martlin.jmop.common.storages.storage.filesystemer.OneDiredFilesystemer;
import cz.martlin.jmop.common.storages.storage.filesystemer.trackfile.FailsaveTrackFileCreater;
import cz.martlin.jmop.common.storages.storage.filesystemer.trackfile.TrackFileCreater;
import cz.martlin.jmop.common.storages.storage.musicdataloader.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

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

	/**
	 * Creates the storage.
	 * 
	 * @param dirsLayout         the dirs layout: bundles-dir or all-in-one dir ?
	 * @param bundleDataFile     the bundle data file: by using the
	 *                           all-tracks-playlist or primitive text file?
	 * @param failsave           should the storage be fail-save or fail-first?
	 * @param playlistFileFormat the format of the playlist files: xspf or primitive
	 *                           text file?
	 * @param reporter           the error reporter
	 * @param root               the root directory
	 * @param config             the configuration
	 * @param inmemory           the instance of the inmemory musicbase
	 * @return
	 */
	public BaseMusicbaseStorage create(DirsLayout dirsLayout, BundleDataFile bundleDataFile, boolean failsave,
			PlaylistFileFormat playlistFileFormat, BaseErrorReporter reporter, File root,
			BaseStorageConfiguration config, BaseInMemoryMusicbase inmemory) {

		Objects.requireNonNull(root, "The root dir is not specified");

		BaseFileSystemAccessor fs = new DefaultFileSystemAccessor();
		BaseMusicdataFileManipulator manipulator = musicdataBuilder.create(playlistFileFormat, failsave, reporter, fs);
		String playlistFileExtension = manipulator.fileExtension();

		Locators locators = locatorsBuilder.build(root, dirsLayout, bundleDataFile, config, playlistFileExtension);
		BaseMusicdataSaver saver = saverBuilder.create(manipulator, locators, inmemory);
		TrackFileCreater tracksCreater = createTrackFileCreater(fs, locators, failsave, reporter);
		BaseMusicbaseFilesystemer filesystemer = createFilesystemer(dirsLayout, locators, fs, tracksCreater, failsave,
				reporter);

		BaseMusicdataLoader loader = loaderBuilder.create(dirsLayout, root, failsave, locators, manipulator, fs,
				reporter);

		FileSystemedStorage fss = new FileSystemedStorage(filesystemer, null, saver, loader);

		BaseMusicbaseStorage atpWrappedOrNot;
		if (bundleDataFile == BundleDataFile.ALL_TRACKS_PLAYLIST) {
			String allTracksPlaylistName = ((AllTracksPlaylistStorageConfig) config).getAllTracksPlaylistName();
			atpWrappedOrNot = new AllTracksPlaylistStorage(fss, inmemory, allTracksPlaylistName);
		} else {
			atpWrappedOrNot = fss;
		}

		return new LoggingMusicbaseStorage(atpWrappedOrNot);
	}

	/**
	 * Creates the instance of the track file creater.
	 * 
	 * @param fs
	 * @param locators
	 * @param failsave
	 * @param reporter
	 * @return
	 */
	private TrackFileCreater createTrackFileCreater(BaseFileSystemAccessor fs, Locators locators, boolean failsave,
			BaseErrorReporter reporter) {

		if (failsave) {
			return new FailsaveTrackFileCreater(locators.tracksFilesLocator, fs, reporter);
		} else {
			return new TrackFileCreater(locators.tracksFilesLocator, fs);
		}
	}

	/**
	 * Creates the filesystemer based on the dirs layout.
	 * 
	 * @param dirsLayout
	 * @param locators
	 * @param fs
	 * @param tracksCreater
	 * @param failsave
	 * @param reporter
	 * @return
	 */
	private BaseMusicbaseFilesystemer createFilesystemer(DirsLayout dirsLayout, Locators locators,
			BaseFileSystemAccessor fs, TrackFileCreater tracksCreater, boolean failsave, BaseErrorReporter reporter) {

		BaseMusicbaseFilesystemer fsr;
		switch (dirsLayout) {
		case ALL_IN_ONE_DIR:
			fsr = new OneDiredFilesystemer(tracksCreater);
			break;

		case BUNDLES_DIR:
			fsr = new BundlesDirsFilesystemer(fs, locators.bundlesDirLocatorOrNull, locators.playlistsLocator,
					locators.tracksFilesLocator, tracksCreater);
			break;

		default:
			throw new IllegalArgumentException();
		}

		if (failsave) {
			return new FailsaveFilesystemer(fsr, reporter);
		} else {
			return fsr;
		}
	}

	/**
	 * Specifies how the bundles and all the files are to be layouted on the disk.
	 * 
	 * @author martin
	 *
	 */
	public static enum DirsLayout {
		/**
		 * Each directory for bundle.
		 */
		BUNDLES_DIR,

		/**
		 * All files are located in one directory.
		 */
		ALL_IN_ONE_DIR;
	}

}
