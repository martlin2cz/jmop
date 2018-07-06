package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.IOException;

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
import cz.martlin.jmop.core.sources.local.PlaylistLoader;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public class DownloaderTest {

	public static void main(String[] args) throws IOException {
		final String id = "TAOQWSmkofA";
		final String title = "sample";
		final String description = "Sample sound track";
		final String bundleName = "testing-tracks";
		final File rootDir = File.createTempFile("xxx", "xxx").getParentFile(); // hehe
		final SourceKind source = SourceKind.YOUTUBE;

		AbstractRemoteSource remote = new YoutubeSource();

		BaseFilesNamer namer = new DefaultFilesNamer();
		PlaylistLoader loader = null;
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(rootDir, namer, loader);
		Bundle bundle = new Bundle(source, bundleName);

		BaseLocalSource local = new DefaultLocalSource(fileSystem);
		Sources sources = new Sources(local, remote);
		ProgressListener listener = new SimpleLoggingListener(System.out);
		
		BaseSourceDownloader downloader = new YoutubeDlDownloader(sources, listener);
		//BaseSourceDownloader downloader = new TestingDownloader(sources);

		TrackIdentifier identifier = new TrackIdentifier(source, id);
		Track track = new Track(identifier, title, description);

		try {
			boolean success = downloader.download(track);
			System.err.println("Success? " + success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
