package cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.impls;

import cz.martlin.jmop.common.storages.components.AllTracksPlaylistStorageComponent;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BaseBundleDataFileNamer;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.namers.BasePlaylistFileNamer;

/**
 * The bundle data file namer saving the bundle data into the
 * all-tracks-playlist file.
 * 
 * @author martin
 *
 */
public class AllTracksPlaylistBundleDataFileNamer
		implements BaseBundleDataFileNamer, AllTracksPlaylistStorageComponent {

	private final String allTracksPlaylistName;
	private final BasePlaylistFileNamer namer;

	public AllTracksPlaylistBundleDataFileNamer(String allTracksPlaylistName, BasePlaylistFileNamer namer) {
		super();
		this.allTracksPlaylistName = allTracksPlaylistName;
		this.namer = namer;
	}

	@Override
	public String bundleDataFileName(String bundleName) {
		return namer.playlistFileName(bundleName, allTracksPlaylistName);
	}

	@Override
	public boolean isBundleDataFile(String fileName) {
		String allTracksPlaylistFileName = namer.playlistFileName(null, allTracksPlaylistName); // FIXME provide bundle
																								// name
		return allTracksPlaylistFileName.equals(fileName);
	}
}
