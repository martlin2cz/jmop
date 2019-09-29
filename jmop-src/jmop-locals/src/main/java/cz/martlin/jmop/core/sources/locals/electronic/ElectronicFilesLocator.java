package cz.martlin.jmop.core.sources.locals.electronic;

import java.io.File;

import cz.martlin.jmop.core.sources.local.BaseFilesNamer;

public class ElectronicFilesLocator extends AbstractFilesLocator {

	private final File root;
	private final BaseFilesNamer namer;

	public ElectronicFilesLocator(File root, BaseFilesNamer namer) {
		super();
		this.root = root;
		this.namer = namer;
	}

	/////////////////////////////////////////////////////////////////

	@Override
	public File getRootDirectory() {
		return root;
	}

	@Override
	public File getTempRootDirectory() {
		String tempDirName = namer.nameOfTempDir();
		// TODO temp dir
		return new File(root, "temp-" + tempDirName);
	}

	@Override
	public File getSaveRootDirectory() {
		return root;
	}

	@Override
	public File getCacheRootDirectory() {
		String cacheDirName = namer.nameOfCacheDir();
		return new File(root, cacheDirName);
	}

}
