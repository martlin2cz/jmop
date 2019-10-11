package cz.martlin.jmop.core.sources.remote.ffmpeg;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.common.io.Files;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.XXX_BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.funky.FunkyFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.funky.FunkyFilesNamer;
import cz.martlin.jmop.core.sources.locals.funky.FunkyLocalSource;
import cz.martlin.jmop.core.sources.locals.funky.FunkyPlaylistLoader;
import cz.martlin.jmop.core.sources.locals.testing.TestingTrackFileAccessor;
import cz.martlin.jmop.core.sources.remote.ConversionReason;
import cz.martlin.jmop.core.sources.remote.empty.TestingDownloader;

public class FFMPEGConverterTest {

	@Test
	public void test() throws Exception {
		BaseConfiguration config = new ConstantConfiguration();
		XXX_BaseLocalSource local = createLocal(config);
		FFMPEGConverter converter = new FFMPEGConverter(local);

		Bundle bundle = new Bundle(SourceKind.YOUTUBE, "the-bundle");
		Track track = bundle.createTrack("123456", "da-track", "...", DurationUtilities.createDuration(0, 0, 52));

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

	private void prepareTesingTrackFile(XXX_BaseLocalSource local, Track track, TrackFileLocation location)
			throws JMOPSourceException, IOException {

		TrackFileFormat format = TestingTrackFileAccessor.TESTING_FILE_FORMAT;
		File file = local.fileOfTrack(track, location, format);

		TestingDownloader.copyTestingFileTo(file);
	}

	private XXX_BaseLocalSource createLocal(BaseConfiguration config) throws IOException {
		XXX_BaseFilesNamer namer = new FunkyFilesNamer();
		File root = Files.createTempDir();
		AbstractPlaylistLoader loader = new FunkyPlaylistLoader();
		AbstractFileSystemAccessor fileSystem = new FunkyFileSystemAccessor(root, namer, loader);
		XXX_BaseLocalSource local = new FunkyLocalSource(config, fileSystem);
		return local;
	}
}
