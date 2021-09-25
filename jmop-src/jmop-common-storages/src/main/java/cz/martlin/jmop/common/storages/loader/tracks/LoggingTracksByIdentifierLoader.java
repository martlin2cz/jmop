package cz.martlin.jmop.common.storages.loader.tracks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An logging implementation of the {@link BaseTracksByIdentifierLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class LoggingTracksByIdentifierLoader<IT> implements BaseTracksByIdentifierLoader<IT> {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseTracksByIdentifierLoader<IT> delegee;

	public LoggingTracksByIdentifierLoader(BaseTracksByIdentifierLoader<IT> delegee) {
		super();
		this.delegee = delegee;
	}

	@Override
	public Track loadTrack(Bundle bundle, IT indentifier) throws JMOPPersistenceException {

		LOG.info("Loading track of identifier: {}", indentifier);
		Track track = delegee.loadTrack(bundle, indentifier);

		LOG.debug("Loaded track: {}", track);
		return track;
	}

}
