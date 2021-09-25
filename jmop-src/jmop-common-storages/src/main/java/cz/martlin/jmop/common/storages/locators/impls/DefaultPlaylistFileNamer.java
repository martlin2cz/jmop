package cz.martlin.jmop.common.storages.locators.impls;

import cz.martlin.jmop.common.storages.locators.BasePlaylistFileNamer;

/**
 * The default playlist file namer. The playlist file name consists of the
 * playlist name and the playlist file extension.
 * 
 * @author martin
 *
 */
public class DefaultPlaylistFileNamer implements BasePlaylistFileNamer {

	private final String playlistFileExtension;

	public DefaultPlaylistFileNamer(String playlistFileExtension) {
		super();
		this.playlistFileExtension = playlistFileExtension;
	}

	@Override
	public String playlistFileName(String bundleName, String playlistName) {
		return playlistName + "." + playlistFileExtension;
	}
	
	@Override
	public boolean isPlaylistFile(String fileName) {
		return fileName.endsWith("." + playlistFileExtension);
	}
	
	

}
