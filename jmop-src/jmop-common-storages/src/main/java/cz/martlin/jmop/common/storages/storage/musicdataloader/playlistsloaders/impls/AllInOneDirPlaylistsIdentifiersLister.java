package cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.impls;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.storages.components.AllInOneDirStorageComponent;
import cz.martlin.jmop.common.storages.filesystem.BaseFileSystemAccessor;
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
public class AllInOneDirPlaylistsIdentifiersLister
		implements BasePlaylistsIdentifiersLister<File>, AllInOneDirStorageComponent {

	private final File root;
	private final BaseFileSystemAccessor fs;
	private final BasePlaylistLocator playlistLocator;

	public AllInOneDirPlaylistsIdentifiersLister(File root, BaseFileSystemAccessor fs,
			BasePlaylistLocator playlistLocator) {
		super();
		this.root = root;
		this.fs = fs;
		this.playlistLocator = playlistLocator;
	}

	@Override
	public Set<File> loadPlaylistsIdentifiers(Bundle bundle) throws JMOPPersistenceException {
		return fs.listFiles(root) //
				.filter((f) -> playlistLocator.isPlaylistFile(f)) //
				.collect(Collectors.toSet());
	}

}
