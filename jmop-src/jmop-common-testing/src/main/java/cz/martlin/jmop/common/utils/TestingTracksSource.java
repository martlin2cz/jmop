package cz.martlin.jmop.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.Files;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The testing implementation of the {@link TracksSource}. Simply pick
 * predefined sample file, copies it to the temp dir and returns its path.
 * 
 * @author martin
 *
 */
public class TestingTracksSource implements TracksSource {

	private final TrackFileFormat format;

	public TestingTracksSource(TrackFileFormat format) {
		super();
		this.format = format;
	}

	@Override
	public File trackFile(Track track) throws JMOPMusicbaseException {
		try {
			byte[] data = readSampleTrack();
			File file = prepareTrackFile(track);

			Files.write(data, file);
			System.out.println("Prepared testing track file: " + file.getAbsolutePath() + " for the: " + track);
			return file;
		} catch (IOException e) {
			throw new JMOPMusicbaseException("Cannot create testing file", e);
		}
	}

	/**
	 * Reads the sample track resource.
	 * 
	 * @return
	 * @throws IOException
	 */
	private byte[] readSampleTrack() throws IOException {
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

	/**
	 * Prepares the (empty) final track file.
	 * 
	 * @param track
	 * @return
	 * @throws IOException
	 */
	private File prepareTrackFile(Track track) throws IOException {
		String prefix = "track-" + track.getTitle() + "-";
		String suffix = "." + format.fileExtension();
		File file = File.createTempFile(prefix, suffix);
		return file;
	}

}
