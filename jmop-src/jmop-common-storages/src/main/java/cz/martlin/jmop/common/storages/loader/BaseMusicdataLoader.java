package cz.martlin.jmop.common.storages.loader;

import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An general loader of the muscdata (bundles, playlist, tracks). Performs the
 * load of the whole musicbase into the specified in-memory musicbase.
 * 
 * @author martin
 *
 */
public interface BaseMusicdataLoader {

	/**
	 * Loads the (whole) musicbase data into the provided inmemory musicbase
	 * instance.
	 * 
	 * @param inmemory
	 * @throws JMOPPersistenceException
	 */
	void load(BaseInMemoryMusicbase inmemory) throws JMOPPersistenceException;

}
