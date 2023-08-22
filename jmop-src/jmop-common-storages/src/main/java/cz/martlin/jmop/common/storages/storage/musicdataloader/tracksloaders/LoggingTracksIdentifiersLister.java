package cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An logging implementation of the {@link BaseTracksByIdentifierLister}.
 * 
 * It's component of {@link ByIdentifierTracksLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class LoggingTracksIdentifiersLister<IT> implements BaseTracksIdentifiersLister<IT> {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseTracksIdentifiersLister<IT> delegee;

	public LoggingTracksIdentifiersLister(BaseTracksIdentifiersLister<IT> delegee) {
		super();
		this.delegee = delegee;
	}

	@Override
	public Set<IT> loadTracksIdentifiers(Bundle bundle) throws JMOPPersistenceException {
		LOG.info("Loading tracks identifiers of bundle {}", bundle);
		Set<IT> ids = delegee.loadTracksIdentifiers(bundle);

		LOG.debug("Loaded track identifiers: {}", ids);
		return ids;
	}

}
