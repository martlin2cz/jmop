package cz.martlin.jmop.core.sources.local;

import java.io.File;

public abstract class SimpleFilesNamer implements BaseFilesNamer {

	protected static final String DOT = ".";

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

	public abstract String directoryNameOfCache();

	@Override
	public File tempBundleDir(String bundleName) {
		File tempDirRoot = getSystemTempDirectory();
		String tempDirName = temporaryDirectoryName();
		return new File(tempDirRoot, tempDirName);
	}

	private File getSystemTempDirectory() {
		String path = System.getProperty("java.io.tmpdir");
		return new File(path);
	}

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
