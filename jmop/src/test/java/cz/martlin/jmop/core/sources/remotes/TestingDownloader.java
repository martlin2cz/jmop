package cz.martlin.jmop.core.sources.remotes;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TrackFileFormat;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;

public class TestingDownloader implements BaseSourceDownloader {

	public static final String TESTING_SAMPLE_FILE = "samples/sample.opus";

	private final TrackFileFormat downloadFormat;
	private final BaseLocalSource local;

	public TestingDownloader(BaseLocalSource local, TrackFileFormat downloadFormat) {
		super();
		this.local = local;
		this.downloadFormat = downloadFormat;
	}

	@Override
	public TrackFileFormat formatOfDownload() {
		return downloadFormat;
	}

	@Override
	public boolean download(Track track, TrackFileLocation location) throws Exception {
		InputStream ins = getClass().getClassLoader().getResourceAsStream(TESTING_SAMPLE_FILE);

		File targetFile = local.fileOfTrack(track, location, downloadFormat);

		Files.copy(ins, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		return true;
	}

	@Override
	public void specifyListener(ProgressListener listener) {
		// nothing
	}

	@Override
	public boolean check() {
		return true;
	}

}
