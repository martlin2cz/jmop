package cz.martlin.jmop.core.sources.locals.testing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class TestingTrackFileAccessor {
	private static final Logger LOG = LoggerFactory.getLogger(TestingTrackFileAccessor.class);

	private static final String FILE_DIR = "samples";
	private static final String FILE_BASENAME = "sample";

	private TestingTrackFileAccessor() {
	}

	public static InputStream read(TrackFileFormat format) {
		String fileName = FILE_DIR + "/" + FILE_BASENAME + "." + format.getExtension();

		InputStream ins = TestingTrackFileAccessor.class //
				.getClassLoader().getResourceAsStream(fileName);

		return ins;
	}

	public static void create(File file, TrackFileFormat format) throws IOException {
		LOG.info("Creating testing track file " + file.getAbsolutePath() + "...");
		InputStream ins = read(format);

		Path path = file.toPath();
		Files.copy(ins, path);

		LOG.info("Created testing track file " + file.getAbsolutePath() + "!");
	}
}
