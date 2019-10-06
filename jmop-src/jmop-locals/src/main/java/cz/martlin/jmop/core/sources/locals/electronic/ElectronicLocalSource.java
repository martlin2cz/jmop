package cz.martlin.jmop.core.sources.locals.electronic;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.sources.local.AbstractLocalSource;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.local.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.BasePlaylistsLocalSource;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.locals.BaseBundleFilesLoaderStorer;
import cz.martlin.jmop.core.sources.locals.BasePlaylistFilesLoaderStorer;

public class ElectronicLocalSource extends AbstractLocalSource {

	private final BaseConfiguration config;
	private final BaseFilesLocator locator;
	private final BaseFileSystemAccessor filesystem;
	private final BasePlaylistFilesLoaderStorer pfls;
	private final BaseBundleFilesLoaderStorer bfls;

	public ElectronicLocalSource(BaseConfiguration config, BaseFilesLocator locator, BaseFileSystemAccessor filesystem,
			BasePlaylistFilesLoaderStorer pfls, BaseBundleFilesLoaderStorer bfls) {
		super();
		this.config = config;
		this.locator = locator;
		this.filesystem = filesystem;
		this.pfls = pfls;
		this.bfls = bfls;
	}

	@Override
	protected BaseTracksLocalSource createTracksSource() {
		return new ElectronicTracksSource(locator, filesystem);
	}

	@Override
	protected BaseBundlesLocalSource createBundlesSource() {
		return new ElectronicBundlesSource(config, locator, filesystem, bfls);
	}

	@Override
	protected BasePlaylistsLocalSource createPlaylistsSource() {
		return new ElectronicPlaylistsSource(locator, filesystem, pfls);
	}

}
