package cz.martlin.jmop.common.storages.simplefs;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.musicbase.commons.BaseFilesLocator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public class SimpleLocator implements BaseFilesLocator {

	private final File root;

	public SimpleLocator(File root) {
		super();
		this.root = root;
	}

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

	@Override
	public File bundleDir(String bundleName) {
		return new File(root, bundleName);
	}

	@Override
	public File playlistFile(String bundleName, String playlistName) {
		File bundleDir = bundleDir(bundleName);
		return new File(bundleDir, playlistName);
	}

	@Override
	public File trackFile(String bundleName, String trackTitle) {
		File bundleDir = bundleDir(bundleName);
		return new File(bundleDir, trackTitle);
	}

}
