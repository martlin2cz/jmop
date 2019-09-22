package cz.martlin.jmop.core.sources.local;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;

public interface BasePlaylistsLocalSource {
	public List<Playlist> loadPlaylists(Bundle bundle);
	
	public void createPlaylist(Playlist playlist);
	public void deletePlaylist(Playlist playlist);
	
	
	public void saveUpdatedPlaylist(Playlist playlist);
	
	//TODO savePlaylist
	
	//TODO move to different bundle ?
	
}
