package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.data.Track;

public interface BaseFilesNamer {

	/////////////////////////////////////////////////////////////////////////////////////

	public String directoryNameOfBundle(String bundleName);

	public String fileNameOfPlaylist(String playlistName, String playlistFileExtension);

	public String fileNameOfTrack(Track track, TrackFileFormat format);
	/////////////////////////////////////////////////////////////////////////////////////

	public File bundleDirOfBundleDirName(File root, String bundleDirName);

	public File bundleDirOfBundleName(File root, String bundleName);

	public File cacheBundleDir(File root, String bundleName);

	public File tempBundleDir(String bundleName);

	/////////////////////////////////////////////////////////////////////////////////////
	public File playlistFileOfPlaylist(File root, String bundleDirName, String playlistName,
			String playlistFileExtension);

	public File playlistFileOfFile(File root, String bundleDirName, String playlistFileName);

	/////////////////////////////////////////////////////////////////////////////////////

}
