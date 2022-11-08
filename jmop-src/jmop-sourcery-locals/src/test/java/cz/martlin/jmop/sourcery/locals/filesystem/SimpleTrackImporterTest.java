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

/**
 * The test of {@link SimpleTrackImporter}.
 * 
 * @author martin
 *
 */
class SimpleTrackImporterTest {

	@Test
	void test() throws IOException {
		BaseTracksFromFileImporter importer = new SimpleTrackImporter();

		TestingTrackFilesCreator creator = new TestingTrackFilesCreator();
		File file = creator.prepare(TrackFileFormat.MP3);

		List<TrackData> datas = importer.importTracks(file);
		System.out.println(datas);

		TrackData data = datas.get(0);

		assertEquals(file.getName(), data.getTitle());
		assertEquals(TestingTrackFilesCreator.SAMPLE_FILE_DURATION, data.getDuration());
		assertEquals(file, data.getTrackFile());
		assertEquals(null, data.getDescription());
		assertEquals(file.toURI(), data.getURI());
	}

}
