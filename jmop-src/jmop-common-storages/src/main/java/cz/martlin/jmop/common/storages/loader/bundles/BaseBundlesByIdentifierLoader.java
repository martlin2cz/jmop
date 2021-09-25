package cz.martlin.jmop.common.storages.loader.bundles;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link BaseBundlesLoader} which specifies how to load
 * a bundle based on the identifier.
 * 
 * @author martin
 *
 * @param <IT>
 */
public interface BaseBundlesByIdentifierLoader<IT> {

	/**
	 * Loads the bundle of the given identifier.
	 * 
	 * @param indentifier
	 * @return
	 */
	Bundle loadBundle(IT indentifier) throws JMOPPersistenceException;

}
