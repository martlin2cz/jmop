package cz.martlin.jmop.common.storages.locators.impls;

import java.io.File;

import cz.martlin.jmop.common.storages.BundlesDirStorageComponent;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataFileNamer;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirNamer;
import cz.martlin.jmop.common.storages.locators.BasePlaylistFileNamer;
import cz.martlin.jmop.common.storages.locators.BaseTrackFilesNamer;

/**
 * Bundle, playlist and track data file locator locating the bundle file in the
 * bundles dir. Delegates to the particular namers.
 * 
 * @author martin
 *
 */
public class BundlesDirDataFilesLocator
		implements AbstractCommonLocator, BaseBundlesDirLocator, BundlesDirStorageComponent {

	private final File root;
	private final BaseBundlesDirNamer bundlesDirNamer;
	private final BaseBundleDataFileNamer bundleNamer;
	private final BasePlaylistFileNamer playlistNamer;
	private final BaseTrackFilesNamer trackNamer;

	public BundlesDirDataFilesLocator(File root, BaseBundlesDirNamer bundlesDirNamer,
			BaseBundleDataFileNamer bundleNamer, BasePlaylistFileNamer playlistNamer, BaseTrackFilesNamer trackNamer) {
		super();
		this.root = root;
		this.bundlesDirNamer = bundlesDirNamer;
		this.bundleNamer = bundleNamer;
		this.playlistNamer = playlistNamer;
		this.trackNamer = trackNamer;
	}

	@Override
	public File bundleDir(String bundleName) {
		String dirName = bundlesDirNamer.bundleDirName(bundleName);
		return new File(root, dirName);
	}

	@Override
	public File bundleDataFile(String bundleName) {
		File dirName = bundleDir(bundleName);
		String fileName = bundleNamer.bundleDataFileName(bundleName);
		return new File(dirName, fileName);
	}

	@Override
	public File playlistFile(String bundleName, String playlistName) {
		File dirName = bundleDir(bundleName);
		String fileName = playlistNamer.playlistFileName(bundleName, playlistName);
		return new File(dirName, fileName);
	}

	@Override
	public File trackFile(String bundleName, String trackTitle) {
		File dirName = bundleDir(bundleName);
		String fileName = trackNamer.trackFileName(bundleName, trackTitle);
		return new File(dirName, fileName);
	}

	@Override
	public boolean isBundleDataFile(File file) {
		String fileName = file.getName();
		return bundleNamer.isBundleDataFile(fileName);
	}

	@Override
	public boolean isPlaylistFile(File file) {
		String fileName = file.getName();
		return playlistNamer.isPlaylistFile(fileName);
	}
}
