package cz.martlin.jmop.common.storages.builder;

import java.io.File;

import cz.martlin.jmop.common.storages.builder.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.builder.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.components.BaseStorageConfiguration;
import cz.martlin.jmop.common.storages.config.AllTracksPlaylistStorageConfig;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.AbstractCommonLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.AllInOneDirDataFilesLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BasePlaylistLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseTrackFileLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BundlesDirDataFilesLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BaseBundleDataFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BaseBundlesDirNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BasePlaylistFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BaseTrackFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.impls.AllTracksPlaylistBundleDataFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.impls.DefaultBundlesDirNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.impls.DefaultPlaylistFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.impls.DefaultTrackFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.impls.SimpleBundleDataFileNamer;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The locators builder.
 * 
 * @author martin
 *
 */
public class LocatorsBuilder implements Builder<Locators> {

	/**
	 * Builds the locators.
	 * 
	 * @param root
	 * @param dirsLayout
	 * @param bundleDataFile
	 * @param config
	 * @param playlistFileExtension
	 * @return
	 */
	public Locators build(File root, DirsLayout dirsLayout, BundleDataFile bundleDataFile,
			BaseStorageConfiguration config, String playlistFileExtension) {

		BasePlaylistFileNamer playlistNamer = buildPlaylistFileNamer(playlistFileExtension);
		BaseTrackFileNamer trackNamer = buildTracksFileNamer(config);
		BaseBundleDataFileNamer bundleNamer = buildTheBundleDataFileNamer(bundleDataFile, config, playlistNamer);
		
		AbstractCommonLocator locator = buildTheCommonLocator(root, dirsLayout, bundleNamer, playlistNamer, trackNamer);
		if (dirsLayout == DirsLayout.BUNDLES_DIR) {
			BaseBundlesDirLocator bundleDirLocator = buildBundlesDirLocator(root, bundleNamer, playlistNamer, trackNamer);
			return new Locators(locator, locator, locator, bundleDirLocator);
		} else {
			return new Locators(locator, locator, locator);
		}

	}

	/**
	 * Builds bundles dir locator.
	 * 
	 * @param root
	 * @param bundleNamer
	 * @param playlistNamer
	 * @param trackNamer
	 * @return
	 */
	private BaseBundlesDirLocator buildBundlesDirLocator(File root, BaseBundleDataFileNamer bundleNamer, BasePlaylistFileNamer playlistNamer, BaseTrackFileNamer trackNamer) {
		BaseBundlesDirNamer bundlesDirNamer = new DefaultBundlesDirNamer();
		return new BundlesDirDataFilesLocator(root, bundlesDirNamer, bundleNamer, playlistNamer, trackNamer);
	}

	/**
	 * Builds the common locator.
	 * 
	 * @param root
	 * @param dirsLayout
	 * @param bundleNamer
	 * @param playlistNamer
	 * @param trackNamer
	 * @return
	 */
	private AbstractCommonLocator buildTheCommonLocator(File root, DirsLayout dirsLayout, BaseBundleDataFileNamer bundleNamer, BasePlaylistFileNamer playlistNamer, BaseTrackFileNamer trackNamer) {
		switch (dirsLayout) {
		case ALL_IN_ONE_DIR: {
			return new AllInOneDirDataFilesLocator(root, bundleNamer, playlistNamer, trackNamer);
		}
		case BUNDLES_DIR: {
			BaseBundlesDirNamer bundlesDirNamer = new DefaultBundlesDirNamer();
			return new BundlesDirDataFilesLocator(root, bundlesDirNamer, bundleNamer, playlistNamer, trackNamer);
		}
		default:
			throw new IllegalArgumentException(dirsLayout.toString());
		}
	}

	/**
	 * Builds tracks file namer.
	 * 
	 * @param config
	 * @return
	 */
	private DefaultTrackFileNamer buildTracksFileNamer(BaseStorageConfiguration config) {
		TrackFileFormat saveFormat = config.trackFileFormat();
		return new DefaultTrackFileNamer(saveFormat);
	}

	/**
	 * Builds the playlist file namer.
	 * 
	 * @param playlistFileExtension
	 * @return
	 */
	private DefaultPlaylistFileNamer buildPlaylistFileNamer(String playlistFileExtension) {
		return new DefaultPlaylistFileNamer(playlistFileExtension);
	}

	/**
	 * Builds the bundle data file namer.
	 * 
	 * @param bundleDataFile
	 * @param config
	 * @param playlistFileNamer
	 * @return
	 */
	private BaseBundleDataFileNamer buildTheBundleDataFileNamer(BundleDataFile bundleDataFile,
			BaseStorageConfiguration config, BasePlaylistFileNamer playlistFileNamer) {

		switch (bundleDataFile) {
		case ALL_TRACKS_PLAYLIST:
			String allTracksPlaylistName = ((AllTracksPlaylistStorageConfig) config).getAllTracksPlaylistName();
			return new AllTracksPlaylistBundleDataFileNamer(allTracksPlaylistName, playlistFileNamer);
		case SIMPLE:
			return new SimpleBundleDataFileNamer();
		default:
			throw new IllegalArgumentException(bundleDataFile.toString());
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The bundle data, playlist data  tracks file and bundles dir locator (if any) locators collection.
	 * 
	 * 
	 * @author martin
	 *
	 */
	public static class Locators {
		public final BaseBundleDataLocator bundleDataLocator;
		public final BasePlaylistLocator playlistsLocator;
		public final BaseTrackFileLocator tracksFilesLocator;
		public final BaseBundlesDirLocator bundlesDirLocatorOrNull;

		public Locators(BaseBundleDataLocator bundleDataLocator, BasePlaylistLocator playlistsLocator,
				BaseTrackFileLocator tracksFilesLocator) {
			super();
			this.bundleDataLocator = bundleDataLocator;
			this.playlistsLocator = playlistsLocator;
			this.tracksFilesLocator = tracksFilesLocator;
			this.bundlesDirLocatorOrNull = null;
		}

		public Locators(BaseBundleDataLocator bundleDataLocator, BasePlaylistLocator playlistsLocator,
				BaseTrackFileLocator tracksFilesLocator, BaseBundlesDirLocator bundlesDirLocator) {
			super();
			this.bundleDataLocator = bundleDataLocator;
			this.playlistsLocator = playlistsLocator;
			this.tracksFilesLocator = tracksFilesLocator;
			this.bundlesDirLocatorOrNull = bundlesDirLocator;
		}
	}

	/**
	 * The way of storing the bundle data.
	 * @author martin
	 *
	 */
	public static enum BundleDataFile {
		/**
		 * Bundle data gets stored by using the all-tracks-playlist.
		 */
		ALL_TRACKS_PLAYLIST, 
		
		/**
		 * Bundle data gets stored in simple text file.
		 */
		SIMPLE;
	}
}
