package cz.martlin.jmop.core.sources.remotes;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.locals.DefaultLocalSource;
import cz.martlin.jmop.core.sources.remote.XXX_AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceDownloader;
import javafx.util.Duration;

public class DownloaderTest {

	public static void main(String[] args) throws IOException {
		final String id = "TAOQWSmkofA"; //$NON-NLS-1$
		final String title = "sample"; //$NON-NLS-1$
		final String description = "Sample sound track"; //$NON-NLS-1$
		final String bundleName = "testing-tracks"; //$NON-NLS-1$
		final Duration duration = DurationUtilities.createDuration(0, 0, 9);
		final File rootDir = File.createTempFile("xxx", "xxx").getParentFile(); // hehe //$NON-NLS-1$ //$NON-NLS-2$
		final SourceKind source = SourceKind.YOUTUBE;

		ConstantConfiguration config = new ConstantConfiguration();
		InternetConnectionStatus connection = new InternetConnectionStatus(config);
		XXX_AbstractRemoteSource remote = new YoutubeQuerier(connection);

		BaseFilesNamer namer = new DefaultFilesNamer();
		AbstractPlaylistLoader loader = null;
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(rootDir, namer, loader);
		Bundle bundle = new Bundle(source, bundleName);

		BaseLocalSource local = new DefaultLocalSource(config, fileSystem);
		ProgressListener listener = new SimpleLoggingListener(System.out);

		XXX_BaseSourceDownloader downloader = new YoutubeDlDownloader(connection, local, remote);
		// BaseSourceDownloader downloader = new TestingDownloader(sources);
		//downloader.specifyListener(listener);

		Track track = bundle.createTrack(id, title, description, duration);
		TrackFileLocation location = TrackFileLocation.TEMP;
		try {

			boolean success = downloader.download(track, location, listener);
			System.err.println("Success? " + success); //$NON-NLS-1$
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
