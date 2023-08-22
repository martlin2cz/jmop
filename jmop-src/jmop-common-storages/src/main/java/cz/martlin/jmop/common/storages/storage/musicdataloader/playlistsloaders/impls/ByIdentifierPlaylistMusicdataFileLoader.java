package cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.impls;

import java.io.File;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.BasePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.ByIdentifierPlaylistsLoader;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The playlist-from-file loader which utilises the
 * {@link BaseMusicdataFileManipulator} to load the playlist from the file.
 * 
 * It's component of {@link ByIdentifierPlaylistsLoader}.
 * 
 * @author martin
 *
 */
public class ByIdentifierPlaylistMusicdataFileLoader implements BasePlaylistsByIdentifierLoader<File> {

	private final BaseMusicdataFileManipulator manipulator;

	public ByIdentifierPlaylistMusicdataFileLoader(BaseMusicdataFileManipulator manipulator) {
		super();
		this.manipulator = manipulator;
	}

	@Override
	public Playlist loadPlaylist(Bundle bundle, File file, Map<String, Track> tracksMap)
			throws JMOPPersistenceException {
		return manipulator.loadPlaylistData(bundle, tracksMap, file);
	}

}
