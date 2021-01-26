package cz.martlin.jmop.common.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

class TestingTracksSourceTest {

	@Test
	void testWav()  {
		check(TrackFileFormat.WAV);
	}
	
	@Test
	void testMP3()  {
		check(TrackFileFormat.MP3);
	}
	
	@Disabled
	@Test
	void testOPUS()  {
		check(TrackFileFormat.OPUS);
	}
	
	///////////////////////////////////////////////////////////////////////////

	private void check(TrackFileFormat format)  {
		TestingTracksSource source = new TestingTracksSource(format);
		
		Track track = createTestingTrack();
		File file = source.trackFile(track);
		assertTrue(file.isFile());
	}

	private Track createTestingTrack() {
		Bundle bundle = new Bundle("baaar-bundle", Metadata.createNew());
		Track track = new Track(bundle, "foo-id", "foo-title", "foo-description", //
				Duration.minutes(1), Metadata.createNew());
		return track;
	}

}
