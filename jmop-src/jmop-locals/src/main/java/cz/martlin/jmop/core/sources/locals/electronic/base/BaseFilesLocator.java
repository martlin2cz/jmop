package cz.martlin.jmop.core.sources.locals.electronic.base;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public interface BaseFilesLocator {

	public File getRootDirectory();
	
	public File getDirectoryOfBundle(String bundleName, TrackFileLocation location);
	
	public File getFileOfPlaylist(String bundleDirectoryName, String playlistName);
	
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format);



	
}
