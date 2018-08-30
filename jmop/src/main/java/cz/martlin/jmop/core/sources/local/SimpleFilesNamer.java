package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public abstract class SimpleFilesNamer implements BaseFilesNamer {

	protected static final String DOT = ".";

	public SimpleFilesNamer() {
		super();
	}

	@Override
	public File fileOfTrack(File root, Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format) {
		File directory = locatedDirectoryOfBundle(root, location, bundle.getKind(), bundle.getName());

		String trackFileName = filenameOfTrack(track);

		String extension = format.getExtension();

		String trackFile = trackFileName + DOT + extension;
		return new File(directory, trackFile);
	}

	protected abstract String filenameOfTrack(Track track);

	/////////////////////////////////////////////////////////////////////////////////////
	private File locatedDirectoryOfBundle(File root, TrackFileLocation location, SourceKind kind, String name) {
		switch (location) {
		case CACHE:
			return cacheDirectoryOfBundle(root, kind, name);
		case SAVE:
			return saveDirectoryOfBundle(root, kind, name);
		case TEMP:
			return tempDirectoryOfBundle(root, kind, name);
		default:
			throw new IllegalArgumentException("Unknown location: " + location);
		}
	}

	

	private File saveDirectoryOfBundle(File root, SourceKind kind, String name) {
		return directoryOfBundle(root, kind, name);
	}

	private File cacheDirectoryOfBundle(File root, SourceKind kind, String name) {
		String cacheDirName = cacheDirectoryName();
		File cacheDir = new File(root, cacheDirName);
		File cacheBundleDir = new File(cacheDir, name);
		return cacheBundleDir;
	}

	protected abstract String cacheDirectoryName();

	private File tempDirectoryOfBundle(File root, SourceKind kind, String name) {
		return tmpDirectory();
	}
	
	@Override
	public File tmpDirectory() {
		String tmpDirPath = System.getProperty("java.io.tmpdir");
		File tmpDirRoot = new File(tmpDirPath);

		String tmpDirName = tmpDirectoryName();
		return new File(tmpDirRoot, tmpDirName);
	}

	protected abstract String tmpDirectoryName();

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public File directoryOfBundle(File root, SourceKind source, String name) {
		String directoryName = dirnameOfBundle(source, name);
		return new File(root, directoryName);
	}

	protected abstract String dirnameOfBundle(SourceKind source, String name);

	@Override
	public String dirToBundleName(File directory) {
		String directoryName = directory.getName();
		return bundleNameOfDirectory(directoryName);
	}

	protected abstract String bundleNameOfDirectory(String directoryName);

	@Override
	public boolean isBundleDirectory(File directory) {
		String name = directory.getName();
		return isBundleDir(name);
	}

	protected abstract boolean isBundleDir(String directoryName);

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String fileToPlaylistName(File file) {
		String filename = file.getName();

		return nameOfPlaylist(filename);
	}

	protected abstract String nameOfPlaylist(String filename);

	@Override
	public File fileOfPlaylist(File root, SourceKind source, String bundleName, String playlistName) {
		File bundleDir = directoryOfBundle(root, source, bundleName);
		String playlistFileName = filenameOfPlaylist(playlistName);

		return new File(bundleDir, playlistFileName);
	}

	protected abstract String filenameOfPlaylist(String name);

	@Override
	public boolean isPlaylistFile(File file) {
		String name = file.getName();
		return isPlaylistFile(name);
	}

	protected abstract boolean isPlaylistFile(String fileName);

}
