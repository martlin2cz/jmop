package cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * An failsave implementation of the {@link BaseTracksByIdentifierLoader}.
 * Catches any execeptions, reports them and returns null.
 * 
 * It's component of {@link ByIdentifierTracksLoader}.
 *  
 * @author martin
 *
 * @param <IT>
 */
public class FailsaveTracksByIdentifierLoader<IT> implements BaseTracksByIdentifierLoader<IT> {

	private final BaseTracksByIdentifierLoader<IT> delegee;
	private final BaseErrorReporter reporter;

	public FailsaveTracksByIdentifierLoader(BaseTracksByIdentifierLoader<IT> delegee, BaseErrorReporter reporter) {
		super();
		this.delegee = delegee;
		this.reporter = reporter;
	}

	@Override
	public Track loadTrack(Bundle bundle, IT indentifier) {
		try {
			return delegee.loadTrack(bundle, indentifier);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not load track", e);
			return null;
		}
	}

}
