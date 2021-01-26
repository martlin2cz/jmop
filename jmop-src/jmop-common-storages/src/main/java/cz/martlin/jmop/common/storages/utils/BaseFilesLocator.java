package cz.martlin.jmop.common.storages.utils;

import java.io.File;

/**
 * The locator. Maps bundle, playlist and track name/title to particualar files
 * on disk. And back. Please, without any further digging into them: opening,
 * loading, parsing, extracting and so.
 * 
 * Note: Use the {@link FilesLocatorExtension} where possible.
 * 
 * @author martin
 *
 */
public interface BaseFilesLocator {

	public File getRootDirectory();

	public File bundleDir(String bundleName);

	public File playlistFile(String bundleName, String playlistName);

	public File trackFile(String bundleName, String trackTitle);

	public String bundleName(File bundleDir);

	public String playlistName(File playlistFile);

	public String trackTitle(File trackFile);

}
