package cz.martlin.jmop.common.storages.loader.playlists;

import java.util.Collections;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * An failsave implementation of the {@link BasePlaylistsByIdentifierLister}.
 * Catches any execeptions, reports them and returns empty set or null
 * respectivelly.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class FailsavePlaylistsIdentifiersLister<IT> implements BasePlaylistsIdentifiersLister<IT> {

	private final BasePlaylistsIdentifiersLister<IT> delegee;
	private final BaseErrorReporter reporter;

	public FailsavePlaylistsIdentifiersLister(BasePlaylistsIdentifiersLister<IT> delegee,
			BaseErrorReporter reporter) {
		super();
		this.delegee = delegee;
		this.reporter = reporter;
	}

	@Override
	public Set<IT> loadPlaylistsIdentifiers(Bundle bundle) {
		try {
			return delegee.loadPlaylistsIdentifiers(bundle);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not list playlists identifiers", e);
			return Collections.emptySet();
		}
	}

}
