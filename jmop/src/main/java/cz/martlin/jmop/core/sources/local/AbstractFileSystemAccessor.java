package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public interface AbstractFileSystemAccessor {



	public File getFileOfTrack(Bundle bundle, Track track);
	
	//TODO: all the others, like list, add ...
	//TODO: and playlists, thumbnails, etc.
	
	
}
