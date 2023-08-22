package cz.martlin.jmop.common.testing.resources;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Track;
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
		
		Track track;
		try {
			track = createTestingTrack();
		} catch (IOException e) {
			assumeTrue(e == null);
			return;
		}
		File file = source.trackFile(track);
		assertTrue(file.isFile());
	}

	private Track createTestingTrack() throws IOException {
		Bundle bundle = new Bundle("baaar-bundle", Metadata.createNew());
		Track track = new Track(bundle, "foo-title", "foo-description", //
				Duration.minutes(1), URI.create("foo.net"), File.createTempFile("track", ".whatever"), Metadata.createNew());
		return track;
	}

}
