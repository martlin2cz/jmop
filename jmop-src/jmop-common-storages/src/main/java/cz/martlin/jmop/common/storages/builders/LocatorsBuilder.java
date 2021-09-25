package cz.martlin.jmop.common.storages.builders;

import java.io.File;

import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.builders.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.configs.AllTracksPlaylistStorageConfig;
import cz.martlin.jmop.common.storages.configs.BaseStorageConfiguration;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataFileNamer;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirNamer;
import cz.martlin.jmop.common.storages.locators.BasePlaylistFileNamer;
import cz.martlin.jmop.common.storages.locators.BasePlaylistLocator;
import cz.martlin.jmop.common.storages.locators.BaseTrackFileLocator;
import cz.martlin.jmop.common.storages.locators.BaseTrackFilesNamer;
import cz.martlin.jmop.common.storages.locators.impls.AbstractCommonLocator;
import cz.martlin.jmop.common.storages.locators.impls.AllInOneDirDataFilesLocator;
import cz.martlin.jmop.common.storages.locators.impls.AllTracksPlaylistBundleDataFileNamer;
import cz.martlin.jmop.common.storages.locators.impls.BundlesDirDataFilesLocator;
import cz.martlin.jmop.common.storages.locators.impls.DefaultBundlesDirNamer;
import cz.martlin.jmop.common.storages.locators.impls.DefaultPlaylistFileNamer;
import cz.martlin.jmop.common.storages.locators.impls.DefaultTracksFileNamer;
import cz.martlin.jmop.common.storages.locators.impls.SimpleBundleDataFileNamer;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class LocatorsBuilder implements Builder<Locators> {

	public Locators build(File root, DirsLayout dirsLayout, BundleDataFile bundleDataFile,
			BaseStorageConfiguration config, String playlistFileExtension) {

		BasePlaylistFileNamer playlistNamer = buildPlaylistFileNamer(playlistFileExtension);
		BaseTrackFilesNamer trackNamer = buildTracksFileNamer(config);
		BaseBundleDataFileNamer bundleNamer = buildTheBundleDataFileNamer(bundleDataFile, config, playlistNamer);
		
		AbstractCommonLocator locator = buildTheCommonLocator(root, dirsLayout, bundleNamer, playlistNamer, trackNamer);
		if (dirsLayout == DirsLayout.BUNDLES_DIR) {
			BaseBundlesDirLocator bundleDirLocator = buildBundlesDirLocator(root, bundleNamer, playlistNamer, trackNamer);
			return new Locators(locator, locator, locator, bundleDirLocator);
		} else {
			return new Locators(locator, locator, locator);
		}

	}

	private BaseBundlesDirLocator buildBundlesDirLocator(File root, BaseBundleDataFileNamer bundleNamer, BasePlaylistFileNamer playlistNamer, BaseTrackFilesNamer trackNamer) {
		BaseBundlesDirNamer bundlesDirNamer = new DefaultBundlesDirNamer();
		return new BundlesDirDataFilesLocator(root, bundlesDirNamer, bundleNamer, playlistNamer, trackNamer);
	}

	private AbstractCommonLocator buildTheCommonLocator(File root, DirsLayout dirsLayout, BaseBundleDataFileNamer bundleNamer, BasePlaylistFileNamer playlistNamer, BaseTrackFilesNamer trackNamer) {
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

	private DefaultTracksFileNamer buildTracksFileNamer(BaseStorageConfiguration config) {
		TrackFileFormat saveFormat = config.trackFileFormat();
		return new DefaultTracksFileNamer(saveFormat);
	}

	private DefaultPlaylistFileNamer buildPlaylistFileNamer(String playlistFileExtension) {
		return new DefaultPlaylistFileNamer(playlistFileExtension);
	}

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

	public static enum BundleDataFile {
		ALL_TRACKS_PLAYLIST, SIMPLE;
	}
}
