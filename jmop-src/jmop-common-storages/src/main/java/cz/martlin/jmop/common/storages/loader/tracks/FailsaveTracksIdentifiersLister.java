package cz.martlin.jmop.common.storages.loader.tracks;

import java.util.Collections;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * An failsave implementation of the {@link BaseTracksByIdentifierLister}.
 * Catches any execeptions, reports them and returns empty set or null
 * respectivelly.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class FailsaveTracksIdentifiersLister<IT> implements BaseTracksIdentifiersLister<IT> {

	private final BaseTracksIdentifiersLister<IT> delegee;
	private final BaseErrorReporter reporter;

	public FailsaveTracksIdentifiersLister(BaseTracksIdentifiersLister<IT> delegee, BaseErrorReporter reporter) {
		super();
		this.delegee = delegee;
		this.reporter = reporter;
	}

	@Override
	public Set<IT> loadTracksIdentifiers(Bundle bundle) {
		try {
			return delegee.loadTracksIdentifiers(bundle);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not list tracks identifiers", e);
			return Collections.emptySet();
		}
	}

}
