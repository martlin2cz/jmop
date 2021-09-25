package cz.martlin.jmop.common.storages.builders;

import java.io.File;

import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.builders.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.loader.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.loader.CommonMusicdataLoader;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesLoader;
import cz.martlin.jmop.common.storages.loader.bundles.ByIdentifierBundlesLoader;
import cz.martlin.jmop.common.storages.loader.bundles.FailsaveBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.bundles.FailsaveBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.loader.bundles.LoggingBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.bundles.LoggingBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.loader.bundles.impls.BundlesDirBundleIdentifierLister;
import cz.martlin.jmop.common.storages.loader.bundles.impls.ByIdentifierBundleMusicdataFileLoader;
import cz.martlin.jmop.common.storages.loader.playlists.BasePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.playlists.BasePlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.loader.playlists.BasePlaylistsLoader;
import cz.martlin.jmop.common.storages.loader.playlists.ByIdentifierPlaylistsLoader;
import cz.martlin.jmop.common.storages.loader.playlists.FailsavePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.playlists.FailsavePlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.loader.playlists.LoggingPlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.playlists.LoggingPlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.loader.playlists.impls.BundlesDirPlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.loader.playlists.impls.ByIdentifierPlaylistMusicdataFileLoader;
import cz.martlin.jmop.common.storages.loader.tracks.BaseTracksLoader;
import cz.martlin.jmop.common.storages.loader.tracks.impls.BundleMusicfileTracksLoader;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.BasePlaylistLocator;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.utils.Builder;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

public class LoaderBuilder implements Builder<BaseMusicdataLoader> {

	/////////////////////////////////////////////////////////////////////////////////////

	public <BIT> BaseMusicdataLoader create(DirsLayout dirsLayout, File root, boolean failsave, Locators locators,
			BaseMusicdataFileManipulator manipulator, BaseFileSystemAccessor fs, BaseErrorReporter reporter) {

		if (dirsLayout == DirsLayout.BUNDLES_DIR) {
			return createBundlesDir(locators, manipulator, fs, root, failsave, reporter);
		} else {
			throw new UnsupportedOperationException("Nothing else is currently supported");
		}

	}

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

	private BaseTracksLoader createTracksLoader(BaseBundleDataLocator locator,
			BaseMusicdataFileManipulator manipulator) {

		return new BundleMusicfileTracksLoader(locator, manipulator);
	}

	/////////////////////////////////////////////////////////////////////////////////////

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
