package cz.martlin.jmop.common.storages.loader.tracks;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.loader.CommonMusicdataLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An component of {@link CommonMusicdataLoader} which is responsible for the
 * loading of the tracks.
 * 
 * @author martin
 *
 */
public interface BaseTracksLoader {

	/**
	 * Loads the tracks from the given bundle.
	 * 
	 * @param bundle
	 * @return
	 * @throws JMOPPersistenceException
	 */
	Set<Track> loadTracks(Bundle bundle) throws JMOPPersistenceException;

}
