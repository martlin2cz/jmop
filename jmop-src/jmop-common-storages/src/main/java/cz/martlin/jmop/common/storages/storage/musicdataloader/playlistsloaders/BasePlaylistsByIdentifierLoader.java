package cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders;

import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.BaseTracksLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link BaseTracksLoader} which specifies how to load
 * a playlist based on the identifier.
 * 
 * It's component of {@link ByIdentifierPlaylistsLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public interface BasePlaylistsByIdentifierLoader<IT> {

	/**
	 * Loads the particular playlist.
	 * 
	 * @param bundle
	 * @param indentifier
	 * @param tracksMap
	 * @return
	 */
	Playlist loadPlaylist(Bundle bundle, IT indentifier, Map<String, Track> tracksMap) throws JMOPPersistenceException;

}
