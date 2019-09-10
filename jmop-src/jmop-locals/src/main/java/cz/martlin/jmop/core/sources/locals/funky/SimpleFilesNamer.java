package cz.martlin.jmop.core.sources.locals.funky;

import java.io.File;

import cz.martlin.jmop.core.sources.local.BaseFilesNamer;

/**
 * The simple, but abstract implementation of files namer. Specifies abstract
 * "atomic" methods for simply converting bundle/playlist/track to dir name/file
 * name. Assumes track and playlist files are located in their bundle directory,
 * temporary location lays as a subdir of system temp dir, and cache directory
 * is located in the root and is flat (no subdirectories for bundles).
 * 
 * @author martin
 *
 */
public abstract class SimpleFilesNamer implements BaseFilesNamer {

	protected static final String DOT = "."; //$NON-NLS-1$

	public SimpleFilesNamer() {
		super();
	}

	@Override
	public File bundleDirOfBundleDirName(File root, String bundleDirName) {
		return new File(root, bundleDirName);
	}

	@Override
	public File bundleDirOfBundleName(File root, String bundleName) {
		String bundleDirName = directoryNameOfBundle(bundleName);
		return bundleDirOfBundleDirName(root, bundleDirName);
	}

	@Override
	public File cacheBundleDir(File root, String bundleName) {
		String cacheDirName = directoryNameOfCache();
		String bundleDirName = directoryNameOfBundle(bundleName);

		File cache = new File(root, cacheDirName);
		return new File(cache, bundleDirName);
	}

	/**
	 * Returns name of cache directory.
	 * 
	 * @return
	 */
	public abstract String directoryNameOfCache();

	@Override
	public File tempBundleDir(String bundleName) {
		File tempDirRoot = getSystemTempDirectory();
		String tempDirName = temporaryDirectoryName();
		return new File(tempDirRoot, tempDirName);
	}

	private File getSystemTempDirectory() {
		String path = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$
		return new File(path);
	}

	/**
	 * Returns name of temporary directory (directory located in the operating
	 * system temp dir).
	 * 
	 * @return
	 */
	public abstract String temporaryDirectoryName();

	@Override
	public File playlistFileOfPlaylist(File root, String bundleDirName, String playlistName,
			String playlistFileExtension) {
		String playlistFileName = fileNameOfPlaylist(playlistName, playlistFileExtension);
		return playlistFileOfFile(root, bundleDirName, playlistFileName);
	}

	@Override
	public File playlistFileOfFile(File root, String bundleDirName, String playlistFileName) {
		File bundleDir = new File(root, bundleDirName);
		return new File(bundleDir, playlistFileName);
	}

}
