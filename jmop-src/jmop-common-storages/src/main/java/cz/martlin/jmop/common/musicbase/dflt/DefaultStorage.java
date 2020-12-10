package cz.martlin.jmop.common.musicbase.dflt;

import java.io.File;

import cz.martlin.jmop.common.musicbase.commons.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.common.musicbase.commons.BaseFilesLocator;
import cz.martlin.jmop.common.musicbase.commons.BaseMusicdataLoader;
import cz.martlin.jmop.common.musicbase.commons.BaseMusicdataSaver;
import cz.martlin.jmop.common.musicbase.commons.BundlesDirsStorage;
import cz.martlin.jmop.common.musicbase.commons.LoaderWithAllTrackPlaylist;
import cz.martlin.jmop.common.musicbase.commons.SaverWithAllTrackPlaylist;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.simplefs.SimpleLocator;
import cz.martlin.jmop.common.storages.simplefs.SimpleExtendedPlaylistManipulator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;

public class DefaultStorage extends BundlesDirsStorage {

	private DefaultStorage(BaseFileSystemAccessor fs, BaseFilesLocator locator, BaseMusicdataSaver saver,
			BaseMusicdataLoader loader) {
		super(fs, locator, saver, loader);
	}

	public static DefaultStorage create(File root,
			String allTracksPlaylistName, TrackFileFormat format, BaseInMemoryMusicbase musicbase) {
		
		BaseFileSystemAccessor fs = new ElectronicFileSystemAccessor();
//		new SimpleExtendedPlaylistManipulator(fs);
		BaseExtendedPlaylistManipulator manipulator = new XSPFFilesManipulator();
		String playlistFileExtension = manipulator.fileExtension();
		
		String trackFileExtension = format.fileExtension();
		BaseFilesLocator locator = new SimpleLocator(root, playlistFileExtension, trackFileExtension);
		
		BaseMusicdataSaver saver = new SaverWithAllTrackPlaylist(allTracksPlaylistName, musicbase, manipulator, locator);
		BaseMusicdataLoader loader = new LoaderWithAllTrackPlaylist(allTracksPlaylistName, fs, locator, manipulator);

		return new DefaultStorage(fs, locator, saver, loader);
	}

}
