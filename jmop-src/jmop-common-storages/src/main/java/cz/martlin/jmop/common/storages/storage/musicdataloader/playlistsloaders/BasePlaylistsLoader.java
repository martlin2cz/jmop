package cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders;

import java.util.Map;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.musicdataloader.CommonMusicdataLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An component of {@link CommonMusicdataLoader} which is responsible for the
 * loading of the playlists.
 * 
 * It's component of {@link CommonMusicdataLoader}.
 * 
 * @author martin
 *
 */
public interface BasePlaylistsLoader {

	/**
	 * Loads the playlists from the given bundle.
	 * 
	 * @param bundle
	 * @param tracksMap
	 * @return
	 * @throws JMOPPersistenceException
	 */
	Set<Playlist> loadPlaylists(Bundle bundle, Map<String, Track> tracksMap) throws JMOPPersistenceException;

}
