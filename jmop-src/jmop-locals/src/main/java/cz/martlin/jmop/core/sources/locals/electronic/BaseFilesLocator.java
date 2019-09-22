package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public interface BaseFilesLocator {

	public File getRootDirectory();
	
	public File getDirectoryOfBundle(Bundle bundle);
	
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format);

	public File getFileOfPlaylist(Bundle bundle, Playlist playlist);

	
}
