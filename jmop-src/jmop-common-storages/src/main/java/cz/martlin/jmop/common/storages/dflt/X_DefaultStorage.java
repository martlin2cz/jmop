package cz.martlin.jmop.common.storages.dflt;

import java.io.File;

import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.storages.filesystemer.BaseMusicbaseFilesystemer;
import cz.martlin.jmop.common.storages.filesystemer.BundlesDirsFilesystemer;
import cz.martlin.jmop.common.storages.filesystemer.FileSystemedStorage;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.fs.DefaultFileSystemAccessor;
import cz.martlin.jmop.common.storages.loader.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.loader.CommonMusicdataLoader;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesLoader;
import cz.martlin.jmop.common.storages.loader.bundles.ByIdentifierBundlesLoader;
import cz.martlin.jmop.common.storages.loader.bundles.FailsaveBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.bundles.LoggingBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.playlists.BasePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.playlists.BasePlaylistsLoader;
import cz.martlin.jmop.common.storages.loader.playlists.ByIdentifierPlaylistsLoader;
import cz.martlin.jmop.common.storages.loader.playlists.FailsavePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.playlists.LoggingPlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.tracks.BaseTracksLoader;
//import cz.martlin.jmop.common.storages.locators.BundlesDirLocatorWithMusicdataFilesAndAllTrackPlaylist;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.musicdatasaver.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.musicdatasaver.BundleInATPFileSaverWithMusicdataFileMan;
import cz.martlin.jmop.common.storages.xspf.XSPFPlaylistFilesMusicdataManipulator;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * @deprecated replaced by StorageBuilder.
 * @author martin
 *
 */
@Deprecated
public class X_DefaultStorage extends AllTracksPlaylistStorage {

	public X_DefaultStorage(BaseMusicbaseStorage delegee, BaseInMemoryMusicbase inmemory, String allTracksPlaylistName) {
		super(delegee, inmemory, allTracksPlaylistName);
	}

	public static X_DefaultStorage create(File root, BaseDefaultStorageConfig config, BaseErrorReporter reporter,
			BaseInMemoryMusicbase musicbase) {
		
		throw new UnsupportedOperationException("do not");
//
//		BaseFileSystemAccessor fs = new DefaultFileSystemAccessor();
//		// new SimpleExtendedPlaylistManipulator(fs);
//
//		BaseMusicdataFileManipulator manipulator = XSPFPlaylistFilesMusicdataManipulator.createFailsave(reporter);
//
//		TrackFileFormat format = config.getSaveFormat();
//		String playlistFileExtension = manipulator.fileExtension();
//		String trackFileExtension = format.fileExtension();
//		String allTracksPlaylistName = config.getAllTrackPlaylistName();
//		BundlesDirLocatorWithMusicdataFilesAndAllTrackPlaylist locator = new BundlesDirLocatorWithMusicdataFilesAndAllTrackPlaylist(
//				root, fs, manipulator, playlistFileExtension, allTracksPlaylistName);
//		BaseMusicbaseFilesystemer filesystemer = new BundlesDirsFilesystemer(fs, locator);
//
//		
//		BaseMusicdataSaver saver = new BundleInATPFileSaverWithMusicdataFileMan(locator, manipulator, musicbase,
//				allTracksPlaylistName);
//
//		BaseBundlesByIdentifierLoader<String> bundlesByIdentifierLoader = //
//				new FailsaveBundlesByIdentifierLoader<>( //
//				new LoggingBundlesByIdentifierLoader<>(locator), reporter); //
//		BaseBundlesLoader bundlesLoader = new ByIdentifierBundlesLoader<>(bundlesByIdentifierLoader);
//
//		BasePlaylistsByIdentifierLoader<File> playlistByIdentifierLoader = // 
//				new FailsavePlaylistsByIdentifierLoader<>( //
//				new LoggingPlaylistsByIdentifierLoader<>(locator), reporter);
//		BasePlaylistsLoader playlistsLoader = new ByIdentifierPlaylistsLoader<>(playlistByIdentifierLoader);
//		BaseTracksLoader tracksLoader = locator;
//
//		BaseMusicdataLoader loader = new CommonMusicdataLoader(bundlesLoader, playlistsLoader, tracksLoader);
//
//		BaseMusicbaseStorage storage = new FileSystemedStorage(filesystemer, locator, saver, loader);
//		return new DefaultStorage(storage, musicbase, allTracksPlaylistName);
	}

	@Override
	public String toString() {
		return "DefaultStorage [...]";
	}

}
