package cz.martlin.jmop.core.sources.locals.electronic.base;

import cz.martlin.jmop.common.data.Track;

/**
 * In the current implementation, do not use the namer. The directory name ==
 * bundle name, the file name is either playlist name / track title (just with
 * the file extension, of course). That's it.
 * 
 * This feature is nice, but overcomplication.
 * 
 * TODO: Consider some cleanup on the user input - exclude all the fancy
 * characters from the bundle names, playlist names or track titles.
 * 
 * @author martin
 *
 */
@Deprecated
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
