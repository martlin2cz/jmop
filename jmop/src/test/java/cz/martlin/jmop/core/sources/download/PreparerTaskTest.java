package cz.martlin.jmop.core.sources.download;

import java.io.File;

import cz.martlin.jmop.core.config.Configuration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.player.TestingPlayer;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.local.DefaultLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.PrimitiveLocator;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PreparerTaskTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final String id = "qUXEFj0t7Ek";
		final String title = "Lorem-ispum";
		final String description = "lorem ipsum dolor sit amet";
		final Duration duration = DurationUtilities.createDuration(4, 5, 6);
		final String bundleName = "another-testing-tracks";
		final File rootDir = File.createTempFile("xxx", "xxx").getParentFile(); // hehe
		final SourceKind source = SourceKind.YOUTUBE;
		final TrackFileFormat playerFormat = TrackFileFormat.OPUS;
		
		Configuration config = new Configuration();
		InternetConnectionStatus connection = new InternetConnectionStatus(config);
		AbstractRemoteSource remote = new YoutubeSource(connection);

		BaseFilesNamer namer = new DefaultFilesNamer();
		AbstractPlaylistLoader loader = null;
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(rootDir, namer, loader);
		Bundle bundle = new Bundle(source, bundleName);

		BaseLocalSource local = new DefaultLocalSource(config, fileSystem);
		
		ProgressListener listener = new SimpleLoggingListener(System.out);
		
		BaseSourceDownloader downloader = new YoutubeDlDownloader(connection, local, remote);
		//BaseSourceDownloader downloader = new TestingDownloader(local, downloadFormat);
		
		Track track = bundle.createTrack(id, title, description, duration);


		BaseSourceConverter converter = new FFMPEGConverter(local);
		//BaseSourceConverter converter = new NoopConverter();
		converter.specifyListener(listener);

		
		BasePlayer player = new TestingPlayer(playerFormat);

		AbstractTrackFileLocator locator = new PrimitiveLocator();
		PreparerTask task = new PreparerTask(config, locator, local, downloader, converter, player, track);
		
		task.messageProperty().addListener((observable, oldVal, newVal) -> {
			System.out.println("# " + newVal);
		});
		task.setOnSucceeded((e) -> {
			System.out.print("Done!");
			Platform.exit();
		});

		task.run();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
