package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.utils.FileExtensionSpecifier;
import cz.martlin.jmop.core.misc.JMOPSourceException;

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
	 * @throws JMOPSourceException
	 */
	void savePlaylistWithBundle(Playlist playlist, File file) throws JMOPSourceException;

	/**
	 * Saves the given playlist (but not the bundle) into the given file.
	 * 
	 * @param playlist
	 * @param file
	 * @throws JMOPSourceException
	 */
	void saveOnlyPlaylist(Playlist playlist, File file) throws JMOPSourceException;

	/**
	 * Loads the bundle from the given file.
	 * 
	 * @param file
	 * @return
	 * @throws JMOPSourceException
	 */
	Bundle loadOnlyBundle(File file) throws JMOPSourceException;

	/**
	 * Loads the playlist from the given file.
	 * 
	 * @param bundle
	 * @param file
	 * @return
	 * @throws JMOPSourceException
	 */
	Playlist loadOnlyPlaylist(Bundle bundle, File file) throws JMOPSourceException;

}
