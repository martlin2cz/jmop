package cz.martlin.jmop.core.sources.locals.electronic.base;

import cz.martlin.jmop.common.data.Track;

public interface BaseFilesNamer {

	public String directoryNameOfBundle(String bundleName);

	public String fileBasenameOfPlaylist(String playlistName);

	public String fileBasenameOfTrack(String trackTitle);
	
	
	@Deprecated
	public String fileBasenameOfTrack(Track track);

	@Deprecated
	public String nameOfTempDir();
	@Deprecated
	public String nameOfCacheDir();
}
