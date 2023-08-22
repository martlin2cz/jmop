package cz.martlin.jmop.common.storages.storage;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.storages.storage.filesystemer.BaseMusicbaseFilesystemer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.storage.musicdataloader.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.BaseMusicdataSaver.SaveReason;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

/**
 * An musicbase storage, which somehow stores the musicbase into some files and
 * directories in the filesystem. It's the opposite of some non-file system based storage,
 * like the SQL storage or any other "whole musicbase in the one file" storage.
 * 
 * @author martin
 *
 */
public class FileSystemedStorage implements BaseMusicbaseStorage {

	private final BaseMusicbaseFilesystemer filesystemer;
	private final BaseMusicdataSaver saver;
	private final BaseMusicdataLoader loader;

	public FileSystemedStorage(BaseMusicbaseFilesystemer filesystemer, BaseBundlesDirLocator xxx_locator,
			BaseMusicdataSaver saver, BaseMusicdataLoader loader) {
		super();
		this.filesystemer = filesystemer;
		this.saver = saver;
		this.loader = loader;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void load(BaseInMemoryMusicbase inmemory) {
		try {
			loader.load(inmemory);
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not load musicbase", e);
		}
	}

	@Override
	public void terminate(BaseInMemoryMusicbase inmemory) {
		// okay, we don't need anything
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void createBundle(Bundle bundle) {
		try {
			filesystemer.createBundle(bundle);
			saver.saveBundleData(bundle, SaveReason.CREATED);

		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not create bundle", e);
		}
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName) {
		try {
			filesystemer.renameBundle(oldName, newName);
			saver.saveBundleData(bundle, SaveReason.RENAMED);

		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not rename bundle", e);
		}
	}

	@Override
	public void removeBundle(Bundle bundle) {
		try {
			filesystemer.removeBundle(bundle);
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not remove bundle", e);
		}
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle) {
		try {
			saver.saveBundleData(bundle, SaveReason.UPDATED);

		} catch (JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not save the bundle", e);
		}
	}

	@Override
	public void createPlaylist(Playlist playlist) {
		try {
			saver.savePlaylistData(playlist, SaveReason.CREATED);

		} catch (JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not create the playlist", e);
		}
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) {
		try {
			filesystemer.renamePlaylist(playlist, oldName, newName);
			saver.savePlaylistData(playlist, SaveReason.RENAMED);

		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not rename playlist", e);
		}
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) {
		try {
			filesystemer.movePlaylist(playlist, oldBundle, newBundle);
			saver.savePlaylistData(playlist, SaveReason.MOVED);

		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not create bundle", e);
		}
	}

	@Override
	public void removePlaylist(Playlist playlist) {
		try {
			filesystemer.removePlaylist(playlist);

		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not remove playlist", e);
		}
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) {
		try {
			saver.savePlaylistData(playlist, SaveReason.UPDATED);

		} catch (JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not save the playlist", e);
		}
	}

	@Override
	public void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile)
			throws JMOPRuntimeException {
		try {
			filesystemer.createTrack(track, trackCreationWay, trackSourceFile);
			saver.saveTrackData(track, SaveReason.CREATED);
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not create track", e);
		}
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) {
		try {
			filesystemer.renameTrack(track, oldTitle, newTitle);
			saver.saveTrackData(track, SaveReason.RENAMED);

		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not rename track", e);
		}
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) {
		try {
			filesystemer.moveTrack(track, oldBundle, newBundle);
			saver.saveTrackData(track, SaveReason.MOVED);

		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not move track", e);
		}
	}

	@Override
	public void removeTrack(Track track) {
		try {
			filesystemer.removeTrack(track);

		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not remove track", e);
		}
	}

	@Override
	public void saveUpdatedTrack(Track track) {
		try {
			saver.saveTrackData(track, SaveReason.UPDATED);

		} catch (JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not save the track", e);
		}
	}

	@Override
	public void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile)
			throws JMOPRuntimeException {
		try {
			filesystemer.specifyTrackFile(track, trackFileHow, trackSourceFile);
			saver.saveTrackData(track, SaveReason.UPDATED);
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not specify track file", e);
		}
	}
}
