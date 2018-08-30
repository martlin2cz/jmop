package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public interface BaseFilesNamer {
	public File fileOfTrack(File root, Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format);

	/////////////////////////////////////////////////////////////////////////////////////

	public File tmpDirectory();

	public boolean isBundleDirectory(File directory);

	public String dirToBundleName(File directory);

	public File directoryOfBundle(File root, SourceKind source, String name);

	/////////////////////////////////////////////////////////////////////////////////////

	public boolean isPlaylistFile(File file);

	public String fileToPlaylistName(File file);

	public File fileOfPlaylist(File root, SourceKind source, String bundleName, String playlistName);

	public String nameOfFullPlaylist();

}
