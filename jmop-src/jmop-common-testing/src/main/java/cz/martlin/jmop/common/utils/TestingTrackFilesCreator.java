package cz.martlin.jmop.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;


public class TestingTrackFilesCreator {

	public TestingTrackFilesCreator() {
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
		String path = "cz/martlin/jmop/common/testing/sample." + format.fileExtension();

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
