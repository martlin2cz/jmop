package cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers;

import cz.martlin.jmop.common.storages.storage.filesystemer.locators.AbstractCommonLocator;

/**
 * The namer of the playlist files. Computes file name of the playlist data file.
 * 
 * It's component of the {@link AbstractCommonLocator}.
 * 
 * @author martin
 *
 */
public interface BasePlaylistFileNamer {

	/**
	 * Computes the name of the playlist data file of the given playlist.
	 * 
	 * @param bundleName
	 * @param playlistName
	 * @return
	 */
	String playlistFileName(String bundleName, String playlistName);

	/**
	 * Returns true if the given file is playlist data file.
	 * 
	 * @param fileName
	 * @return
	 */
	boolean isPlaylistFile(String fileName);

}