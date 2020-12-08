package cz.martlin.jmop.common.musicbase.persistent;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicbaseStorage {

	void load(BaseInMemoryMusicbase inmemory) throws JMOPSourceException;

	void createBundle(Bundle bundle) throws JMOPSourceException;

	void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPSourceException;

	void removeBundle(Bundle bundle) throws JMOPSourceException;

	void saveUpdatedBundle(Bundle bundle) throws JMOPSourceException;

	void createPlaylist(Playlist playlist) throws JMOPSourceException;

	void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPSourceException;

	void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPSourceException;

	void removePlaylist(Playlist playlist) throws JMOPSourceException;
	
	void saveUpdatedPlaylist(Playlist playlist) throws JMOPSourceException;

	void createTrack(Track track) throws JMOPSourceException;

	void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPSourceException;

	void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPSourceException;

	void removeTrack(Track track) throws JMOPSourceException;

	void saveUpdatedTrack(Track track) throws JMOPSourceException;

}
