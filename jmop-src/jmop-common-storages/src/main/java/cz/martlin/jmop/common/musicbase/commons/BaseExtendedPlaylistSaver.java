package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;
import java.util.List;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;

public interface BaseExtendedPlaylistSaver {

	void savePlaylist(Playlist playlist);

	String loadBundleNameFromAllTracksPlaylist(File allTracksPlaylistFile);

	Bundle loadBundleDataFromAllTracksPlaylist(File allTracksPlaylistFile);

	List<String> loadTrackTitlesFromAllTracksPlaylist(File allTracksPlaylistFile);

	Track loadTrackDataFromAllTracksPlaylist(File allTracksPlaylistFile, String trackTitle);

	String loadPlaylistNameFromPlaylistFile(File playlistFile);

	Playlist loadPlaylistDataFromPlaylistFile(File playlistFile);

}
