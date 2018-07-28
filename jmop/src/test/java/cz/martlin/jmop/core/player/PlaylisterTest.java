package cz.martlin.jmop.core.player;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeTrue;

import java.io.File;

import org.junit.Test;

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
import cz.martlin.jmop.core.sources.local.PlaylistLoader;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import cz.martlin.jmop.core.wrappers.ToPlaylistAppendingHandler;
import cz.martlin.jmop.misc.TestingTools;
import javafx.util.Duration;

public class PlaylisterTest {

	@Test
	public void test() {
		//TestingTools.runAsJavaFX(() -> { TODO } );
		
		JMOPPlaylister playlister = createPlaylister();

		playlister.play();

		playlister.toNext();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private JMOPPlaylister createPlaylister() {
		File root = createRoot();

		AbstractRemoteSource remote = new YoutubeSource();

		BaseFilesNamer namer = new DefaultFilesNamer();
		PlaylistLoader loader = new DefaultPlaylistLoader();
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(fileSystem);
		
		ProgressListener listener = new SimpleLoggingListener(System.out);
		BaseSourceDownloader downloader = new YoutubeDlDownloader(local, remote);
		downloader.specifyListener(listener);
		
		TrackFileFormat inputFormat = YoutubeDlDownloader.DOWNLOAD_FILE_FORMAT;
		TrackFileFormat outputFormat = TrackFileFormat.MP3;
		
		boolean deleteOriginal = false;
		BaseSourceConverter converter = new FFMPEGConverter(local, inputFormat, outputFormat,  deleteOriginal );
		converter.specifyListener(listener);

		Bundle bundle = createTestingBundle(local);

		Track track = createInitialTrack(local, bundle);
		BetterPlaylistRuntime playlist = new BetterPlaylistRuntime(track);
		AbstractPlayer player = new TestingPlayer();

		InternetConnectionStatus connection = new InternetConnectionStatus();
		GuiDescriptor gui = null;
		AutomaticSavesPerformer saver = new AutomaticSavesPerformer(local);
		TrackPreparer preparer = new TrackPreparer(remote, local, converter, downloader, saver , gui );
		
		JMOPPlaylister playlister = new JMOPPlaylister(player, preparer , connection,saver);
		TrackPlayedHandler handler = new ToPlaylistAppendingHandler(playlister);
		playlister.setPlaylist(playlist);

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

	private Track createInitialTrack(BaseLocalSource local, Bundle bundle) {
		final String trackName = "Sample house music";
		final String trackId = "WYp9Eo9T3BA";
		final String trackDesc = "This is just some somple house music originaly by Shingo Nakamura";
		final Duration duration = DurationUtilities.createDuration(1, 12, 13);
		
		Track track = bundle.createTrack(trackId, trackName, trackDesc, duration);

		TestingDownloader downloader = new TestingDownloader(local);
		try {
			assumeTrue("Cannot prepare initial track", downloader.download(track));
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
