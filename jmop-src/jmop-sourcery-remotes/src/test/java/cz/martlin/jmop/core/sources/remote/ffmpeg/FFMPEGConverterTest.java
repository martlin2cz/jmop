package cz.martlin.jmop.core.sources.remote.ffmpeg;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Metadata;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.testing.TestingTrackFileAccessor;
import cz.martlin.jmop.core.sources.locals.testing.TestingTracksSource;
import cz.martlin.jmop.core.sources.remote.ConversionReason;

public class FFMPEGConverterTest {

	@Test
	public void test() throws Exception {
		BaseTracksLocalSource local = createTracksLocal();
		FFMPEGConverter converter = new FFMPEGConverter(local);

		Bundle bundle = new Bundle(SourceKind.YOUTUBE, "the-bundle", Metadata.createNew());
		Track track = bundle.createTrack("123456", "da-track", "...", DurationUtilities.createDuration(0, 0, 52), Metadata.createNew());

		ConversionReason reason = ConversionReason.PREPARE_TO_PLAY;
		TrackFileFormat fromFormat = TrackFileFormat.OPUS;
		TrackFileLocation fromLocation = TrackFileLocation.TEMP;
		TrackFileFormat toFormat = TrackFileFormat.WAV;
		TrackFileLocation toLocation = TrackFileLocation.TEMP;

		prepareTesingTrackFile(local, track, fromLocation);
		File fromFile = local.fileOfTrack(track, fromLocation, fromFormat);
		assumeTrue(fromFile.exists());

		BaseLongOperation<Track, Track> operation = converter.convert(track, fromLocation, fromFormat, toLocation,
				toFormat, reason);

		BaseProgressListener listener = new PrintingListener(System.out);
		Track result = operation.run(listener);

		File toFile = local.fileOfTrack(track, toLocation, toFormat);
		assertTrue(toFile.exists());

		System.out.println("The operation returned: " + result);
		assertNotNull(result);
	}

	private void prepareTesingTrackFile(BaseTracksLocalSource tracks, Track track, TrackFileLocation location)
			, IOException {

		TrackFileFormat format = TrackFileFormat.MP3;
		File file = tracks.fileOfTrack(track, location, format);

		TestingTrackFileAccessor.create(file, format);
	}

	private BaseTracksLocalSource createTracksLocal() {
		return new TestingTracksSource();
	}
}
