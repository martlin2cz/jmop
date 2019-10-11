package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.local.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.BasePlaylistsLocalSource;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;

public class ElectronicLocalSource implements BaseLocalSource {

	private final BaseBundlesLocalSource bundles;
	private final BaseTracksLocalSource tracks;
	private final BasePlaylistsLocalSource playlists;

	public ElectronicLocalSource(BaseConfiguration config, File root) {
		super();

		BaseFileSystemAccessor filesystem = new ElectronicFileSystemAccessor();

		XSPFPlaylistFilesLoaderStorer xpfls = new XSPFPlaylistFilesLoaderStorer(config);
		BaseFilesNamer namer = new ElectronicFilesNamer();
		BaseFilesLocator locator = new ElectronicFilesLocator(xpfls, namer, root);

		this.bundles = new ElectronicBundlesSource(config, locator, filesystem, xpfls);
		this.tracks = new ElectronicTracksSource(locator, filesystem);
		this.playlists = new ElectronicPlaylistsSource(locator, filesystem, xpfls);
	}

	@Override
	public BaseBundlesLocalSource bundles() {
		return bundles;
	}

	@Override
	public BaseTracksLocalSource tracks() {
		return tracks;
	}

	@Override
	public BasePlaylistsLocalSource playlists() {
		return playlists;
	}

}
