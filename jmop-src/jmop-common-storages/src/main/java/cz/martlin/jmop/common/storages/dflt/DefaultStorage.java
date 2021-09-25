package cz.martlin.jmop.common.storages.dflt;

import java.io.File;

import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.bundlesdir.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.bundlesdir.BundlesDirsStorage;
import cz.martlin.jmop.common.storages.bundlesdir.LoaderWithAllTrackPlaylist;
import cz.martlin.jmop.common.storages.bundlesdir.SaverWithAllTrackPlaylist;
import cz.martlin.jmop.common.storages.load.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.common.storages.simples.SimpleLocator;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.common.storages.xpfs.XSPFPlaylistFilesManipulator;
import cz.martlin.jmop.common.storages.xpfs._old_XSPFFilesManipulator;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class DefaultStorage extends BundlesDirsStorage {

	private DefaultStorage(BaseFileSystemAccessor fs, BaseFilesLocator locator, BaseMusicdataSaver saver,
			BaseMusicdataLoader loader) {
		super(fs, locator, saver, loader);
	}

	public static DefaultStorage create(File root,
			BaseDefaultStorageConfig config, BaseErrorReporter reporter, BaseInMemoryMusicbase musicbase) {
		
		BaseFileSystemAccessor fs = new ElectronicFileSystemAccessor();
		//		new SimpleExtendedPlaylistManipulator(fs);

		BaseExtendedPlaylistManipulator manipulator = new XSPFPlaylistFilesManipulator(reporter);
		
		TrackFileFormat format = config.getSaveFormat();
		String playlistFileExtension = manipulator.fileExtension();
		String trackFileExtension = format.fileExtension();
		BaseFilesLocator locator = new SimpleLocator(root, playlistFileExtension, trackFileExtension);
		
		String allTracksPlaylistName = config.getAllTrackPlaylistName();
		BaseMusicdataSaver saver = new SaverWithAllTrackPlaylist(allTracksPlaylistName, musicbase, manipulator, locator);
		BaseMusicdataLoader loader = new LoaderWithAllTrackPlaylist(allTracksPlaylistName, fs, locator, manipulator, reporter);

		return new DefaultStorage(fs, locator, saver, loader);
	}

}
