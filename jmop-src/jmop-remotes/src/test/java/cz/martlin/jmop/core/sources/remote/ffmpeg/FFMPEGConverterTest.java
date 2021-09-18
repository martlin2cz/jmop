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
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.locals.DefaultLocalSource;
import cz.martlin.jmop.core.sources.locals.DefaultPlaylistLoader;
import cz.martlin.jmop.core.sources.locals.TestingTrackFileAccessor;
import cz.martlin.jmop.core.sources.remote.ConversionReason;
import cz.martlin.jmop.core.sources.remote.empty.TestingDownloader;

public class FFMPEGConverterTest {

	@Test
	public void test() throws Exception {
		BaseConfiguration config = new ConstantConfiguration();
		BaseLocalSource local = createLocal(config);
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

	private void prepareTesingTrackFile(BaseLocalSource local, Track track, TrackFileLocation location)
			throws JMOPSourceException, IOException {

		TrackFileFormat format = TestingTrackFileAccessor.TESTING_FILE_FORMAT;
		File file = local.fileOfTrack(track, location, format);

		TestingDownloader.copyTestingFileTo(file);
	}

	private BaseLocalSource createLocal(BaseConfiguration config) throws IOException {
		BaseFilesNamer namer = new DefaultFilesNamer();
		File root = Files.createTempDir();
		AbstractPlaylistLoader loader = new DefaultPlaylistLoader();
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(config, fileSystem);
		return local;
	}
}