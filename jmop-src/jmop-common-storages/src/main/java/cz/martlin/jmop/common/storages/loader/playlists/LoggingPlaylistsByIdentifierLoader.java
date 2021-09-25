package cz.martlin.jmop.common.storages.loader.playlists;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An logging implementation of the {@link BasePlaylistsByIdentifierLoader}.
 * 
 * @author martin
 *
 * @param <IT>
 */
public class LoggingPlaylistsByIdentifierLoader<IT> implements BasePlaylistsByIdentifierLoader<IT> {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BasePlaylistsByIdentifierLoader<IT> delegee;

	public LoggingPlaylistsByIdentifierLoader(BasePlaylistsByIdentifierLoader<IT> delegee) {
		super();
		this.delegee = delegee;
	}

	@Override
	public Playlist loadPlaylist(Bundle bundle, IT indentifier, Map<String, Track> tracksMap)
			throws JMOPPersistenceException {

		LOG.info("Loading playlist of identifier: {}", indentifier);
		Playlist playlist = delegee.loadPlaylist(bundle, indentifier, tracksMap);

		LOG.debug("Loaded playlist: {}", playlist);
		return playlist;
	}

}
