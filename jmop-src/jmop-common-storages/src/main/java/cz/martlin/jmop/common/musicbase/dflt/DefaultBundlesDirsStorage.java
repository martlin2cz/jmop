package cz.martlin.jmop.common.musicbase.dflt;

import java.io.File;

import cz.martlin.jmop.common.musicbase.commons.BundlesDirsStorage;
import cz.martlin.jmop.common.musicbase.commons.SaverWithAllTrackPlaylist;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.simplefs.SimpleLocator;
import cz.martlin.jmop.core.config.BaseConfiguration;

public class DefaultBundlesDirsStorage extends BundlesDirsStorage {

	public DefaultBundlesDirsStorage(BaseConfiguration config, BaseInMemoryMusicbase musicbase, File root,
			String playlistFileExtension, String trackFileExtension, String allTracksPlaylistName) {

		super(new ElectronicFileSystemAccessor(), //
				new SimpleLocator(root, playlistFileExtension, trackFileExtension), //
				new SaverWithAllTrackPlaylist(allTracksPlaylistName, musicbase, //
						new XSPFFilesSaver()));
	}

}
