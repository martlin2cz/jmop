package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;

public interface AbstractFileSystemAccessor {

	public List<String> listBundles() throws IOException;

	public Bundle loadBundle(String name) throws IOException;

	public void createBundle(Bundle bundle) throws IOException;

	/////////////////////////////////////////////////////////////////////////////////////

	public List<String> listPlaylists(Bundle bundle) throws IOException;

	public Playlist getPlaylist(Bundle bundle, String name) throws IOException;

	public void savePlaylist(Bundle bundle, Playlist playlist) throws IOException;

	/////////////////////////////////////////////////////////////////////////////////////

	public File getFileOfTrack(Bundle bundle, Track track, TrackFileFormat format) throws IOException;

	

}
