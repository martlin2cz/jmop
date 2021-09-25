package cz.martlin.jmop.common.storages.loader.bundles;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.loader.CommonMusicdataLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An component of {@link CommonMusicdataLoader} which is responsible for the
 * loading of the bundles.
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
