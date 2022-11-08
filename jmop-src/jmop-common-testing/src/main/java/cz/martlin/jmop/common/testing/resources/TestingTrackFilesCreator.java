package cz.martlin.jmop.common.testing.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;


public class TestingTrackFilesCreator {

	public static final Duration SAMPLE_FILE_DURATION = DurationUtilities.createDuration(0, 0, 10);
	
	public TestingTrackFilesCreator() {
	}
	
	public File prepare(TrackFileFormat format) throws IOException {
		String extension = format.fileExtension();
		File file = File.createTempFile("sample-track-", "." + extension );
		prepare(format, file);
		return file;
	}
	
	
	public void prepare(TrackFileFormat format, File file) throws IOException {
		byte[] data = readSampleTrack(format);
		Path path = file.toPath();
		
		Files.write(path, data);
	}
	

	/**
	 * Reads the sample track resource.
	 * 
	 * @return
	 * @throws IOException
	 */
	private byte[] readSampleTrack(TrackFileFormat format) throws IOException {
		String path = "cz/martlin/jmop/common/testing/resources/sample." + format.fileExtension();

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream ins = classloader.getResourceAsStream(path);
		if (ins == null) {
			throw new FileNotFoundException("The sample track in format " + format + " not found.");
		}
		
		try {
			return ins.readAllBytes();
		} finally {
			ins.close();
		}
	}
}
