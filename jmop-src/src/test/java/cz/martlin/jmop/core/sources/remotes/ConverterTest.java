package cz.martlin.jmop.core.sources.remotes;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.XXX_ProgressListener;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.locals.DefaultLocalSource;
import cz.martlin.jmop.core.sources.remote.XXX_BaseSourceConverter;
import javafx.util.Duration;

public class ConverterTest {
	public static void main(String[] args) throws IOException {
		final String id = "3V7EugoweM4"; //$NON-NLS-1$
		final String title = "Something"; //$NON-NLS-1$
		final String description = "Something interresting"; //$NON-NLS-1$
		final Duration duration = DurationUtilities.createDuration(1, 2, 3);
		final String bundleName = "testing-tracks"; //$NON-NLS-1$
		final File rootDir = File.createTempFile("xxx", "xxx").getParentFile(); // hehe //$NON-NLS-1$ //$NON-NLS-2$
		final SourceKind source = SourceKind.YOUTUBE;

		ConstantConfiguration config = new ConstantConfiguration();
		BaseFilesNamer namer = new DefaultFilesNamer();
		AbstractPlaylistLoader loader = null;
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(rootDir, namer, loader);
		Bundle bundle = new Bundle(source, bundleName);

		BaseLocalSource local = new DefaultLocalSource(config, fileSystem);

		TrackFileLocation inputLocation = TrackFileLocation.TEMP;
		TrackFileLocation outputLocation = TrackFileLocation.SAVE;
		TrackFileFormat inputFormat = TrackFileFormat.OPUS;
		TrackFileFormat outputFormat = TrackFileFormat.MP3;

		XXX_ProgressListener listener = new SimpleLoggingListener(System.out);

		XXX_BaseSourceConverter converter = new FFMPEGConverter(local);
		// BaseSourceConverter converter = new NoopConverter();
		//converter.specifyListener(listener);

		Track track = bundle.createTrack(id, title, description, duration);

		try {

			boolean success = converter.convert(track, inputLocation, inputFormat, outputLocation, outputFormat, listener);
			System.err.println("Success? " + success); //$NON-NLS-1$
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
