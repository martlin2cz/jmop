package cz.martlin.jmop.common.storages.storage;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

/**
 * The logging storage. Logs and delegates to the {@link #delegee} storage.
 * 
 * @author martin
 *
 */
public class LoggingMusicbaseStorage implements BaseMusicbaseStorage {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseMusicbaseStorage delegee;

	public LoggingMusicbaseStorage(BaseMusicbaseStorage delegee) {
		super();
		this.delegee = delegee;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void load(BaseInMemoryMusicbase inmemory) {
		LOG.info("Loading the persistent storage");

		delegee.load(inmemory);

		LOG.info("Loaded " + inmemory.bundles().size() + " bundles in total");
	}

	@Override
	public void terminate(BaseInMemoryMusicbase inmemory) {
		LOG.info("Terminating the persistent storage");

		delegee.terminate(inmemory);
	}

	@Override
	public void createBundle(Bundle bundle) {
		LOG.info("Creating bundle {}", bundle.getName());

		delegee.createBundle(bundle);
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName) {
		LOG.info("Renamind bundle {} to {}", oldName, newName);

		delegee.renameBundle(bundle, oldName, newName);
	}

	@Override
	public void removeBundle(Bundle bundle) {
		LOG.info("Removing bundle {}", bundle.getName());

		delegee.removeBundle(bundle);
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle) {
		LOG.info("Saving bundle {}", bundle.getName());

		delegee.saveUpdatedBundle(bundle);
	}

	@Override
	public void createPlaylist(Playlist playlist) {
		LOG.info("Creating playlist {}", playlist.getName());

		delegee.createPlaylist(playlist);
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) {
		LOG.info("Renaming playlist {} to {}", oldName, newName);

		delegee.renamePlaylist(playlist, oldName, newName);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) {
		LOG.info("Moving playlist {} from {} to {}", playlist.getName(), oldBundle.getName(), newBundle.getName());

		delegee.movePlaylist(playlist, oldBundle, newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) {
		LOG.info("Removing playlist {}", playlist.getName());

		delegee.removePlaylist(playlist);
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) {
		LOG.info("Saving playlist {}", playlist.getName());

		delegee.saveUpdatedPlaylist(playlist);
	}

	@Override
	public void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile)
			throws JMOPRuntimeException {
		LOG.info("Creating track {}, with {} of {}", track.getTitle(), trackCreationWay, trackSourceFile);

		delegee.createTrack(track, trackCreationWay, trackSourceFile);
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) {
		LOG.info("Renaming track {} to {}", oldTitle, newTitle);

		delegee.renameTrack(track, oldTitle, newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) {
		LOG.info("Moving track {} from {} to {}", track.getTitle(), oldBundle, newBundle);

		delegee.moveTrack(track, oldBundle, newBundle);
	}

	@Override
	public void removeTrack(Track track) {
		LOG.info("Removing track {}", track.getTitle());

		delegee.removeTrack(track);
	}

	@Override
	public void saveUpdatedTrack(Track track) {
		LOG.info("Updating track {}", track.getTitle());

		delegee.saveUpdatedTrack(track);
	}

	@Override
	public void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile)
			throws JMOPRuntimeException {
		LOG.info("Speciffing track {} track file to be {} with {}", track.getTitle(), trackFileHow, trackSourceFile);

		delegee.specifyTrackFile(track, trackFileHow, trackSourceFile);
	}

}
