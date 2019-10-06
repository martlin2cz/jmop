package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.locals.BasePlaylistFilesLoaderStorer;

public class ElectronicFilesLocator extends AbstractFilesLocator {

	private final File root;
	/* private final BaseFilesNamer namer; */

	public ElectronicFilesLocator(File root/* , BaseFilesNamer namer */, BasePlaylistFilesLoaderStorer pfls) {
		super(pfls, root);
		this.root = root;
		/* this.namer = namer; */
	}

	/////////////////////////////////////////////////////////////////

	@Override
	protected String getBasenameOfPlaylistFile(String playlistName) {
		return playlistName; // FIXME properly
	}

	@Override
	protected String getBundleDirName(String bundleName) {
		return bundleName;// FIXME properly
	}

	@Override
	protected String getTrackFileBasename(Track track) {
		return "track-" + track.getIdentifier();// FIXME properly
	}

	@Override
	public File getRootDirectory() {
		return root;
	}

	@Override
	public File getTempRootDirectory() {
		throw new UnsupportedOperationException();
		/*
		 * String tempDirName = namer.nameOfTempDir(); // TODO temp dir return new
		 * File(root, "temp-" + tempDirName);
		 */
	}

	@Override
	public File getSaveRootDirectory() {
		return root;
	}

	@Override
	public File getCacheRootDirectory() {
		throw new UnsupportedOperationException();
		/*
		 * String cacheDirName = namer.nameOfCacheDir(); return new File(root,
		 * cacheDirName);
		 */
	}

}
