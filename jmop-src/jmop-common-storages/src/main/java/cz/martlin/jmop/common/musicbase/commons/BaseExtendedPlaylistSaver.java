package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;

/**
 * An component responsible for storing the playlists, but also the tracks and
 * bundle infos along them in one file.
 * 
 * @author martin
 *
 */
public interface BaseExtendedPlaylistSaver {

	void savePlaylistWithBundle(Playlist playlist, File playlistFile) throws JMOPSourceException;
	
	void saveOnlyPlaylist(Playlist playlist, File playlistFile) throws JMOPSourceException;

	Bundle loadBundleDataFromAllTracksPlaylist(File allTracksPlaylistFile) throws JMOPSourceException;

	Playlist loadPlaylistDataFromPlaylistFile(Bundle bundle, File playlistFile) throws JMOPSourceException;

}
