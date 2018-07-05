package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.util.List;

import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public interface BaseFilesNamer {
	public File fileOfTrack(Bundle bundle, Track track, TrackFileFormat format);


	/////////////////////////////////////////////////////////////////////////////////////
	
	public File directoryOfBundle(Bundle bundle);

	public String dirToBundleName(File directory);

	public List<File> listBundles();


	/////////////////////////////////////////////////////////////////////////////////////

	public String fileToPlaylistName(File file);

	public File fileOfPlaylist(Bundle bundle, String name);

	public List<File> listPlaylists(Bundle bundle);

	// TODO
}
