package cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link ByIdentifierTracksLoader} which specifies how to load
 * a track based on that identifier.
 * 
 * It's component of {@link ByIdentifierTracksLoader}.
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
