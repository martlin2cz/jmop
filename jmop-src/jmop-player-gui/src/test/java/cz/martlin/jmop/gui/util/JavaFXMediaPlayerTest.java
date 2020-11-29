package cz.martlin.jmop.gui.util;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNoException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.XXX_ProgressListener;
import cz.martlin.jmop.core.player.JavaFXMediaPlayer;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.local.base.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.base.location.BaseTrackFileLocator;
import cz.martlin.jmop.core.sources.local.base.location.PrimitiveLocator;
import cz.martlin.jmop.core.sources.local.bases.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.bases.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.local.bases.DefaultLocalSource;
import cz.martlin.jmop.core.sources.local.bases.DefaultPlaylistLoader;
import cz.martlin.jmop.core.sources.remotes.XXX_FFMPEGConverter;
import cz.martlin.jmop.core.sources.remotes.TestingDownloader;
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
				XXX_ProgressListener listener = null; //FIXME ?
				
				TestingDownloader downloader = new TestingDownloader(local, downloadFormat);
				downloader.download(track, downloadLocation, listener);

				XXX_FFMPEGConverter converter = new XXX_FFMPEGConverter(local);
				converter.convert(track, downloadLocation, downloadFormat, playLocation, playFormat, listener);

				BaseTrackFileLocator locator = new PrimitiveLocator();
				JavaFXMediaPlayer player = new JavaFXMediaPlayer(local, locator);
				// AbstractPlayer player = new AplayPlayer(local);

				// TrackPlayedHandler handler = null;
				// player.setHandler(handler);

				player.startPlaying(track);
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
		String name = "testing-kind"; //$NON-NLS-1$
		Bundle bundle = new Bundle(kind, name);
		// TODO: local.createBundle, but just once!
		// try {
		// prepareLocal().createBundle(bundle);
		// } catch (JMOPSourceException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		String identifier = "xxx42yyy"; //$NON-NLS-1$
		String description = "Lorem Ipsum ..."; //$NON-NLS-1$
		String title = "test-track"; //$NON-NLS-1$
		Duration duration = DurationUtilities.createDuration(0, 1, 1);
		Track track = bundle.createTrack(identifier, title, description, duration);
		return track;
	}

	private BaseLocalSource prepareLocal() throws IOException {
		ConstantConfiguration config = new ConstantConfiguration();
		AbstractPlaylistLoader loader = new DefaultPlaylistLoader();
		BaseFilesNamer namer = new DefaultFilesNamer();
		File root = new File("/tmp/jmop-gui/"); //$NON-NLS-1$
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(config, fileSystem);
		return local;
	}

}
