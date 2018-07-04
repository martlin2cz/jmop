package cz.martlin.jmop.core.sources.download;

import java.io.File;

import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.Sources;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.local.DefaultLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class DownloaderTaskTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final String id = "qUXEFj0t7Ek";
		final String title = "Lorem-ispum";
		final String description = "lorem ipsum dolor sit amet";
		final String bundleName = "another-testing-tracks";
		final File rootDir = File.createTempFile("xxx", "xxx").getParentFile(); // hehe
		final SourceKind source = SourceKind.YOUTUBE;

		AbstractRemoteSource remote = new YoutubeSource();

		BaseFilesNamer namer = new DefaultFilesNamer(rootDir);
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(namer);
		Bundle bundle = new Bundle(source, bundleName);

		BaseLocalSource local = new DefaultLocalSource(fileSystem, bundle);
		Sources sources = new Sources(local, remote);
		ProgressListener listener = new SimpleLoggingListener(System.out);
		
		//BaseSourceDownloader downloader = new YoutubeDlDownloader(sources, listener);
		BaseSourceDownloader downloader = new TestingDownloader(sources);
		
		TrackIdentifier identifier = new TrackIdentifier(source, id);
		Track track = new Track(identifier, title, description);

		TrackFileFormat inputFormat = TrackFileFormat.OPUS;
		TrackFileFormat outputFormat = TrackFileFormat.MP3;

		BaseSourceConverter converter = new FFMPEGConverter(sources, inputFormat, outputFormat, listener);
		//BaseSourceConverter converter = new NoopConverter();
		
		DownloaderTask task = new DownloaderTask(downloader, converter, track);
		
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
