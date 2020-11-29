package cz.martlin.jmop.core.sources.local;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BasePlaylistsLocalSource {
	public List<Playlist> loadPlaylists(Bundle bundle) throws JMOPSourceException;

	public void createPlaylist(Playlist playlist) throws JMOPSourceException;

	public void deletePlaylist(Playlist playlist) throws JMOPSourceException;

	public void saveUpdatedPlaylist(Playlist playlist) throws JMOPSourceException;

	// TODO move to different bundle ?

}
