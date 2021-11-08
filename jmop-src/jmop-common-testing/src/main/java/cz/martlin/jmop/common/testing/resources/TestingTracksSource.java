package cz.martlin.jmop.common.testing.resources;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The testing implementation of the {@link TracksLocator}. Simply pick
 * predefined sample file, copies it to the temp dir and returns its path.
 * 
 * @author martin
 *
 */
public class TestingTracksSource {

	private final TrackFileFormat format;
	private final TestingTrackFilesCreator creator;

	public TestingTracksSource(TrackFileFormat format) {
		super();
		this.format = format;
		this.creator = new TestingTrackFilesCreator();
	}

	public File trackFile(Track track)  {
		try {
			File file = prepareTrackFile(track);
			creator.prepare(format, file);
			
			System.out.println("Prepared testing track file: " + file.getAbsolutePath() + " for the: " + track);
			return file;
		} catch (IOException e) {
			throw new RuntimeException("Cannot create testing file", e);
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
