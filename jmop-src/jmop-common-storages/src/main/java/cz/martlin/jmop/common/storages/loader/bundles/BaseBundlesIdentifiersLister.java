package cz.martlin.jmop.common.storages.loader.bundles;

import java.util.Set;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link BaseBundlesLoader} which specifies how to list
 * list of bundle identifiers.
 * 
 * @author martin
 *
 * @param <IT>
 */
public interface BaseBundlesIdentifiersLister<IT> {

	/**
	 * Loads list of bundles identifiers.
	 * 
	 * @return
	 */
	Set<IT> loadBundlesIdentifiers() throws JMOPPersistenceException;
}
