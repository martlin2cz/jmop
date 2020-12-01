package cz.martlin.jmop.common.musicbase;

import java.util.List;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;

public interface BaseMusicbaseLoading {
	///////////////////////////////////////////////////////////////////////////
	public List<String> bundlesNames();

	public Bundle getBundle(String name);

	///////////////////////////////////////////////////////////////////////////
	public List<String> playlistsNames(Bundle bundle);

	public Playlist getPlaylist(Bundle bundle, String name);

	///////////////////////////////////////////////////////////////////////////
	public List<String> listTracksIDs(Bundle bundle);

	public Track getTrack(Bundle bundle, String id);
	
	///////////////////////////////////////////////////////////////////////////
}
