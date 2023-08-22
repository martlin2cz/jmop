package cz.martlin.jmop.common.storages.storage.filesystemer;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An components of the persistent musicbase, which is responsible for the file
 * system changes, I.e. creating, removing, renaming and moving files.
 * 
 * It's component of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public interface BaseMusicbaseFilesystemer {

	/**
	 * Stores creation of the specified bundle.
	 * 
	 * @param bundle
	 * @throws JMOPPersistenceException
	 */
	void createBundle(Bundle bundle) throws JMOPPersistenceException;

	/**
	 * Stores rename of the given bundle.
	 * 
	 * @param oldName
	 * @param newName
	 * @throws JMOPPersistenceException
	 */
	void renameBundle(String oldName, String newName) throws JMOPPersistenceException;

	/**
	 * Stores removal (removes from the storage) the given bundle.
	 * 
	 * @param bundle
	 * @throws JMOPPersistenceException
	 */
	void removeBundle(Bundle bundle) throws JMOPPersistenceException;

	/**
	 * Stores the rename of the given playlist.
	 * 
	 * @param playlist
	 * @param oldName
	 * @param newName
	 * @throws JMOPPersistenceException
	 */
	void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPPersistenceException;

	/**
	 * Stores the move of the given playlist.
	 * 
	 * @param playlist
	 * @param oldBundle
	 * @param newBundle
	 * @throws JMOPPersistenceException
	 */
	void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPPersistenceException;

	/**
	 * Stores removal (removes from the storage) of given playlist.
	 * 
	 * @param playlist
	 * @throws JMOPPersistenceException
	 */
	void removePlaylist(Playlist playlist) throws JMOPPersistenceException;

	/**
	 * Stores the newly created track.
	 * 
	 * @param track
	 * @param trackCreationWay
	 * @param trackSourceFile
	 * @throws JMOPPersistenceException
	 */
	void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile)
			throws JMOPPersistenceException;

	/**
	 * Stores the rename of the track.
	 * 
	 * @param track
	 * @param oldTitle
	 * @param newTitle
	 * @throws JMOPPersistenceException
	 */
	void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPPersistenceException;

	/**
	 * Stores the move of the track to a new bundle.
	 * 
	 * @param track
	 * @param oldBundle
	 * @param newBundle
	 * @throws JMOPPersistenceException
	 */
	void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPPersistenceException;

	/**
	 * Stores track removal (removes from the storage) of the given track.
	 * 
	 * @param track
	 * @throws JMOPPersistenceException
	 */
	void removeTrack(Track track) throws JMOPPersistenceException;

	/**
	 * Executes the track creation and if needed, updates the track (both the object
	 * itself and the stores that change as well).
	 * 
	 * @param track
	 * @param trackFileHow
	 * @param trackSourceFile
	 * @throws JMOPPersistenceException
	 */
	void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile)
			throws JMOPPersistenceException;

}