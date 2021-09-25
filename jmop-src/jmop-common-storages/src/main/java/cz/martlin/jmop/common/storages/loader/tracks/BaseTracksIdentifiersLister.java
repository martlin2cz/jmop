package cz.martlin.jmop.common.storages.loader.tracks;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link BaseTracksLoader} which specifies how to list
 * list of tracks identifiers.
 * 
 * @author martin
 *
 * @param <IT>
 */
public interface BaseTracksIdentifiersLister<IT> {

	/**
	 * Loads the identifier of the tracks in the given bundle.
	 * 
	 * @param bundle
	 * @return
	 */
	Set<IT> loadTracksIdentifiers(Bundle bundle) throws JMOPPersistenceException;

}
