package cz.martlin.jmop.core.sources.remote.empty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.AbstractLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.testing.TestingTrackFileAccessor;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;

public class TestingDownloader implements BaseDownloader {




	private final TrackFileFormat downloadFormat;
	private final BaseTracksLocalSource tracks;

	public TestingDownloader(BaseTracksLocalSource tracks, TrackFileFormat downloadFormat) {
		super();
		this.tracks = tracks;
		this.downloadFormat = downloadFormat;
	}

	@Override
	public TrackFileFormat downloadFormat() {
		return downloadFormat;
	}

	@Override
	public BaseLongOperation<Track, Track> download(Track track, TrackFileLocation location)
			throws JMOPMusicbaseException {

		File file = tracks.fileOfTrack(track, location, downloadFormat);
		return new TestingDownloaderOperation(track, file);
	}

	/**
	 * Copies testing file to given file.
	 * 
	 * @param targetFile
	 * @throws JMOPMusicbaseException
	 * @throws IOException
	 */
	public void copyTestingFileTo(File targetFile) throws JMOPMusicbaseException, IOException {
		InputStream ins = TestingTrackFileAccessor.read(downloadFormat);

		Files.copy(ins, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public class TestingDownloaderOperation extends AbstractLongOperation<Track, Track> {
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
