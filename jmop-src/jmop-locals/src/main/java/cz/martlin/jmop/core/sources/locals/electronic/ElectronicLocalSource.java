package cz.martlin.jmop.core.sources.locals.electronic;

import cz.martlin.jmop.core.sources.local.AbstractLocalSource;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.local.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.BasePlaylistsLocalSource;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.locals.BasePlaylistFilesLoaderStorer;

public class ElectronicLocalSource extends AbstractLocalSource {

	private final BaseFilesLocator locator;
	private final BaseFileSystemAccessor filesystem;
	private final BasePlaylistFilesLoaderStorer pfls;

	public ElectronicLocalSource(BaseFilesLocator locator, BaseFileSystemAccessor filesystem,
			BasePlaylistFilesLoaderStorer pfls) {
		super();
		this.locator = locator;
		this.filesystem = filesystem;
		this.pfls = pfls;
	}

	@Override
	protected BaseTracksLocalSource createTracksSource() {
		return new ElectronicTracksSource(locator, filesystem);
	}

	@Override
	protected BaseBundlesLocalSource createBundlesSource() {
		return new ElectronicBundlesSource(locator, filesystem, pfls);
	}

	@Override
	protected BasePlaylistsLocalSource createPlaylistsSource() {
		return new ElectronicPlaylistsSource(locator, filesystem, pfls);
	}

}
