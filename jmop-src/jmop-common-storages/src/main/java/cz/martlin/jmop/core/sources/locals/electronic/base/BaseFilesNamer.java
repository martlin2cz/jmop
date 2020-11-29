package cz.martlin.jmop.core.sources.locals.electronic.base;

import cz.martlin.jmop.core.data.Track;

public interface BaseFilesNamer {

	public String directoryNameOfBundle(String bundleName);

	public String fileBasenameOfPlaylist(String playlistName);

	public String fileBasenameOfTrack(Track track);

	public String nameOfTempDir();

	public String nameOfCacheDir();
}
