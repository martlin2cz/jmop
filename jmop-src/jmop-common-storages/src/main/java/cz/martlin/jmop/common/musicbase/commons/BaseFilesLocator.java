package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public interface BaseFilesLocator {

	public File getRootDirectory();
	
	@Deprecated
	public File getDirectoryOfBundle(String bundleName, TrackFileLocation location);
	@Deprecated
	public File getFileOfPlaylist(String bundleDirectoryName, String playlistName);
	@Deprecated
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format);

	
	
	public File bundleDir(String bundleName);
	
	public File playlistFile(String bundleName, String playlistName);
	
	public File trackFile(String bundleName, String trackTitle);

}
