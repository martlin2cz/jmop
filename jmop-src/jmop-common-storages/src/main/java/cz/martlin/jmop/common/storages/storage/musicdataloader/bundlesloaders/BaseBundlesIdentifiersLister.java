package cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders;

import java.util.Set;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An sub-component of the {@link ByIdentifierBundlesLoader} which specifies how to list
 * list of bundle identifiers.
 * 
 * It's component of {@link ByIdentifierBundlesLoader}.
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
