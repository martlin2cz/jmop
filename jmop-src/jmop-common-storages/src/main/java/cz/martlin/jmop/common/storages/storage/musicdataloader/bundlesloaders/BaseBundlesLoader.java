package cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.storage.musicdataloader.CommonMusicdataLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An component of {@link CommonMusicdataLoader} which is responsible for the
 * loading of the bundles.
 * 
 * It's component of {@link CommonMusicdataLoader}.
 * 
 * @author martin
 *
 */
public interface BaseBundlesLoader {

	/**
	 * Loads the bundles.
	 * @return
	 * @throws JMOPPersistenceException
	 */
	Set<Bundle> loadBundles() throws JMOPPersistenceException;

}
