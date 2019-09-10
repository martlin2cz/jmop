package cz.martlin.jmop.core.sources.remote.empty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.AbstractLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.TestingTrackFileAccessor;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;

public class TestingDownloader implements BaseDownloader {




	private final TrackFileFormat downloadFormat;
	private final BaseLocalSource local;

	public TestingDownloader(BaseLocalSource local, TrackFileFormat downloadFormat) {
		super();
		this.local = local;
		this.downloadFormat = downloadFormat;
	}

	@Override
	public TrackFileFormat downloadFormat() {
		return TestingTrackFileAccessor.TESTING_FILE_FORMAT;
	}

	@Override
	public BaseLongOperation<Track, Track> download(Track track, TrackFileLocation location)
			throws JMOPSourceException {

		File file = local.fileOfTrack(track, location, downloadFormat);
		return new TestingDownloaderOperation(track, file);
	}

	/**
	 * Copies testing file to given file.
	 * 
	 * @param targetFile
	 * @throws JMOPSourceException
	 * @throws IOException
	 */
	public static void copyTestingFileTo(File targetFile) throws JMOPSourceException, IOException {
		InputStream ins = TestingTrackFileAccessor.read();

		Files.copy(ins, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public static class TestingDownloaderOperation extends AbstractLongOperation<Track, Track> {
		private final File file;

		public TestingDownloaderOperation(Track track, File file) {
			super("Testing downloading", track, (t) -> t.toHumanString());

			this.file = file;
		}

		@Override
		public Track run(BaseProgressListener listener) throws Exception {
			copyTestingFileTo(file);

			return getInput();
		}

		@Override
		public void terminate() {
			// termination not supported
		}

	}

}
