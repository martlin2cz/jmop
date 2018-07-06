package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public interface BaseFilesNamer {
	public File fileOfTrack(File root, Bundle bundle, Track track, TrackFileFormat format);

	/////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isBundleDirectory(File directory);

	public String dirToBundleName(File directory);

	public File directoryOfBundle(File root, SourceKind source, String name);

	/////////////////////////////////////////////////////////////////////////////////////

	public boolean isPlaylistFile(File file);

	public String fileToPlaylistName(File file);

	public File fileOfPlaylist(File root, SourceKind source, String bundleName, String playlistName);

	public File fileOfFullPlaylist(File root, SourceKind source, String bundleName);
}
