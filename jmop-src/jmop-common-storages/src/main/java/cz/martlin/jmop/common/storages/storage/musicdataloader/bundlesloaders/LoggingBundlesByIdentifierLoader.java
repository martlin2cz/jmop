package cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * Just an implementation of the {@link BaseBundlesByIdentifierLoader}, but
 * logging.
 * 
 * It's component of {@link ByIdentifierBundlesLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class LoggingBundlesByIdentifierLoader<IT> implements BaseBundlesByIdentifierLoader<IT> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseBundlesByIdentifierLoader<IT> delegee;

	public LoggingBundlesByIdentifierLoader(BaseBundlesByIdentifierLoader<IT> delegee) {
		super();
		this.delegee = delegee;
	}

	@Override
	public Bundle loadBundle(IT indentifier) throws JMOPPersistenceException {
		LOG.info("Loading bundle of identifier: {}", indentifier);
		Bundle bundle = delegee.loadBundle(indentifier);

		LOG.debug("Loaded bundle: {}", bundle);
		return bundle;
	}

}
