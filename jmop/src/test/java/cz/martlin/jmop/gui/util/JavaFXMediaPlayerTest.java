package cz.martlin.jmop.gui.util;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNoException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import cz.martlin.jmop.core.config.Configuration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.download.FFMPEGConverter;
import cz.martlin.jmop.core.sources.download.TestingDownloader;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.local.DefaultLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultPlaylistLoader;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.PrimitiveLocator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.misc.TestingTools;
import javafx.util.Duration;

public class JavaFXMediaPlayerTest {

	@Test
	public void test() throws Exception {
		final Thread main = Thread.currentThread();

		TestingTools.runAsJavaFX(() -> {
			try {
				BaseLocalSource local = prepareLocal();
				Track track = prepareTrack();
				TrackFileLocation downloadLocation = TrackFileLocation.TEMP;
				TrackFileLocation playLocation = TrackFileLocation.SAVE;
				TrackFileFormat downloadFormat = TrackFileFormat.OPUS;
				TrackFileFormat playFormat = TrackFileFormat.MP3;

				TestingDownloader downloader = new TestingDownloader(local, downloadFormat);
				downloader.download(track, downloadLocation);

				FFMPEGConverter converter = new FFMPEGConverter(local);
				converter.convert(track, downloadLocation, downloadFormat, playLocation, playFormat);

				AbstractTrackFileLocator locator = new PrimitiveLocator();
				JavaFXMediaPlayer player = new JavaFXMediaPlayer(local, locator);
				// AbstractPlayer player = new AplayPlayer(local);

				// TrackPlayedHandler handler = null;
				// player.setHandler(handler);

				player.startPlayling(track);
			} catch (IOException e) {
				assumeNoException(e);
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.toString());
			} finally {
				main.interrupt();
			}
		});

		try {
			TimeUnit.MINUTES.sleep(10);
		} catch (InterruptedException eIgnore) {
		}
	}

	private Track prepareTrack() {
		SourceKind kind = SourceKind.YOUTUBE;
		String name = "testing-kind";
		Bundle bundle = new Bundle(kind, name);
		// TODO: local.createBundle, but just once!
		// try {
		// prepareLocal().createBundle(bundle);
		// } catch (JMOPSourceException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		String identifier = "xxx42yyy";
		String description = "Lorem Ipsum ...";
		String title = "test-track";
		Duration duration = DurationUtilities.createDuration(0, 1, 1);
		Track track = bundle.createTrack(identifier, title, description, duration);
		return track;
	}

	private BaseLocalSource prepareLocal() throws IOException {
		Configuration config = new Configuration();
		AbstractPlaylistLoader loader = new DefaultPlaylistLoader();
		BaseFilesNamer namer = new DefaultFilesNamer();
		File root = new File("/tmp/jmop-gui/");
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(config, fileSystem);
		return local;
	}

}
