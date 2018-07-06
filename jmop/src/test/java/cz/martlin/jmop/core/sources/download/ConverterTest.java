package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
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
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;

public class ConverterTest {
	public static void main(String[] args) throws IOException {
		final String id = "3V7EugoweM4";
		final String title = "Something";
		final String description = "Something interresting";
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
		
		TrackFileFormat inputFormat = TrackFileFormat.OPUS;
		TrackFileFormat outputFormat = TrackFileFormat.MP3;
		
		ProgressListener listener = new SimpleLoggingListener(System.out);
		BaseSourceConverter converter = new FFMPEGConverter(sources, inputFormat, outputFormat, listener);
		//BaseSourceConverter converter = new NoopConverter();

		
		Track track = new Track(bundle, id, title, description);

		try {
			boolean success = converter.convert(track);
			System.err.println("Success? " + success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

