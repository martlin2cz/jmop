package cz.martlin.jmop.common.musicbase.persistent;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseMusicbaseStorage extends TracksSource {

	void load(BaseInMemoryMusicbase inmemory) throws JMOPMusicbaseException;

	void createBundle(Bundle bundle) throws JMOPMusicbaseException;

	void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPMusicbaseException;

	void removeBundle(Bundle bundle) throws JMOPMusicbaseException;

	void saveUpdatedBundle(Bundle bundle) throws JMOPMusicbaseException;

	void createPlaylist(Playlist playlist) throws JMOPMusicbaseException;

	void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPMusicbaseException;

	void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPMusicbaseException;

	void removePlaylist(Playlist playlist) throws JMOPMusicbaseException;
	
	void saveUpdatedPlaylist(Playlist playlist) throws JMOPMusicbaseException;

	void createTrack(Track track) throws JMOPMusicbaseException;

	void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPMusicbaseException;

	void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPMusicbaseException;

	void removeTrack(Track track) throws JMOPMusicbaseException;

	void saveUpdatedTrack(Track track) throws JMOPMusicbaseException;

}
