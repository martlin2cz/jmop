package cz.martlin.jmop.common.musicbase.persistent;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;

public interface BaseMusicbaseStorage {

	void load(BaseInMemoryMusicbase inmemory);

	void createBundle(Bundle bundle);

	void renameBundle(Bundle bundle, String oldName, String newName);

	void removeBundle(Bundle bundle);

	void saveUpdatedBundle(Bundle bundle);

	void createPlaylist(Playlist playlist);

	void renamePlaylist(Playlist playlist, String oldName, String newName);

	void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle);

	void removePlaylist(Playlist playlist);

	void saveUpdatedPlaylist(Playlist playlist);

	void createTrack(Track track);

	void renameTrack(Track track, String oldTitle, String newTitle);

	void moveTrack(Track track, Bundle oldBundle, Bundle newBundle);

	void removeTrack(Track track);

	void saveUpdatedTrack(Track track);

}
