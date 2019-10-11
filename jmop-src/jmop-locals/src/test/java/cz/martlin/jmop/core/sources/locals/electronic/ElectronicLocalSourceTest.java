package cz.martlin.jmop.core.sources.locals.electronic;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.TestingDataCreator;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseBundlesLocalSource;
import cz.martlin.jmop.core.sources.local.BasePlaylistsLocalSource;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.testing.TestingDirectoryAccessor;

public class ElectronicLocalSourceTest {

	private final TestingDirectoryAccessor tda = new TestingDirectoryAccessor("jmop-electronic-local-source-test");

	@Before
	public void before() throws IOException {
		tda.create();
		System.out.println("Working with " + tda.getDir().getAbsolutePath());
	}

	@After
	public void after() throws IOException {
		tda.delete();
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testBundles() throws JMOPSourceException {
		ElectronicLocalSource local = createSource();
		BaseBundlesLocalSource bundles = local.bundles();

		Bundle bundle = TestingDataCreator.createTestingBundle();

		assertThat(bundles.listBundles(), empty());

		bundles.createBundle(bundle);
		assertThat(bundles.listBundles(), contains(bundle));
		assertTrue(file("box").isDirectory());
		assertTrue(file("box", "all_tracks.xspf").isFile());

		bundles.deleteBundle(bundle);
		assertThat(bundles.listBundles(), empty());

		// TODO test move
	}

	@Test
	public void testPlaylists() throws JMOPSourceException {
		ElectronicLocalSource local = createSource();
		BasePlaylistsLocalSource playlists = local.playlists();

		Bundle bundle = TestingDataCreator.createTestingBundle();
		local.bundles().createBundle(bundle);
		assumeTrue(file("box").isDirectory());

		Playlist allTracksPlaylists = bundle.getPlaylist("all tracks");

		Playlist playlistFirst = TestingDataCreator.createTestingPlaylist(bundle, "first");

		assertThat(playlists.loadPlaylists(bundle), contains(allTracksPlaylists));

		playlists.createPlaylist(playlistFirst);
		assertThat(playlists.loadPlaylists(bundle), contains(allTracksPlaylists, playlistFirst));
		assertTrue(file("box", "all_tracks.xspf").isFile());
		assertTrue(file("box", "first.xspf").isFile());

		playlists.saveUpdatedPlaylist(playlistFirst);
		assertThat(playlists.loadPlaylists(bundle), contains(allTracksPlaylists, playlistFirst));
		assertTrue(file("box", "first.xspf").isFile());

		Playlist playlistSecond = TestingDataCreator.createTestingPlaylist(bundle, "second");
		playlists.createPlaylist(playlistSecond);
		assertThat(playlists.loadPlaylists(bundle), contains(allTracksPlaylists, playlistFirst, playlistSecond));
		assertTrue(file("box", "all_tracks.xspf").isFile());
		assertTrue(file("box", "first.xspf").isFile());
		assertTrue(file("box", "second.xspf").isFile());

		playlists.deletePlaylist(playlistFirst);
		assertThat(playlists.loadPlaylists(bundle), contains(allTracksPlaylists, playlistSecond));
		assertTrue(file("box", "all_tracks.xspf").isFile());
		assertFalse(file("box", "first.xspf").isFile());
		assertTrue(file("box", "second.xspf").isFile());

		// TODO test duplicate
	}

	@Test
	public void testTracks() throws JMOPSourceException {
		ElectronicLocalSource local = createSource();
		BaseTracksLocalSource tracks = local.tracks();
		
		Bundle bundle = TestingDataCreator.createTestingBundle();
		local.bundles().createBundle(bundle);
		assumeTrue(file("box").isDirectory());

		Track fooTrack = bundle.getTrack(TestingDataCreator.FOO_TRACK_ID);
		for (TrackFileLocation location : TrackFileLocation.values()) {
			for (TrackFileFormat format : TrackFileFormat.values()) {
				File file = tracks.fileOfTrack(fooTrack, location, format);
				System.out.println(file);
				assertFalse(tracks.exists(fooTrack, location, format));

				assumeTrue(tryCreateFile(file));
				assertTrue(tracks.exists(fooTrack, location, format));

				tracks.deleteIfExists(fooTrack, location, format);
				assertFalse(tracks.exists(fooTrack, location, format));
			}
		}

		// TODO test move
	}

	///////////////////////////////////////////////////////////////////////////
	private ElectronicLocalSource createSource() {
		BaseConfiguration config = new ConstantConfiguration();
		File root = tda.getDir();
		ElectronicLocalSource local = new ElectronicLocalSource(config, root);
		return local;
	}

	private boolean tryCreateFile(File file) {
		try {
			return file.createNewFile();
		} catch (IOException e) {
			System.err.println(e.toString());
			// TODO log warn error ?
			return false;
		}
	}

	private File file(String... segments) {
		String root = tda.getDir().getAbsolutePath();
		Path path = Paths.get(root, segments);
		return path.toFile();
	}
}
