package cz.martlin.jmop.common.storages.utils;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;

/**
 * An extension of the {@link BaseFilesLocator}, which adds some extra methods
 * to utilise the usage of that module.
 * 
 * @author martin
 *
 */
public class FilesLocatorExtension implements TracksSource {
	private final BaseFilesLocator locator;

	public FilesLocatorExtension(BaseFilesLocator locator) {
		super();
		this.locator = locator;
	}

	public File getRootDirectory() {
		return locator.getRootDirectory();
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns dir of the given bundle.
	 * 
	 * @param bundle
	 * @return
	 */
	public File bundleDir(Bundle bundle) {
		String bundleName = bundle.getName();
		return bundleDir(bundleName);
	}

	/**
	 * Returns dir of the given bundle name.
	 * 
	 * @param bundleName
	 * @return
	 */
	public File bundleDir(String bundleName) {
		return locator.bundleDir(bundleName);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns file of the given playlist.
	 * 
	 * @param playlist
	 * @return
	 */
	public File playlistFile(Playlist playlist) {
		Bundle bundle = playlist.getBundle();
		String bundleName = bundle.getName();
		String playlistName = playlist.getName();
		return playlistFile(bundleName, playlistName);
	}

	/**
	 * Returns file of the playlist of given name in given bundle.
	 * 
	 * @param bundle
	 * @param playlistName
	 * @return
	 */
	public File playlistFile(Bundle bundle, String playlistName) {
		String bundleName = bundle.getName();
		return playlistFile(bundleName, playlistName);
	}

	/**
	 * Returns file of the given playlist but in given bundle.
	 * 
	 * @param bundle
	 * @param playlist
	 * @return
	 */
	public File playlistFile(Bundle bundle, Playlist playlist) {
		String bundleName = bundle.getName();
		String playlistName = playlist.getName();
		return playlistFile(bundleName, playlistName);
	}

	/**
	 * Returns file of the playlist of given name in bundle of given name.
	 * 
	 * @param bundleName
	 * @param playlistName
	 * @return
	 */
	public File playlistFile(String bundleName, String playlistName) {
		return locator.playlistFile(bundleName, playlistName);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns file of the given track.
	 * 
	 * @param track
	 * @return
	 */
	@Override
	public File trackFile(Track track) {
		Bundle bundle = track.getBundle();
		String bundleName = bundle.getName();
		String trackTitle = track.getTitle();
		return trackFile(bundleName, trackTitle);
	}

	/**
	 * Returns file of the track in given bundle and with title.
	 * 
	 * @param bundle
	 * @param trackTitle
	 * @return
	 */
	public File trackFile(Bundle bundle, String trackTitle) {
		String bundleName = bundle.getName();
		return trackFile(bundleName, trackTitle);
	}

	/**
	 * Returns file of the given track but in given bundle.
	 * 
	 * @param bundle
	 * @param track
	 * @return
	 */
	public File trackFile(Bundle bundle, Track track) {
		String bundleName = bundle.getName();
		String trackTitle = track.getTitle();
		return trackFile(bundleName, trackTitle);
	}

	/**
	 * Returns file of the track of given title in bundle of given name.
	 * 
	 * @param bundleName
	 * @param trackTitle
	 * @return
	 */
	public File trackFile(String bundleName, String trackTitle) {
		return locator.trackFile(bundleName, trackTitle);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns name of bundle of given bundle dir.
	 * 
	 * @param bundleDir
	 * @return
	 */
	public String bundleName(File bundleDir) {
		return locator.bundleName(bundleDir);
	}

	/**
	 * Returns name of playlist of given playlist file.
	 * 
	 * @param playlistFile
	 * @return
	 */
	public String playlistName(File playlistFile) {
		return locator.playlistName(playlistFile);
	}

	/**
	 * Returns name of track of given track file.
	 * 
	 * @param trackFile
	 * @return
	 */
	public String trackTitle(File trackFile) {
		return locator.trackTitle(trackFile);
	}

}
