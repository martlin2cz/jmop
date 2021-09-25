package cz.martlin.jmop.common.storages.locators;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;

/**
 * An general playlist locator interface. Provides path to the playlist file
 * (either existing or not). Also provides some convience methods.
 * 
 * @author martin
 *
 */
public interface BasePlaylistLocator {

	public File playlistFile(String bundleName, String playlistName);

	public default File playlistFile(Playlist playlist) {
		Bundle bundle = playlist.getBundle();
		return playlistFile(bundle, playlist);
	}

	public default File playlistFile(Bundle bundle, Playlist playlist) {
		String bundleName = bundle.getName();
		String playlistName = playlist.getName();

		return playlistFile(bundleName, playlistName);
	}

	public default File playlistFile(Bundle bundle, String playlistName) {
		String bundleName = bundle.getName();

		return playlistFile(bundleName, playlistName);
	}

	public default File playlistFile(String bundleName, Playlist playlist) {
		String playlistName = playlist.getName();

		return playlistFile(bundleName, playlistName);
	}

	
	public boolean isPlaylistFile(File file);

}
