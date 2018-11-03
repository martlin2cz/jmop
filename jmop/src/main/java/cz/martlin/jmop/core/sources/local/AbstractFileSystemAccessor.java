package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TrackFileFormat;

public interface AbstractFileSystemAccessor {

	public List<String> listBundlesDirectoriesNames() throws IOException;

	public String bundleDirectoryName(String bundleName) throws IOException;

	public void createBundleDirectory(String bundleName) throws IOException;

	/////////////////////////////////////////////////////////////////////////////////////

	public List<String> listPlaylistsFiles(String bundleDirName) throws IOException;

	public boolean existsPlaylist(String bundleDirName, String playlistName) throws IOException;

	public PlaylistFileData getPlaylistMetadataOfName(String bundleDirName, String playlistName) throws IOException;

	public PlaylistFileData getPlaylistMetadataOfFile(String bundleDirName, String playlistFileName) throws IOException;

	public PlaylistFileData getPlaylistOfName(Bundle bundle, String bundleDirName, String playlistName) throws IOException;

	public void savePlaylist(String bundleDirName, Playlist playlist) throws IOException;

	/////////////////////////////////////////////////////////////////////////////////////

	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format)
			throws IOException;

	public boolean existsTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format)
			throws IOException;

	


}
