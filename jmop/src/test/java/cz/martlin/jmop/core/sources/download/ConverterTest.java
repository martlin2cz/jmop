package cz.martlin.jmop.core.sources.download;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.local.DefaultLocalSource;
import cz.martlin.jmop.core.sources.local.PlaylistLoader;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

public class ConverterTest {
	public static void main(String[] args) throws IOException {
		final String id = "3V7EugoweM4";
		final String title = "Something";
		final String description = "Something interresting";
		final Duration duration = DurationUtilities.createDuration(1, 2, 3);
		final String bundleName = "testing-tracks";
		final File rootDir = File.createTempFile("xxx", "xxx").getParentFile(); // hehe
		final SourceKind source = SourceKind.YOUTUBE;

		BaseFilesNamer namer = new DefaultFilesNamer();
		PlaylistLoader loader = null;
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(rootDir, namer, loader);
		Bundle bundle = new Bundle(source, bundleName);

		BaseLocalSource local = new DefaultLocalSource(fileSystem);
		
		TrackFileFormat inputFormat = TrackFileFormat.OPUS;
		TrackFileFormat outputFormat = TrackFileFormat.MP3;
		boolean deleteOriginal = false;
		
		ProgressListener listener = new SimpleLoggingListener(System.out);
		
		BaseSourceConverter converter = new FFMPEGConverter(local, inputFormat, outputFormat, deleteOriginal );
		//BaseSourceConverter converter = new NoopConverter();
		converter.specifyListener(listener);

		
		Track track = bundle.createTrack(id, title, description, duration);

		try {
			boolean success = converter.convert(track);
			System.err.println("Success? " + success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

