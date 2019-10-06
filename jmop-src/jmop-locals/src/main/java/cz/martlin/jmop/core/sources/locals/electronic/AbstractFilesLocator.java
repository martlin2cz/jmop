package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.BasePlaylistFilesLoaderStorer;

public abstract class AbstractFilesLocator implements BaseFilesLocator {

	private final BasePlaylistFilesLoaderStorer pfls;
	private final File root;
	
	public AbstractFilesLocator(BasePlaylistFilesLoaderStorer pfls, File root) {
		super();
		this.pfls = pfls;
		this.root = root;
	}


	@Override
	public File getFileOfPlaylist(String bundleDirName, String playlistName) {
		File bundleDir = new File(root, bundleDirName);
		
		String playlistFileBasename = getBasenameOfPlaylistFile(playlistName);
		String playlistFileName = playlistFileBasename + "." + pfls.extensionOfFile();
		return new File(bundleDir, playlistFileName);
	}
	
	
	protected abstract String getBasenameOfPlaylistFile(String playlistName);


	@Override
	public File getDirectoryOfBundle(String bundleName) {
		String bundleDirName = getBundleDirName(bundleName);
		return new File(root, bundleDirName);
	}

	protected abstract String getBundleDirName(String bundleName);


	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format) {
		//TODO use location
		String bundleName = bundle.getName();
		File bundleDir = getDirectoryOfBundle(bundleName);
		
		String trackFileBasename = getTrackFileBasename(track);
		String trackFileName = trackFileBasename + "." + format.getExtension();
		
		return new File(bundleDir, trackFileName);
	}
	


	protected abstract String getTrackFileBasename(Track track);


	public abstract File getTempRootDirectory();
	public abstract File getSaveRootDirectory();
	public abstract File getCacheRootDirectory();
	
	
}
