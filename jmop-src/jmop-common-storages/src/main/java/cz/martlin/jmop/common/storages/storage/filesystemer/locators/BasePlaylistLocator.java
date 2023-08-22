package cz.martlin.jmop.common.storages.storage.filesystemer.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;

/**
 * An general playlist locator interface. Provides path to the playlist file
 * (either existing or not). Also provides some convience methods.
 * 
 * It's part of the various components of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public interface BasePlaylistLocator {

	/**
	 * Returns playlist data file of the specified playlist.
	 * 
	 * @param bundleName
	 * @param playlistName
	 * @return
	 */
	public File playlistFile(String bundleName, String playlistName);

	/**
	 * Returns playlist data file of the specified playlist.
	 * 
	 * @param playlist
	 * @return
	 */
	public default File playlistFile(Playlist playlist) {
		Bundle bundle = playlist.getBundle();
		return playlistFile(bundle, playlist);
	}

	/**
	 * Returns playlist data file of the specified playlist.
	 * 
	 * @param bundle
	 * @param playlist
	 * @return
	 */
	public default File playlistFile(Bundle bundle, Playlist playlist) {
		String bundleName = bundle.getName();
		String playlistName = playlist.getName();

		return playlistFile(bundleName, playlistName);
	}

	/**
	 * Returns playlist data file of the specified playlist.
	 * 
	 * @param bundle
	 * @param playlistName
	 * @return
	 */
	public default File playlistFile(Bundle bundle, String playlistName) {
		String bundleName = bundle.getName();

		return playlistFile(bundleName, playlistName);
	}

	/**
	 * Returns playlist data file of the specified playlist.
	 * 
	 * @param bundleName
	 * @param playlist
	 * @return
	 */
	public default File playlistFile(String bundleName, Playlist playlist) {
		String playlistName = playlist.getName();

		return playlistFile(bundleName, playlistName);
	}

	/**
	 * Returns true if the given file is (seems to be) playlist data file.
	 * 
	 * @param file
	 * @return
	 */
	public boolean isPlaylistFile(File file);

}
