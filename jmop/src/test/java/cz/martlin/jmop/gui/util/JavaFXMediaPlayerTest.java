package cz.martlin.jmop.gui.util;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.player.AbstractPlayer;
import cz.martlin.jmop.core.player.AplayPlayer;
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
import cz.martlin.jmop.core.sources.local.PlaylistLoader;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.misc.TestingTools;

public class JavaFXMediaPlayerTest {

	@Test
	public void test() throws Exception {
		final Thread main = Thread.currentThread();
		
		TestingTools.runAsJavaFX(() -> {
			try {
				BaseLocalSource local = prepareLocal();
				Track track = prepareTrack();

				TestingDownloader downloader = new TestingDownloader(local);
				downloader.download(track);

				final TrackFileFormat downloadFormat = TestingDownloader.DOWNLOAD_FORMAT;
				final TrackFileFormat outputFormat = TrackFileFormat.MP3;
				final ProgressListener listener = (p) -> {
				};

				FFMPEGConverter converter = new FFMPEGConverter(local, downloadFormat, outputFormat, listener);
				converter.convert(track);

				JavaFXMediaPlayer player = new JavaFXMediaPlayer(local);
//				AbstractPlayer player = new AplayPlayer(local);
				player.play(track);
				
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
		Track track = new Track(bundle, identifier, title, description);
		return track;
	}

	private BaseLocalSource prepareLocal() {
		PlaylistLoader loader = new DefaultPlaylistLoader();
		BaseFilesNamer namer = new DefaultFilesNamer();
		File root = new File("/tmp/jmop-gui/");
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(fileSystem);
		return local;
	}

}
