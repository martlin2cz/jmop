package cz.martlin.jmop.core.sources.locals.electronic.xspf;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.TestingDataCreator;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.TestingPrinter;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.local.misc.flu.FormatsLocationsUtility;
import cz.martlin.jmop.core.sources.local.misc.flu.TestingFormatsLocationsUtility;
import cz.martlin.jmop.core.sources.locals.electronic.xspf.XSPFPlaylistFilesLoaderStorer;
import cz.martlin.jmop.core.sources.locals.electronic.xspf.XSPFpflsWithFiles;
import cz.martlin.jmop.core.sources.locals.testing.TestingTracksSource;

public class XSPFPlaylistFilesLoaderStorerTest {

	@Test
	public void testBundleFile() throws IOException, JMOPSourceException {
		Bundle bundle = TestingDataCreator.createTestingBundle();
		System.out.println(TestingPrinter.print(bundle));

		File file = File.createTempFile("bundle-file", ".xspf");
		System.out.println("Saving bundle into " + file.getAbsolutePath());

		BaseConfiguration config = new ConstantConfiguration();
		XSPFPlaylistFilesLoaderStorer xpfls = new XSPFPlaylistFilesLoaderStorer(config);

		xpfls.saveBundle(bundle, file);
		// TODO try to open created file in different player

		Bundle rebundle = xpfls.loadBundleData(file);

		System.out.println(TestingPrinter.print(rebundle));

		assertEquals(bundle.toString(), rebundle.toString());
		assertEquals(bundle, rebundle);
	}

	@Test
	public void testPlaylistFile() throws IOException, JMOPSourceException {
		Bundle bundle = TestingDataCreator.createEmptyTestingBundle();
		Playlist playlist = TestingDataCreator.createTestingPlaylist(bundle);
		System.out.println(TestingPrinter.print(playlist));

		File file = File.createTempFile("playlist-file", ".xspf");
		System.out.println("Saving playlist into " + file.getAbsolutePath());

		BaseConfiguration config = new ConstantConfiguration();
		XSPFPlaylistFilesLoaderStorer xpfls = new XSPFPlaylistFilesLoaderStorer(config);

		xpfls.savePlaylist(bundle, playlist, file);
		// TODO try to open created file in different player

		Playlist replaylist = xpfls.loadPlaylist(bundle, file);

		System.out.println(TestingPrinter.print(replaylist));

		assertEquals(replaylist.toString(), replaylist.toString());
		assertEquals(replaylist, playlist);
	}

	//////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testBundleFileWithFiles() throws IOException, JMOPSourceException {
		Bundle bundle = TestingDataCreator.createTestingBundle();
		System.out.println(TestingPrinter.print(bundle));

		File file = File.createTempFile("bundle-file", ".xspf");
		System.out.println("Saving bundle with files into " + file.getAbsolutePath());

		BaseTracksLocalSource tracks = new TestingTracksSource();
		FormatsLocationsUtility flu = new TestingFormatsLocationsUtility(TrackFileFormat.WAV, TrackFileLocation.CACHE);

		BaseConfiguration config = new ConstantConfiguration();
		XSPFPlaylistFilesLoaderStorer xpfls = new XSPFpflsWithFiles(config, tracks, flu);

		xpfls.saveBundle(bundle, file);
	}

}
