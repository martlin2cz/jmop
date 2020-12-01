package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Track;

/**
 * Abstract specificies for naming of various file-system items: bundle dir,
 * track file, playlist file.
 * 
 * @author martin
 *
 */
@Deprecated
public interface XXX_BaseFilesNamer {

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns directory name of bundle of given name.
	 * 
	 * @param bundleName
	 * @return
	 */
	public String directoryNameOfBundle(String bundleName);

	/**
	 * Returns name of file of playlist with given name and with given filename
	 * extension.
	 * 
	 * @param playlistName
	 * @param playlistFileExtension
	 * @return
	 */
	public String fileNameOfPlaylist(String playlistName, String playlistFileExtension);

	/**
	 * Returns name of file of track with given format.
	 * 
	 * @param track
	 * @param format
	 * @return
	 */
	@Deprecated
	public String fileNameOfTrack(Track track, TrackFileFormat format);

	/**
	 * TODO doc
	 * 
	 * @param bundle
	 * @param track
	 * @param location
	 * @param format
	 * @return
	 */
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format);
	/////////////////////////////////////////////////////////////////////////////////////

	public String nameOfTempDir();

	public String nameOfCacheDir();

/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns directory of bundle with given bundle dirname and relative to given
	 * root.
	 * 
	 * @param root
	 * @param bundleDirName
	 * @return
	 */
	@Deprecated
	public File bundleDirOfBundleDirName(File root, String bundleDirName);

	/**
	 * Returns directory of bundle with given name and relative to given root.
	 * 
	 * @param root
	 * @param bundleName
	 * @return
	 */
	@Deprecated
	public File bundleDirOfBundleName(File root, String bundleName);

	/**
	 * Returns cache directory for bundle of given name and relative to given root.
	 * 
	 * @param root
	 * @param bundleName
	 * @return
	 */
	@Deprecated
	public File cacheBundleDir(File root, String bundleName);

	/**
	 * Returns temporary directory for bundle of given name.
	 * 
	 * @param bundleName
	 * @return
	 */
	@Deprecated
	public File tempBundleDir(String bundleName);

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Returns file of playlist, relative to given root, located in given bundle
	 * directory name, with given name of playlist and with given playlist file
	 * extension.
	 * 
	 * @param root
	 * @param bundleDirName
	 * @param playlistName
	 * @param playlistFileExtension
	 * @return
	 */
	@Deprecated
	public File playlistFileOfPlaylist(File root, String bundleDirName, String playlistName,
			String playlistFileExtension);

	/**
	 * Returns file of playlist, relative to given root, located in given bundle
	 * directory name, with given playlist file name.
	 * 
	 * @param root
	 * @param bundleDirName
	 * @param playlistFileName
	 * @return
	 */
	@Deprecated
	public File playlistFileOfFile(File root, String bundleDirName, String playlistFileName);

	/////////////////////////////////////////////////////////////////////////////////////

}
