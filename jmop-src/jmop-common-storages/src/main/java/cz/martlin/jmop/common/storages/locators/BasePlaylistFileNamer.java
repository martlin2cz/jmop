package cz.martlin.jmop.common.storages.locators;

public interface BasePlaylistFileNamer {

	String playlistFileName(String bundleName, String playlistName);

	boolean isPlaylistFile(String fileName);

}