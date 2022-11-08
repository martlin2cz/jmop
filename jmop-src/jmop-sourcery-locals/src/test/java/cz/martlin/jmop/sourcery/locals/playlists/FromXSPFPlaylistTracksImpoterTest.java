package cz.martlin.jmop.sourcery.locals.playlists;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.sourcery.local.BaseTracksFromFileImporter;

/**
 * Just the test of {@link FromXSPFPlaylistTracksImpoter}.
 * 
 * @author martin
 *
 */
class FromXSPFPlaylistTracksImpoterTest {

	@Test
	void test() throws IOException {
		BaseTracksFromFileImporter importer = new FromXSPFPlaylistTracksImpoter();

		SamplePlaylistFileHandler samplePlaylistFileHandler = new SamplePlaylistFileHandler();
		File file = samplePlaylistFileHandler.getSamplePlaylistFile();

		List<TrackData> tracks = importer.importTracks(file);
		System.out.println(tracks);

		assertEquals(3, tracks.size());

		TrackData oneMoreTime = tracks.get(0);
		assertEquals("One More Time", oneMoreTime.getTitle());
		assertEquals(DurationUtilities.createDuration(0, 5, 20), oneMoreTime.getDuration());
		assertEquals("Daft Punk - One More Time", oneMoreTime.getDescription());
		assertEquals(null, oneMoreTime.getTrackFile());
		assertEquals(null, oneMoreTime.getURI());

		TrackData verdisQue = tracks.get(1);
		assertEquals("Veridis Quo", verdisQue.getTitle());
		assertEquals(DurationUtilities.createDuration(0, 5, 44), verdisQue.getDuration());
		assertEquals("Daft Punk - Veridis Quo", verdisQue.getDescription());
		assertEquals(new File("/daftpunk.mp3"), verdisQue.getTrackFile());
		assertEquals(URI.create("https://404.net/#daft-punk-doesnt-exist-anymore"), verdisQue.getURI());

		TrackData aerodynamic = tracks.get(2);
		assertEquals("Aerodynamic", aerodynamic.getTitle());

	}

}
