package cz.martlin.jmop.common.storages.loader.bundles;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * An failsave implementation of the {@link BaseBundlesByIdentifierLoader}.
 * Catches any execeptions, reports them and returns null.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class FailsaveBundlesByIdentifierLoader<IT> implements BaseBundlesByIdentifierLoader<IT> {

	private final BaseBundlesByIdentifierLoader<IT> delegee;
	private final BaseErrorReporter reporter;

	public FailsaveBundlesByIdentifierLoader(BaseBundlesByIdentifierLoader<IT> delegee, BaseErrorReporter reporter) {
		super();
		this.delegee = delegee;
		this.reporter = reporter;
	}


	@Override
	public Bundle loadBundle(IT indentifier) {
		try {
			return delegee.loadBundle(indentifier);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not load bundle ", e);
			return null;
		}
	}

}
