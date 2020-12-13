package cz.martlin.jmop.common.storages.simples;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

/**
 * The simple implementation of the locator, which maps simply:
 * - the bundles names to bundle dirs names,
 * - the playlist names to playlist files names (with extension), and
 * - the track titles to track files names (with extension)
 * 
 * @author martin
 *
 */
public class SimpleLocator implements BaseFilesLocator {

	private final File root;
	// TODO use some sort of "ProvidesExtension" or "HasFileExtension" utility
	// interface
	private final String playlistFileExtension;
	private final String trackFileExtension;

	public SimpleLocator(File root, String playlistFileExtension, String trackFileExtension) {
		super();
		this.root = root;
		this.playlistFileExtension = playlistFileExtension;
		this.trackFileExtension = trackFileExtension;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public File getRootDirectory() {
		return root;
	}

	@Override
	public File getDirectoryOfBundle(String bundleName, TrackFileLocation location) {
		return null; // XXX
	}

	@Override
	public File getFileOfPlaylist(String bundleDirectoryName, String playlistName) {
		return null; // XXX
	}

	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format) {
		return null; // XXX
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public File bundleDir(String bundleName) {
		return new File(root, bundleName);
	}

	@Override
	public File playlistFile(String bundleName, String playlistName) {
		File bundleDir = bundleDir(bundleName);
		String playlistFileName = appendExtension(playlistName, playlistFileExtension);
		return new File(bundleDir, playlistFileName);
	}

	@Override
	public File trackFile(String bundleName, String trackTitle) {
		File bundleDir = bundleDir(bundleName);
		String trackFileName = appendExtension(trackTitle, trackFileExtension);
		return new File(bundleDir, trackFileName);
	}

	@Override
	public String bundleName(File bundleDir) {
		return bundleDir.getName();
	}

	@Override
	public String playlistName(File playlistFile) {
		String fileName = playlistFile.getName();
		return stripExtension(fileName, playlistFileExtension);
	}

	@Override
	public String trackTitle(File trackFile) {
		String fileName = trackFile.getName();
		return stripExtension(fileName, trackFileExtension);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private static String appendExtension(String baseFileName, String extension) {
		return baseFileName + "." + extension;
	}

	private static String stripExtension(String fileName, String extension) {
		if (fileName.endsWith(extension)) {
			// TODO TESTME
			//TODO FIXME quite tricky!
			return fileName.substring(0, fileName.length() - extension.length() - 1);
		} else {
			throw new IllegalArgumentException("The file has not the desired extension: " + fileName);
		}
	}

}
