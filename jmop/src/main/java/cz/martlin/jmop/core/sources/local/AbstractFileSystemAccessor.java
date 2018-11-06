package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

/**
 * Abstract encapsulation of file system operations to be used in the JMOP
 * application. Assumes that each bundle is represented by directory (no
 * nescessary of the same name!) and playlist by some file (not nescessary of
 * the same name), probably located witin particullar bundle dir.
 * 
 * @author martin
 *
 */
public interface AbstractFileSystemAccessor {

	/**
	 * Lists name of directories which are bundle directories.
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<String> listBundlesDirectoriesNames() throws IOException;

	/**
	 * Converts given bundle directory to bundle name (probably by some
	 * "bundle.about" file).
	 * 
	 * @param bundleName
	 * @return
	 * @throws IOException
	 */
	public String bundleDirectoryName(String bundleName) throws IOException;

	/**
	 * Creates directory for bundle of given name.
	 * 
	 * @param bundleName
	 * @throws IOException
	 */
	public void createBundleDirectory(String bundleName) throws IOException;

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Lists names of files of playlist within given bundle dir.
	 * 
	 * @param bundleDirName
	 * @return
	 * @throws IOException
	 */
	public List<String> listPlaylistsFiles(String bundleDirName) throws IOException;

	/**
	 * Returns true if playlist of given name exists within the given bundle
	 * dir.
	 * 
	 * @param bundleDirName
	 * @param playlistName
	 * @return
	 * @throws IOException
	 */
	public boolean existsPlaylist(String bundleDirName, String playlistName) throws IOException;

	/**
	 * Loads metadata of the playist of given playlist name within the given
	 * bundle dir.
	 * 
	 * @param bundleDirName
	 * @param playlistName
	 * @return
	 * @throws IOException
	 */
	public PlaylistFileData getPlaylistMetadataOfName(String bundleDirName, String playlistName) throws IOException;

	/**
	 * Loads metadata of playlist of given file name within the given bundle
	 * dir.
	 * 
	 * @param bundleDirName
	 * @param playlistFileName
	 * @return
	 * @throws IOException
	 */
	public PlaylistFileData getPlaylistMetadataOfFile(String bundleDirName, String playlistFileName) throws IOException;

	/**
	 * Loads all data of playlist of given name within the given bundle dir.
	 * 
	 * @param bundle
	 * @param bundleDirName
	 * @param playlistName
	 * @return
	 * @throws IOException
	 */
	public PlaylistFileData getPlaylistOfName(Bundle bundle, String bundleDirName, String playlistName)
			throws IOException;

	/**
	 * Saves playlist to file within the given bundle dir.
	 * 
	 * @param bundleDirName
	 * @param playlist
	 * @throws IOException
	 */
	public void savePlaylist(String bundleDirName, Playlist playlist) throws IOException;

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns path to the file of track at given location and with given
	 * format.
	 * 
	 * @param bundle
	 * @param track
	 * @param location
	 * @param format
	 * @return
	 * @throws IOException
	 */
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format)
			throws IOException;

	/**
	 * Returns true, if track file at given location and with given format
	 * exists.
	 * 
	 * @param bundle
	 * @param track
	 * @param location
	 * @param format
	 * @return
	 * @throws IOException
	 */
	public boolean existsTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format)
			throws IOException;

}
