package cz.martlin.jmop.common.musicbase;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;

public interface BaseMusicbaseModifing {
	///////////////////////////////////////////////////////////////////////////
	public Bundle createBundle(String name);

	public void renameBundle(Bundle bundle, String newName);

	public void removeBundle(Bundle bundle);

	public void updateMetadata(Bundle bundle, Metadata newMetadata);

	///////////////////////////////////////////////////////////////////////////
	public Playlist createPlaylist(Bundle bundle, String name);

	public void renamePlaylist(Playlist playlist, String newName);

	public void movePlaylist(Playlist playlist, Bundle newBundle);

	public void removePlaylist(Playlist playlist);

	public void updateMetadata(Playlist playlist, Metadata newMetadata);

	///////////////////////////////////////////////////////////////////////////
	public Track createTrack(Bundle bundle, TrackData data);

	public void renameTrack(Track track, String newTitle);

	public void moveTrack(Track track, Bundle newBundle);

	public void removeTrack(Track track);

	public void updateMetadata(Track track, Metadata newMetadata);

	///////////////////////////////////////////////////////////////////////////
}
