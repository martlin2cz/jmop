package cz.martlin.jmop.common.storages.storage.filesystemer.locators;

import java.io.File;

import cz.martlin.jmop.common.storages.components.AllInOneDirStorageComponent;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BaseBundleDataFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BasePlaylistFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BaseTrackFileNamer;

/**
 * Bundle, playlist and track data file locator locating the bundle file in
 * all-in-one-dir storage. Delegates to the particular namers.
 * 
 * It's part of the various components of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public class AllInOneDirDataFilesLocator implements AbstractCommonLocator, AllInOneDirStorageComponent {

	private final File root;
	private final BaseBundleDataFileNamer bundleNamer;
	private final BasePlaylistFileNamer playlistNamer;
	private final BaseTrackFileNamer trackNamer;

	public AllInOneDirDataFilesLocator(File root, BaseBundleDataFileNamer bundleNamer,
			BasePlaylistFileNamer playlistNamer, BaseTrackFileNamer trackNamer) {
		super();
		this.root = root;
		this.bundleNamer = bundleNamer;
		this.playlistNamer = playlistNamer;
		this.trackNamer = trackNamer;
	}

	@Override
	public File bundleDataFile(String bundleName) {
		String fileName = bundleNamer.bundleDataFileName(bundleName);
		return new File(root, fileName);
	}

	@Override
	public File playlistFile(String bundleName, String playlistName) {
		String fileName = playlistNamer.playlistFileName(bundleName, playlistName);
		return new File(root, fileName);
	}

	@Override
	public File trackFile(String bundleName, String trackTitle) {
		String fileName = trackNamer.trackFileName(bundleName, trackTitle);
		return new File(root, fileName);
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
