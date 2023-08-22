package cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.impls;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.components.BundlesDirStorageComponent;
import cz.martlin.jmop.common.storages.filesystem.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BasePlaylistLocator;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.BasePlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.ByIdentifierPlaylistsLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The the lister of playist files in the bundles dir storage. Lists particular
 * playlists files.
 * 
 * It's component of {@link ByIdentifierPlaylistsLoader}.
 * 
 * @author martin
 *
 */
public class BundlesDirPlaylistsIdentifiersLister
		implements BasePlaylistsIdentifiersLister<File>, BundlesDirStorageComponent {

	private final BaseFileSystemAccessor fs;
	private final BaseBundlesDirLocator bundlesDirLocator;
	private final BasePlaylistLocator playlistLocator;

	public BundlesDirPlaylistsIdentifiersLister(BaseFileSystemAccessor fs, BaseBundlesDirLocator bundlesDirLocator,
			BasePlaylistLocator playlistLocator) {
		super();
		this.fs = fs;
		this.bundlesDirLocator = bundlesDirLocator;
		this.playlistLocator = playlistLocator;
	}

	@Override
	public Set<File> loadPlaylistsIdentifiers(Bundle bundle) throws JMOPPersistenceException {
		File bundleDir = bundlesDirLocator.bundleDir(bundle.getName());
		return fs.listFiles(bundleDir) //
				.filter((f) -> playlistLocator.isPlaylistFile(f)) //
				.collect(Collectors.toSet());
	}

}
