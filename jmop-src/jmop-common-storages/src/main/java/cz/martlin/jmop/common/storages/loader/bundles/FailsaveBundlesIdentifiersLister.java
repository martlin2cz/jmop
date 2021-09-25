package cz.martlin.jmop.common.storages.loader.bundles;

import java.util.Collections;
import java.util.Set;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * An failsave implementation of the {@link BaseBundlesIdentifiersLister}.
 * Catches the exceptions, reports them and returns empty set.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class FailsaveBundlesIdentifiersLister<IT> implements BaseBundlesIdentifiersLister<IT> {
	private final BaseBundlesIdentifiersLister<IT> delegee;
	private final BaseErrorReporter reporter;

	public FailsaveBundlesIdentifiersLister(BaseBundlesIdentifiersLister<IT> delegee, BaseErrorReporter reporter) {
		super();
		this.delegee = delegee;
		this.reporter = reporter;
	}

	@Override
	public Set<IT> loadBundlesIdentifiers() {
		try {
			return delegee.loadBundlesIdentifiers();
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not list bundles identifiers", e);
			return Collections.emptySet();
		}
	}

}
