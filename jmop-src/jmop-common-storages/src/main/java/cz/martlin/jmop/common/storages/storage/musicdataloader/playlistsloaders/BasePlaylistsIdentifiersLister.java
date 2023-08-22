package cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.BaseTracksLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link BaseTracksLoader} which specifies how to list
 * list of playlists identifiers.
 * 
 * It's component of {@link ByIdentifierPlaylistsLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public interface BasePlaylistsIdentifiersLister<IT> {

	/**
	 * Lists the playlists identifiers in the given bundle.
	 * 
	 * @param bundle
	 * @return
	 */
	Set<IT> loadPlaylistsIdentifiers(Bundle bundle) throws JMOPPersistenceException;

}
