package cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link ByIdentifierTracksLoader} which specifies how to list
 * list of tracks identifiers.
 * 
 * It's component of {@link ByIdentifierTracksLoader}.
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
