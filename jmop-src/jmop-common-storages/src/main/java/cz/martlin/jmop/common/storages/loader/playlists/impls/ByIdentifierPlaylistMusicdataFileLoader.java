package cz.martlin.jmop.common.storages.loader.playlists.impls;

import java.io.File;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.loader.playlists.BasePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The {@link BasePlaylistsByIdentifierLoader} which utilises the
 * {@link BaseMusicdataFileManipulator}.
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
