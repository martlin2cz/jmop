package cz.martlin.jmop.common.storages.builder;

import java.io.File;

import cz.martlin.jmop.common.storages.builder.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.builder.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.filesystem.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BasePlaylistLocator;
import cz.martlin.jmop.common.storages.storage.musicdataloader.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.CommonMusicdataLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.BaseBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.BaseBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.BaseBundlesLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.ByIdentifierBundlesLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.FailsaveBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.FailsaveBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.LoggingBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.LoggingBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.impls.BundlesDirBundleIdentifierLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.impls.ByIdentifierBundleMusicdataFileLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.BasePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.BasePlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.BasePlaylistsLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.ByIdentifierPlaylistsLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.FailsavePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.FailsavePlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.LoggingPlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.LoggingPlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.impls.BundlesDirPlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.impls.ByIdentifierPlaylistMusicdataFileLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.BaseTracksLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.impls.BundleMusicfileTracksLoader;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * The Loaders builder.
 * 
 * @author martin
 *
 */
public class LoaderBuilder implements Builder<BaseMusicdataLoader> {

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates the loader.
	 * 
	 * @param <BIT>
	 * @param dirsLayout
	 * @param root
	 * @param failsave
	 * @param locators
	 * @param manipulator
	 * @param fs
	 * @param reporter
	 * @return
	 */
	public <BIT> BaseMusicdataLoader create(DirsLayout dirsLayout, File root, boolean failsave, Locators locators,
			BaseMusicdataFileManipulator manipulator, BaseFileSystemAccessor fs, BaseErrorReporter reporter) {

		if (dirsLayout == DirsLayout.BUNDLES_DIR) {
			return createBundlesDir(locators, manipulator, fs, root, failsave, reporter);
		} else {
			throw new UnsupportedOperationException("Nothing else is currently supported");
		}

	}

	/**
	 * Creates the bundles dir loader.
	 * @param locators
	 * @param manipulator
	 * @param fs
	 * @param root
	 * @param failsave
	 * @param reporter
	 * @return
	 */
	public BaseMusicdataLoader createBundlesDir(Locators locators, BaseMusicdataFileManipulator manipulator,
			BaseFileSystemAccessor fs, File root, boolean failsave, BaseErrorReporter reporter) {

		BaseBundlesLoader bundlesLoader = createBundlesLoader(root, failsave, reporter, fs, locators.bundleDataLocator,
				manipulator);
		BasePlaylistsLoader playlistsLoader = createPlaylistsLoader(failsave, reporter, fs, locators.bundlesDirLocatorOrNull,
				locators.playlistsLocator, manipulator);
		BaseTracksLoader tracksLoader = createTracksLoader(locators.bundleDataLocator, manipulator);

		return new CommonMusicdataLoader(bundlesLoader, playlistsLoader, tracksLoader);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates the tracks loader.
	 * 
	 * @param locator
	 * @param manipulator
	 * @return
	 */
	private BaseTracksLoader createTracksLoader(BaseBundleDataLocator locator,
			BaseMusicdataFileManipulator manipulator) {

		return new BundleMusicfileTracksLoader(locator, manipulator);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates the playlists loader.
	 * 
	 * @param <T>
	 * @param failsave
	 * @param reporter
	 * @param fs
	 * @param bundleDirsLocator
	 * @param playlistLocator
	 * @param manipulator
	 * @return
	 */
	private <T> BasePlaylistsLoader createPlaylistsLoader(boolean failsave, BaseErrorReporter reporter,
			BaseFileSystemAccessor fs, BaseBundlesDirLocator bundleDirsLocator, BasePlaylistLocator playlistLocator,
			BaseMusicdataFileManipulator manipulator) {

		BasePlaylistsIdentifiersLister<File> playlistsIdLister = new BundlesDirPlaylistsIdentifiersLister(fs,
				bundleDirsLocator, playlistLocator);
		BasePlaylistsByIdentifierLoader<File> playlistsIdLoader = new ByIdentifierPlaylistMusicdataFileLoader(
				manipulator);

		if (failsave) {
			playlistsIdLister = new FailsavePlaylistsIdentifiersLister<>(playlistsIdLister, reporter);
			playlistsIdLoader = new FailsavePlaylistsByIdentifierLoader<>(playlistsIdLoader, reporter);
		}
		
		playlistsIdLister = new LoggingPlaylistsIdentifiersLister<>(playlistsIdLister);
		playlistsIdLoader = new LoggingPlaylistsByIdentifierLoader<>(playlistsIdLoader);
		
		return new ByIdentifierPlaylistsLoader<>(playlistsIdLister, playlistsIdLoader);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates bundles loader.
	 * 
	 * @param <T>
	 * @param root
	 * @param failsave
	 * @param reporter
	 * @param fs
	 * @param locator
	 * @param manipulator
	 * @return
	 */
	private <T> BaseBundlesLoader createBundlesLoader(File root, boolean failsave, BaseErrorReporter reporter,
			BaseFileSystemAccessor fs, BaseBundleDataLocator locator, BaseMusicdataFileManipulator manipulator) {

		BaseBundlesIdentifiersLister<String> bundlesIdLister = new BundlesDirBundleIdentifierLister(root, fs, locator);
		BaseBundlesByIdentifierLoader<String> bundlesIdLoader = new ByIdentifierBundleMusicdataFileLoader(locator,
				manipulator);

		if (failsave) {
			bundlesIdLister = new FailsaveBundlesIdentifiersLister<>(bundlesIdLister, reporter);
			bundlesIdLoader = new FailsaveBundlesByIdentifierLoader<>(bundlesIdLoader, reporter);
		}

		bundlesIdLister = new LoggingBundlesIdentifiersLister<>(bundlesIdLister);
		bundlesIdLoader = new LoggingBundlesByIdentifierLoader<>(bundlesIdLoader);
		
		return new ByIdentifierBundlesLoader<>(bundlesIdLister, bundlesIdLoader);
	}

	/////////////////////////////////////////////////////////////////////////////////////
}
