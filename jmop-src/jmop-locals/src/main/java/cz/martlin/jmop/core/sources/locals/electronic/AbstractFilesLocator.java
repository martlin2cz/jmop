package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public abstract class AbstractFilesLocator implements BaseFilesLocator {


	@Override
	public File getFileOfPlaylist(String bundleDirName, String playlistName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public File getDirectoryOfBundle(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format) {
		// TODO Auto-generated method stub
		return null;
	}
	


	public abstract File getTempRootDirectory();
	public abstract File getSaveRootDirectory();
	public abstract File getCacheRootDirectory();
	
	
}
