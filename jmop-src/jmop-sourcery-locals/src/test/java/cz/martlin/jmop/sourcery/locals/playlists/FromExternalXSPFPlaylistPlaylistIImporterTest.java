package cz.martlin.jmop.sourcery.locals.playlists;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.sourcery.local.BasePlaylistImporter;
import cz.martlin.jmop.sourcery.local.BasePlaylistImporter.PlaylistData;

/**
 * Just the test of {@link FromExternalXSPFPlaylistPlaylistIImporter}
 * 
 * @author martin
 *
 */
class FromExternalXSPFPlaylistPlaylistIImporterTest {

	@Test
	void test() throws IOException {
		BasePlaylistImporter importer = new FromExternalXSPFPlaylistPlaylistIImporter();

		SamplePlaylistFileHandler samplePlaylistFileHandler = new SamplePlaylistFileHandler();
		File file = samplePlaylistFileHandler.getSamplePlaylistFile();

		PlaylistData data = importer.load(file, TrackFileCreationWay.JUST_SET);
		System.out.println(data);
		
		assertEquals("Discovery", data.getName());
		assertEquals(3, data.getTracks().size());

		TrackData oneMoreTime = data.getTracks().get(0);
		assertEquals("One More Time", oneMoreTime.getTitle());
		assertEquals(DurationUtilities.createDuration(0, 5, 20), oneMoreTime.getDuration());
		assertEquals("Daft Punk - One More Time", oneMoreTime.getDescription());
		assertEquals(null, oneMoreTime.getTrackFile());
		assertEquals(null, oneMoreTime.getURI());
		
		TrackData verdisQue = data.getTracks().get(1);
		assertEquals("Veridis Quo", verdisQue.getTitle());
		assertEquals(DurationUtilities.createDuration(0, 5, 44), verdisQue.getDuration());
		assertEquals("Daft Punk - Veridis Quo", verdisQue.getDescription());
		assertEquals(new File("/daftpunk.mp3"), verdisQue.getTrackFile());
		assertEquals(URI.create("https://404.net/#daft-punk-doesnt-exist-anymore"), verdisQue.getURI());

	}

}
