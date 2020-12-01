package cz.martlin.jmop.core.sources.locals.testing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public class TestingTracksSource implements BaseTracksLocalSource {

	private final TestingDirectoryAccessor tda;

	public TestingTracksSource() {
		this.tda = new TestingDirectoryAccessor("testing-tracks-source");
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void create(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException {

		File file = fileOfTrack(track, location, format);
		try {
			TestingTrackFileAccessor.create(file, format);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot create track file", e);
		}
	}

	public void cleanup() throws JMOPSourceException {
		try {
			tda.checkAndDelete();
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot delete testing directory", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException {

		try {
			tda.checkAndCreate();

			File tempDir = tda.getDir();
			String locationDirName = location.name();
			File locationDir = new File(tempDir, locationDirName);
			if (!locationDir.isDirectory()) {
				Files.createDirectories(locationDir.toPath());
			}

			String filenamename = "track-" + track.getIdentifier() + "." + format.getExtension();

			return new File(locationDir, filenamename);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot create dir or obtain track file", e);
		}
	}

	@Override
	public boolean exists(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException {
		File file = fileOfTrack(track, location, format);
		return file.exists();
	}

	@Override
	public void deleteIfExists(Track track, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException {

		File file = fileOfTrack(track, location, format);
		if (!file.exists()) {
			return;
		}

		try {
			Files.delete(file.toPath());
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot delete track file", e);
		}
	}

	@Override
	public void move(Track oldTrack, Track newTrack, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException {
		// since both files has the same content,
		// just simply remove old and create new
		try {
			deleteIfExists(oldTrack, location, format);
			create(newTrack, location, format);
		} catch (JMOPSourceException e) {
			throw new JMOPSourceException("Cannot move track file", e);
		}
	}

}
