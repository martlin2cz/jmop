package cz.martlin.jmop.common.storages.loader.playlists;

import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.loader.tracks.BaseTracksLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link BaseTracksLoader} which specifies how to load
 * a playlist based on the identifier.
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
