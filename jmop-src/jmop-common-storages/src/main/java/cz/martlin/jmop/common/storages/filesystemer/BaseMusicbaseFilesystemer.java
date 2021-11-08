package cz.martlin.jmop.common.storages.filesystemer;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An components of the persistent musicbase, which is responsible for the file
 * system changes, I.e. creating, removing, renaming and moving files.
 * 
 * @author martin
 *
 */
public interface BaseMusicbaseFilesystemer {

	void createBundle(Bundle bundle) throws JMOPPersistenceException;

	void renameBundle(String oldName, String newName) throws JMOPPersistenceException;

	void removeBundle(Bundle bundle) throws JMOPPersistenceException;

	void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPPersistenceException;

	void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPPersistenceException;

	void removePlaylist(Playlist playlist) throws JMOPPersistenceException;

	void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile) throws JMOPPersistenceException;

	void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPPersistenceException;

	void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPPersistenceException;

	void removeTrack(Track track) throws JMOPPersistenceException;


}