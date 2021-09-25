package cz.martlin.jmop.common.storages.loader.bundles;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * Just an implementation of the {@link BaseBundlesIdentifiersLister}, but
 * logging.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class LoggingBundlesIdentifiersLister<IT> implements BaseBundlesIdentifiersLister<IT> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseBundlesIdentifiersLister<IT> delegee;

	public LoggingBundlesIdentifiersLister(BaseBundlesIdentifiersLister<IT> delegee) {
		super();
		this.delegee = delegee;
	}

	@Override
	public Set<IT> loadBundlesIdentifiers() throws JMOPPersistenceException {
		LOG.info("Loading bundles identifiers");
		Set<IT> ids = delegee.loadBundlesIdentifiers();

		LOG.debug("Loaded bundles identifiers: {}", ids);
		return ids;
	}

}
