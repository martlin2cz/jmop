package cz.martlin.jmop.core.player;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cz.martlin.jmop.core.config.DefaultConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.AutomaticSavesPerformer;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.FFMPEGConverter;
import cz.martlin.jmop.core.sources.download.SimpleLoggingListener;
import cz.martlin.jmop.core.sources.download.TestingDownloader;
import cz.martlin.jmop.core.sources.download.YoutubeDlDownloader;
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
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import cz.martlin.jmop.core.wrappers.ToPlaylistAppendingHandler;
import cz.martlin.jmop.misc.TestingTools;
import javafx.util.Duration;

public class PlaylisterTest {

	@Test
	public void test()  {
		try {
		//TestingTools.runAsJavaFX(() -> { TODO } );
		
		JMOPPlaylisterWithGui playlister = createPlaylister();

		playlister.play();

		playlister.toNext();
		} catch (IOException e) {
			assumeNoException(e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private JMOPPlaylisterWithGui createPlaylister() throws IOException {
		File root = createRoot();
		DefaultConfiguration config = new DefaultConfiguration();
		InternetConnectionStatus connection = new InternetConnectionStatus(config);
		AbstractRemoteSource remote = new YoutubeSource(connection);

		BaseFilesNamer namer = new DefaultFilesNamer();
		AbstractPlaylistLoader loader = new DefaultPlaylistLoader();
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(config, fileSystem);
		
		ProgressListener listener = new SimpleLoggingListener(System.out);

		
		BaseSourceDownloader downloader = new YoutubeDlDownloader(connection , local, remote);
		downloader.specifyListener(listener);
		
		BaseSourceConverter converter = new FFMPEGConverter(local);
		converter.specifyListener(listener);

		Bundle bundle = createTestingBundle(local);

		Track track = createInitialTrack(local, bundle, downloader.formatOfDownload());
		BetterPlaylistRuntime playlist = new BetterPlaylistRuntime(track);
		BasePlayer player = new TestingPlayer(TrackFileFormat.WAV);

		GuiDescriptor gui = null;
		AutomaticSavesPerformer saver = new AutomaticSavesPerformer(config, local);
		
		AbstractTrackFileLocator locator = new PrimitiveLocator();
		XXX_TrackPreparer preparer = new XXX_TrackPreparer(config, remote, local, locator, converter, downloader, player, saver,
				gui);

		JMOPPlaylisterWithGui playlister = new JMOPPlaylisterWithGui(player, preparer , connection,saver);
		TrackPlayedHandler handler = new ToPlaylistAppendingHandler(playlister);
		playlister.setPlaylist(playlist);
//		player.setHandler(handler);

		System.out.println("Playlister ready!");

		return playlister;
	}

	private Bundle createTestingBundle(BaseLocalSource local) {
		final SourceKind kind = SourceKind.YOUTUBE;
		final String bundleName = "house-music";
		Bundle bundle = new Bundle(kind, bundleName);

		try {
			local.createBundle(bundle);
		} catch (JMOPSourceException e) {
			assumeNoException("Cannot create bundle", e);
		}

		System.out.println("Testing bundle prepared");

		return bundle;

	}

	private Track createInitialTrack(BaseLocalSource local, Bundle bundle, TrackFileFormat downloadFormat) {
		final String trackName = "Sample house music";
		final String trackId = "WYp9Eo9T3BA";
		final String trackDesc = "This is just some somple house music originaly by Shingo Nakamura";
		final Duration duration = DurationUtilities.createDuration(1, 12, 13);
		
		Track track = bundle.createTrack(trackId, trackName, trackDesc, duration);

		TestingDownloader downloader = new TestingDownloader(local, downloadFormat);
		TrackFileLocation location = TrackFileLocation.TEMP;
		try {
			assumeTrue("Cannot prepare initial track", downloader.download(track, location));
		} catch (Exception e) {
			assumeNoException("Cannot prepare initial track", e);
		}

		System.out.println("Ready to play track " + track);

		return track;
	}

	private File createRoot() {
		final String testDirName = "jmop-playlister-test";

		File dir = TestingTools.testingTempDirectory(testDirName);

		assumeTrue(TestingTools.delete(dir));

		dir.mkdirs();

		assumeTrue("Test directory does not exist", dir.isDirectory());
		System.out.println("Working with " + dir.getAbsolutePath());

		return dir;
	}

}
