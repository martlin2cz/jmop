package cz.martlin.jmop.core.sources.remote.youtubedl;

import static org.junit.Assert.assertEquals;
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
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
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
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.core.sources.remote.youtube.YoutubeQuerier;

public class YoutubeDLDownloaderTest {

	@Test
	public void test() throws Exception {
		BaseConfiguration config = new ConstantConfiguration();
		InternetConnectionStatus connection = new InternetConnectionStatus(config);

		BaseRemoteSourceQuerier querier = new YoutubeQuerier(config, connection);

		XXX_BaseLocalSource local = createLocal(config);
		YoutubeDLDownloader downloader = new YoutubeDLDownloader(querier, local);

		Bundle bundle = new Bundle(SourceKind.YOUTUBE, "bundle");
		String identifier = "PvnlpPWAcZc";
		Track track = bundle.createTrack(identifier, "some track", "some desc",
				DurationUtilities.createDuration(0, 4, 2));

		TrackFileLocation location = TrackFileLocation.TEMP;
		TrackFileFormat format = downloader.downloadFormat();
		File file = local.fileOfTrack(track, location, format );
		
		if (file.exists()) {
			assumeTrue(file.delete());
		}
		
		BaseLongOperation<Track, Track> operation = downloader.download(track, location);

		BaseProgressListener listener = new PrintingListener(System.out);
		Track result = operation.run(listener);

		System.out.println("The result of the operation is " + result);
		assertEquals(track, result);
		
		System.out.println("The downloaded file is: " + file.getAbsolutePath());
		assertTrue(file.exists());
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
