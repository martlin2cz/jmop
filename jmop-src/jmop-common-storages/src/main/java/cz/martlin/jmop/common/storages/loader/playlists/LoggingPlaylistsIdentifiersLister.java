package cz.martlin.jmop.common.storages.loader.playlists;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An logging implementation of the {@link BasePlaylistsByIdentifierLister}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class LoggingPlaylistsIdentifiersLister<IT> implements BasePlaylistsIdentifiersLister<IT> {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BasePlaylistsIdentifiersLister<IT> delegee;

	public LoggingPlaylistsIdentifiersLister(BasePlaylistsIdentifiersLister<IT> delegee) {
		super();
		this.delegee = delegee;
	}

	@Override
	public Set<IT> loadPlaylistsIdentifiers(Bundle bundle) throws JMOPPersistenceException {
		LOG.info("Loading playlist identifiers of bundle {}", bundle);
		Set<IT> ids = delegee.loadPlaylistsIdentifiers(bundle);

		LOG.debug("Loaded playlist identifiers: {}", ids);
		return ids;
	}

}
