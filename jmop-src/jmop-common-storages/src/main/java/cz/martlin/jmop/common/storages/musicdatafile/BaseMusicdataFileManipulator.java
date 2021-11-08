package cz.martlin.jmop.common.storages.musicdatafile;

import java.io.File;
import java.util.Map;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.utils.FileExtensionSpecifier;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An component responsible for storing the playlists and bundles (with all its
 * tracks) in the file of the same format, possibly the same file.
 * 
 * @author martin
 *
 */
public interface BaseMusicdataFileManipulator extends FileExtensionSpecifier {

	/**
	 * Saves the given bundle info (with the given bundle tracks) into the given
	 * file.
	 * 
	 * @param bundle
	 * @param tracks
	 * @param file
	 * @throws JMOPPersistenceException
	 */
	public void saveBundleData(Bundle bundle, Set<Track> tracks, File file)
			throws JMOPPersistenceException;

	/**
	 * Saves the given playlist into the given file.
	 * 
	 * @param playlist
	 * @param file
	 * @throws JMOPPersistenceException
	 */
	public void savePlaylistData(Playlist playlist, File file)
			throws JMOPPersistenceException;

	/**
	 * Loads the bundle (data) from the given file.
	 * 
	 * @param file
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public Bundle loadBundleData(File file) throws JMOPPersistenceException;

	/**
	 * Loads the bundle tracks from the given file.
	 * 
	 * @param file
	 * @param bundle TODO
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public Set<Track> loadBundleTracks(File file, Bundle bundle) throws JMOPPersistenceException;

	/**
	 * Loads the playlist from the given file and by using the specified tracks.
	 * 
	 * @param bundle
	 * @param tracks
	 * @param file
	 * @return
	 * @throws JMOPPersistenceException
	 */
	public Playlist loadPlaylistData(Bundle bundle, Map<String, Track> tracks, File file)
			throws JMOPPersistenceException;

}
