package cz.martlin.jmop.common.musicbase.persistent;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

public interface BaseMusicbaseStorage {

	void load(BaseInMemoryMusicbase inmemory) throws JMOPRuntimeException;

	void terminate(BaseInMemoryMusicbase inmemory) throws JMOPRuntimeException;

	void createBundle(Bundle bundle) throws JMOPRuntimeException;

	void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPRuntimeException;

	void removeBundle(Bundle bundle) throws JMOPRuntimeException;

	void saveUpdatedBundle(Bundle bundle) throws JMOPRuntimeException;

	void createPlaylist(Playlist playlist) throws JMOPRuntimeException;

	void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPRuntimeException;

	void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPRuntimeException;

	void removePlaylist(Playlist playlist) throws JMOPRuntimeException;

	void saveUpdatedPlaylist(Playlist playlist) throws JMOPRuntimeException;

	void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile) throws JMOPRuntimeException;
	
	void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPRuntimeException;

	void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPRuntimeException;

	void removeTrack(Track track) throws JMOPRuntimeException;

	void saveUpdatedTrack(Track track) throws JMOPRuntimeException;

	void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile) throws JMOPRuntimeException;
	
}
