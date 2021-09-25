package cz.martlin.jmop.common.storages.loader.playlists.impls;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.BundlesDirStorageComponent;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.loader.playlists.BasePlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.BasePlaylistLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The {@link BasePlaylistsIdentifiersLister} in the bundles dir storage. Lists
 * particular playlists files.
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
