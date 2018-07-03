package cz.martlin.jmop.core.sources.download;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;

import org.junit.Test;

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
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public class DownloaderTest {

	private static final double EPSILON = 0.1;

	@Test
	public void testParsePercent() {
		String line1 = "[youtube] 1eQCzql3dzI: Downloading webpage";
		String line2 = "[download]   0.0% of 60.02MiB at Unknown speed ETA Unknown ETA";
		String line3 = "[download]  42.9% of 60.02MiB at  9.07MiB/s ETA 00:05";
		String line4 = "[download] unknown";

		assertEquals(null, YoutubeDlDownloader.tryToParsePercentFromLine(line1));
		assertEquals(null, YoutubeDlDownloader.tryToParsePercentFromLine(line4));

		assertEquals(0.0, YoutubeDlDownloader.tryToParsePercentFromLine(line2).doubleValue(), EPSILON);
		assertEquals(42.9, YoutubeDlDownloader.tryToParsePercentFromLine(line3).doubleValue(), EPSILON);
	}

	@Test
	public void testRemoveSuffix() {
		assertEquals("foo", YoutubeDlDownloader.removeSuffix("foo.xxx"));
		assertEquals("foo.bar", YoutubeDlDownloader.removeSuffix("foo.bar.42"));
	}
	
	public static void main(String[] args) throws IOException {
		final String id = "3V7EugoweM4";
		final String title = "Something";
		final String description = "Something interresting";
		final String bundleName = "tracks";
		final File rootDir = File.createTempFile("xxx", "xxx").getParentFile(); // hehe
		final SourceKind source = SourceKind.YOUTUBE;

		AbstractRemoteSource remote = new YoutubeSource();

		BaseFilesNamer namer = new DefaultFilesNamer(rootDir);
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(namer);
		Bundle bundle = new Bundle(source, bundleName);

		BaseLocalSource local = new DefaultLocalSource(fileSystem, bundle);
		Sources sources = new Sources(local, remote);
		ProgressListener listener = new SimpleLoggingListener(System.out);
		BaseSourceDownloader downloader = new YoutubeDlDownloader(sources, listener );

		TrackIdentifier identifier = new TrackIdentifier(source, id);
		Track track = new Track(identifier, title, description);

		try {
			File downloaded = downloader.download(track);
			System.err.println("Downloaded to: " + downloaded.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
