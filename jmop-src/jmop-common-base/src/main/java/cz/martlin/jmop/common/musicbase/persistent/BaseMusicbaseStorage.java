package cz.martlin.jmop.common.musicbase.persistent;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

/**
 * The musicbase storage. The component persisting the musicbase.
 * 
 * @author martin
 *
 */
public interface BaseMusicbaseStorage {

	/**
	 * Loads the storage into the given musicbase.
	 * 
	 * @param inmemory
	 * @throws JMOPRuntimeException
	 */
	void load(BaseInMemoryMusicbase inmemory) throws JMOPRuntimeException;

	/**
	 * Terminates the musicbase. Flush any cached changes, disconnect or whatever
	 * 
	 * @param inmemory
	 * @throws JMOPRuntimeException
	 */
	void terminate(BaseInMemoryMusicbase inmemory) throws JMOPRuntimeException;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Stores the newly created bundle.
	 * 
	 * @param bundle
	 * @throws JMOPRuntimeException
	 */
	void createBundle(Bundle bundle) throws JMOPRuntimeException;

	/**
	 * Stores the renamed bundle. Use the provided names, since it's not guaranteed
	 * whether the bundle got already actually renamed or not.
	 * 
	 * @param bundle
	 * @param oldName
	 * @param newName
	 * @throws JMOPRuntimeException
	 */
	void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPRuntimeException;

	/**
	 * Stores the deletion of the bundle (removes from the storage).
	 * 
	 * @param bundle
	 * @throws JMOPRuntimeException
	 */
	void removeBundle(Bundle bundle) throws JMOPRuntimeException;

	/**
	 * Saves any changes performed in the given bundle object.
	 * 
	 * @param bundle
	 * @throws JMOPRuntimeException
	 */
	void saveUpdatedBundle(Bundle bundle) throws JMOPRuntimeException;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Stores given newly created playlist.
	 * 
	 * @param playlist
	 * @throws JMOPRuntimeException
	 */
	void createPlaylist(Playlist playlist) throws JMOPRuntimeException;

	/**
	 * Stores the renamed playlist. Since it's not guaranteed whether the playlist
	 * object rename already happened, use the given old and new name parameters, if
	 * needed.
	 * 
	 * @param playlist
	 * @param oldName
	 * @param newName
	 * @throws JMOPRuntimeException
	 */
	void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPRuntimeException;

	/**
	 * Stores the moved playlist. Since it's not guaranteed whether the playlist
	 * already has the new bundle set, use the given old and new bundle parameters,
	 * if needed.
	 * 
	 * @param playlist
	 * @param oldBundle
	 * @param newBundle
	 * @throws JMOPRuntimeException
	 */
	void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPRuntimeException;

	/**
	 * Stores the deletion (removes from storage) of the given playlist.
	 * 
	 * @param playlist
	 * @throws JMOPRuntimeException
	 */
	void removePlaylist(Playlist playlist) throws JMOPRuntimeException;

	/**
	 * Saves any changes performed in the given playlist.
	 * 
	 * @param playlist
	 * @throws JMOPRuntimeException
	 */
	void saveUpdatedPlaylist(Playlist playlist) throws JMOPRuntimeException;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Stores the newly created track. If the track creation indicates, execute the
	 * track creation as well.
	 * 
	 * 
	 * @param track
	 * @param trackCreationWay
	 * @param trackSourceFile
	 * @throws JMOPRuntimeException
	 */
	void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile)
			throws JMOPRuntimeException;

	/**
	 * Stores the rename of the track. Since it's not guaranteed the track object
	 * already has the new title, use the old and new title parameters, if needed.
	 * 
	 * @param track
	 * @param oldTitle
	 * @param newTitle
	 * @throws JMOPRuntimeException
	 */
	void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPRuntimeException;

	/**
	 * Stores the track move to a different bundle. Since it's not guaranteed the
	 * track object already has the new bundle set, use the provided old and new
	 * bundle parameters, if needed.
	 * 
	 * @param track
	 * @param oldBundle
	 * @param newBundle
	 * @throws JMOPRuntimeException
	 */
	void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPRuntimeException;

	/**
	 * Stores the deletion (removes from the storage) of the given track.
	 * 
	 * @param track
	 * @throws JMOPRuntimeException
	 */
	void removeTrack(Track track) throws JMOPRuntimeException;

	/**
	 * Saves any changes performed in the given track.
	 * 
	 * @param track
	 * @throws JMOPRuntimeException
	 */
	void saveUpdatedTrack(Track track) throws JMOPRuntimeException;

	/**
	 * Executes the construction of the track file and updates the track object (and
	 * stores as well) if needed.
	 * 
	 * @param track
	 * @param trackFileHow
	 * @param trackSourceFile
	 * @throws JMOPRuntimeException
	 */
	void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile)
			throws JMOPRuntimeException;

	///////////////////////////////////////////////////////////////////////////
}
