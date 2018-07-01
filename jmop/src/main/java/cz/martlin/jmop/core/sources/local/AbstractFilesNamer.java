package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public abstract class AbstractFilesNamer implements BaseFilesNamer {


	private final File rootDir;

	public AbstractFilesNamer(File rootDir) {
		this.rootDir = rootDir;
	}

	@Override
	public File fileOfTrack(Bundle bundle, Track track) {
		File bundleDir = directoryOfBundle(bundle);
		String trackFile = filenameOfTrack(track);

		return new File(bundleDir, trackFile);
	}
	
	private File directoryOfBundle(Bundle bundle) {
		String bundleName = dirnameOfBundle(bundle);
		return new File(rootDir, bundleName);
	}

	protected abstract String dirnameOfBundle(Bundle bundle);


	protected abstract String filenameOfTrack(Track track);

}
