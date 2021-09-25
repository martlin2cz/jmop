package cz.martlin.jmop.common.storages.loader.bundles;

import java.util.HashSet;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An bundle loader, which firstly lists bundles indentifiers and just then
 * loads the particular bundles. The loads delegates to
 * {@link BaseBundlesByIdentifierLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class ByIdentifierBundlesLoader<IT> implements BaseBundlesLoader {

	private final BaseBundlesIdentifiersLister<IT> lister;
	private final BaseBundlesByIdentifierLoader<IT> loader;
	

	public ByIdentifierBundlesLoader(BaseBundlesIdentifiersLister<IT> lister, BaseBundlesByIdentifierLoader<IT> loader) {
		super();
		this.lister = lister;
		this.loader = loader;
	}

	@Override
	public Set<Bundle> loadBundles() throws JMOPPersistenceException {
		Set<IT> indentifiers = lister.loadBundlesIdentifiers();

		return loadBundles(indentifiers);
	}

	private Set<Bundle> loadBundles(Set<IT> indentifiers) throws JMOPPersistenceException {
		Set<Bundle> bundles = new HashSet<>(indentifiers.size());

		for (IT indentifier : indentifiers) {
			Bundle bundle = loader.loadBundle(indentifier);

			if (bundle != null) {
				bundles.add(bundle);
			}
		}

		return bundles;
	}

}
