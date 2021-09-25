package cz.martlin.jmop.common.storages.loader.playlists;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.loader.tracks.BaseTracksLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link BaseTracksLoader} which specifies how to list
 * list of playlists identifiers.
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
