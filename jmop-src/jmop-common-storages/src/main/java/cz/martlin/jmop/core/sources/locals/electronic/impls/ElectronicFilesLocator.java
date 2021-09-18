package cz.martlin.jmop.core.sources.locals.electronic.impls;

import java.io.File;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.base.BasePlaylistFilesLoaderStorer;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFilesLocator;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFilesNamer;

public class ElectronicFilesLocator implements BaseFilesLocator {

	private final BasePlaylistFilesLoaderStorer pfls;
	private final BaseFilesNamer namer;
	private final File root;
	private final File tempDir;

	public ElectronicFilesLocator(BasePlaylistFilesLoaderStorer pfls, BaseFilesNamer namer, File root) {
		super();
		this.pfls = pfls;
		this.namer = namer;
		this.root = root;
		this.tempDir = getTempDir();
	}

	@Override
	public File getRootDirectory() {
		return root;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public File getFileOfPlaylist(String bundleDirName, String playlistName) {
		File bundleDir = new File(root, bundleDirName);

		String playlistFileBasename = namer.fileBasenameOfPlaylist(playlistName);
		String playlistFileName = playlistFileBasename + "." + pfls.extensionOfFile();
		return new File(bundleDir, playlistFileName);
	}

//	protected abstract String getBasenameOfPlaylistFile(String playlistName);

	@Override
	public File getDirectoryOfBundle(String bundleName, TrackFileLocation location) {
		return obtainOwningDirectory(bundleName, location);
	}

//	protected abstract String getBundleDirName(String bundleName);

	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format) {
		String bundleName = bundle.getName();
		File bundleDir = obtainOwningDirectory(bundleName, location);

		String trackFileBasename = namer.fileBasenameOfTrack(track);
		String trackFileName = trackFileBasename + "." + format.getExtension();

		return new File(bundleDir, trackFileName);
	}
///////////////////////////////////////////////////////////////////////////

	private File obtainOwningDirectory(String bundleName, TrackFileLocation location) {
		switch (location) {
		case CACHE:
			return getCacheDirectory();
		case SAVE:
			return getSaveDirectory(bundleName);
		case TEMP:
			return getTempDirectory();
		default:
			throw new IllegalArgumentException("Unknown location");
		}
	}

	private File getTempDirectory() {
		String dirName = namer.nameOfTempDir();
		return new File(tempDir, dirName);

	}

	private File getCacheDirectory() {
		String dirName = namer.nameOfCacheDir();
		return new File(root, dirName);
	}

	private File getSaveDirectory(String bundleName) {
		String dirName = namer.directoryNameOfBundle(bundleName);

		return new File(root, dirName);
	}

///////////////////////////////////////////////////////////////////////////

	private static final File getTempDir() {
		String path = System.getProperty("java.io.tmpdir");
		return new File(path);
	}

//	protected abstract String getTrackFileBasename(Track track);

//	public abstract File getTempRootDirectory();
//	public abstract File getSaveRootDirectory();
//	public abstract File getCacheRootDirectory();

}
