package cz.martlin.jmop.common.utils;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class TestingTracksSource implements TracksSource {

	@Override
	public File trackFile(Track track) throws JMOPMusicbaseException {
		try {
			File file = File.createTempFile("track-" + track.getTitle(), ".wav");
			byte[] bytes = new byte[1000000];
			Files.write(bytes, file);
			
			System.out.println("Prepared testing track file: " + file.getAbsolutePath());
			return file;
		} catch (IOException e) {
			throw new JMOPMusicbaseException("Cannot create testing file", e);
		}
	}

}
