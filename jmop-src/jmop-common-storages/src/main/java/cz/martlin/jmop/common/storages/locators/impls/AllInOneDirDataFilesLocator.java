package cz.martlin.jmop.common.storages.locators.impls;

import java.io.File;

import cz.martlin.jmop.common.storages.AllInOneDirStorageComponent;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataFileNamer;
import cz.martlin.jmop.common.storages.locators.BasePlaylistFileNamer;
import cz.martlin.jmop.common.storages.locators.BaseTrackFilesNamer;

/**
 * Bundle, playlist and track data file locator locating the bundle file in all
 * in one dir. Delegates to the particular namers.
 * 
 * @author martin
 *
 */
public class AllInOneDirDataFilesLocator implements AbstractCommonLocator, AllInOneDirStorageComponent {

	private final File root;
	private final BaseBundleDataFileNamer bundleNamer;
	private final BasePlaylistFileNamer playlistNamer;
	private final BaseTrackFilesNamer trackNamer;

	public AllInOneDirDataFilesLocator(File root, BaseBundleDataFileNamer bundleNamer,
			BasePlaylistFileNamer playlistNamer, BaseTrackFilesNamer trackNamer) {
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
