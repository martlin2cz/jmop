package cz.martlin.jmop.sourcery.locals.filesystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.testing.resources.TestingTrackFilesCreator;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.sourcery.local.BaseTracksFromFileImporter;
import javafx.util.Duration;

/**
 * The test of {@link MP3FileMetadataBasedImporter}.
 * 
 * @author martin
 *
 */
class MP3FileMetadataBasedImporterTest {

	@Test
	void test() throws IOException {
		BaseTracksFromFileImporter importer = new MP3FileMetadataBasedImporter(true);

		TestingTrackFilesCreator creator = new TestingTrackFilesCreator();
		File file = creator.prepare(TrackFileFormat.MP3);

		List<TrackData> datas = importer.importTracks(file);
		System.out.println(datas);

		TrackData data = datas.get(0);

		assertEquals("The Sample", data.getTitle());
		assertEquals(new Duration(12239.0), data.getDuration());
		assertEquals(file, data.getTrackFile());
		assertEquals("The sample track.", data.getDescription());
		assertEquals(file.toURI(), data.getURI());
	}

}
