package cz.martlin.jmop.core.sources.locals;

import java.io.InputStream;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class TestingTrackFileAccessor {
	public static final String TESTING_SAMPLE_FILE = "samples/sample.opus"; //$NON-NLS-1$
	public static final TrackFileFormat TESTING_FILE_FORMAT = TrackFileFormat.OPUS;

	public TestingTrackFileAccessor() {
	}

	public static InputStream read() {
		InputStream ins = TestingTrackFileAccessor.class //
				.getClassLoader().getResourceAsStream(TESTING_SAMPLE_FILE);

		return ins;
	}
}
