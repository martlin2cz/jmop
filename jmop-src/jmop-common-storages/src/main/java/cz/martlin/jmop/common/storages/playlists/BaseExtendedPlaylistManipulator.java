package cz.martlin.jmop.common.storages.playlists;

import java.io.File;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.utils.FileExtensionSpecifier;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

/**
 * An component responsible for storing the playlists, but also the tracks and
 * bundle infos along them in one file.
 * 
 * The file contains just the generic bundle info, but also the playlist which
 * works as a database of all the tracks in the bundle.
 * 
 * @author martin
 *
 */
public interface BaseExtendedPlaylistManipulator extends FileExtensionSpecifier {

	/**
	 * Saves the given playlist and bundle info into the given file.
	 * 
	 * @param playlist
	 * @param file
	 * @throws JMOPPersistenceException 
	 * @
	 */
	void savePlaylistWithBundle(Playlist playlist, File file) throws JMOPPersistenceException ;

	/**
	 * Saves the given playlist (but not the bundle) into the given file.
	 * 
	 * @param playlist
	 * @param file
	 * @throws JMOPPersistenceException 
	 * @
	 */
	void saveOnlyPlaylist(Playlist playlist, File file) throws JMOPPersistenceException ;

	/**
	 * Loads the bundle from the given file.
	 * 
	 * @param file
	 * @return
	 * @throws JMOPPersistenceException 
	 * @
	 */
	Bundle loadOnlyBundle(File file) throws JMOPPersistenceException ;

	/**
	 * Loads the playlist from the given file and by using the specified tracks. If
	 * the tracks is unspecfied, loads the whole tracks from the file.
	 * 
	 * @param bundle
	 * @param tracks
	 * @param file
	 * @return
	 * @throws JMOPPersistenceException 
	 * @
	 */
	Playlist loadOnlyPlaylist(Bundle bundle, Map<String, Track> tracks, File file) throws JMOPPersistenceException ;

}
