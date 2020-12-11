package cz.martlin.jmop.common.storages.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class LoggingMusicbaseStorage implements BaseMusicbaseStorage {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseMusicbaseStorage delegee;

	public LoggingMusicbaseStorage(BaseMusicbaseStorage delegee) {
		super();
		this.delegee = delegee;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void load(BaseInMemoryMusicbase inmemory) throws JMOPSourceException {
		LOG.info("Loading the persistent storage");

		delegee.load(inmemory);
	}

	@Override
	public void createBundle(Bundle bundle) throws JMOPSourceException {
		LOG.info("Creating bundle {}", bundle.getName());

		delegee.createBundle(bundle);
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPSourceException {
		LOG.info("Renamind bundle {} to {}", oldName, newName);

		delegee.renameBundle(bundle, oldName, newName);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPSourceException {
		LOG.info("Removing bundle {}", bundle.getName());

		delegee.removeBundle(bundle);
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle) throws JMOPSourceException {
		LOG.info("Saving bundle {}", bundle.getName());

		delegee.saveUpdatedBundle(bundle);
	}

	@Override
	public void createPlaylist(Playlist playlist) throws JMOPSourceException {
		LOG.info("Creating playlist {}", playlist.getName());

		delegee.createPlaylist(playlist);
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPSourceException {
		LOG.info("Renaming playlist {} to {}", oldName, newName);

		delegee.renamePlaylist(playlist, oldName, newName);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		LOG.info("Moving playlist {} from {} to {}", playlist.getName(), oldBundle.getName(), newBundle.getName());

		delegee.movePlaylist(playlist, oldBundle, newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPSourceException {
		LOG.info("Removing playlist {}", playlist.getName());

		delegee.removePlaylist(playlist);
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) throws JMOPSourceException {
		LOG.info("Saving playlist {}", playlist.getName());

		delegee.saveUpdatedPlaylist(playlist);
	}

	@Override
	public void createTrack(Track track) throws JMOPSourceException {
		LOG.info("Creating track {}", track.getTitle());

		delegee.createTrack(track);
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPSourceException {
		LOG.info("Renaming track {} to {}", oldTitle, newTitle);

		delegee.renameTrack(track, oldTitle, newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		LOG.info("Moving track {} from {} to {}", track.getTitle(), oldBundle, newBundle);

		delegee.moveTrack(track, oldBundle, newBundle);
	}

	@Override
	public void removeTrack(Track track) throws JMOPSourceException {
		LOG.info("Removing track {}", track.getTitle());

		delegee.removeTrack(track);
	}

	@Override
	public void saveUpdatedTrack(Track track) throws JMOPSourceException {
		LOG.info("Updating track {}", track.getTitle());

		delegee.saveUpdatedTrack(track);
	}

}
