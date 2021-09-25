package cz.martlin.jmop.common.storages.loader.tracks;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link BaseTracksLoader} which specifies how to load
 * a track based on that identifier.
 * 
 * @author martin
 *
 * @param <IT>
 */
public interface BaseTracksByIdentifierLoader<IT> {

	/**
	 * Loads the particular track.
	 * 
	 * @param bundle
	 * @param indentifier
	 * @return
	 */
	Track loadTrack(Bundle bundle, IT indentifier) throws JMOPPersistenceException;

}
