package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class TestingDownloader implements BaseSourceDownloader {

	public static final TrackFileFormat DOWNLOAD_FORMAT = TrackFileFormat.OPUS;
	public static final String TESTING_SAMPLE_FILE = "samples/sample.opus";


	private final BaseLocalSource local;

	public TestingDownloader(BaseLocalSource local) {
		super();
		this.local = local;
	}

	@Override
	public boolean download(Track track) throws Exception {
		InputStream ins = getClass().getClassLoader().getResourceAsStream(TESTING_SAMPLE_FILE);

		File targetFile = local.fileOfTrack(track, DOWNLOAD_FORMAT);

		Files.copy(ins, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		return true;
	}

	@Override
	public void specifyListener(ProgressListener listener) {
		// nothing
	}

}
