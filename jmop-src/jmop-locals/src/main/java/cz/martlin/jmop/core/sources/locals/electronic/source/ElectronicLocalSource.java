package cz.martlin.jmop.core.sources.locals.electronic.source;

import java.io.File;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.BasePlaylistsLocalSource;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFilesLocator;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFilesNamer;
import cz.martlin.jmop.core.sources.locals.electronic.impls.ElectronicFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.electronic.impls.ElectronicFilesLocator;
import cz.martlin.jmop.core.sources.locals.electronic.impls.ElectronicFilesNamer;
import cz.martlin.jmop.core.sources.locals.electronic.xspf.XSPFPlaylistFilesLoaderStorer;

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
