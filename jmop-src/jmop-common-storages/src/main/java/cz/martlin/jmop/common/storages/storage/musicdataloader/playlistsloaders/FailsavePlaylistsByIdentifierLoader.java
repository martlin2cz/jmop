package cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders;

import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

/**
 * An failsave implementation of the {@link BasePlaylistsByIdentifierLoader}.
 * Catches any execeptions, reports them and returns empty set or null
 * respectivelly.
 * 
 * It's component of {@link ByIdentifierPlaylistsLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class FailsavePlaylistsByIdentifierLoader<IT> implements BasePlaylistsByIdentifierLoader<IT> {

	private final BasePlaylistsByIdentifierLoader<IT> delegee;
	private final BaseErrorReporter reporter;

	public FailsavePlaylistsByIdentifierLoader(BasePlaylistsByIdentifierLoader<IT> delegee,
			BaseErrorReporter reporter) {
		super();
		this.delegee = delegee;
		this.reporter = reporter;
	}

	@Override
	public Playlist loadPlaylist(Bundle bundle, IT indentifier, Map<String, Track> tracksMap) {
		try {
			return delegee.loadPlaylist(bundle, indentifier, tracksMap);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not load playlist", e);
			return null;
		}
	}

}
