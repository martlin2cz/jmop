package cz.martlin.jmop.player.fascade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.resources.TestingTrackFilesCreator;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.fascade.dflt.DefaultJMOPPlayerBuilder;

class JMOPMusicbaseTest {

	@RegisterExtension
	public TestingMusicdataExtension tme;

	private JMOPMusicbase musicbase;

	public JMOPMusicbaseTest() {
		this.musicbase = DefaultJMOPPlayerBuilder.createTesting().musicbase();

		BaseMusicbaseModifing musicbase = this.musicbase.getMusicbase();
		tme = TestingMusicdataExtension.withMusicbase(() -> musicbase, TrackFileFormat.MP3); 
	}

	@Test
	void testBundleOfName()  {
		Bundle daftPunk = musicbase.bundleOfName("Daft Punk");

		assertEquals(tme.tmd.daftPunk, daftPunk);
	}

	@Test
	void testPlaylistOfName()  {
		Playlist seventeen = musicbase.playlistOfName(tme.tmd.cocolinoDeep, "Seventeen");

		assertEquals(tme.tmd.seventeen, seventeen);
	}

	@Test
	void testTrackOfTitle()  {
		Track atZijiDuchove = musicbase.trackOfTitle(tme.tmd.robick, "At ziji duchove");

		assertEquals(tme.tmd.atZijiDuchove, atZijiDuchove);
	}

	@Test
	void testBundles()  {
		Set<Bundle> actual = musicbase.bundles();

		Iterable<?> expected = Arrays.asList(tme.tmd.cocolinoDeep, tme.tmd.daftPunk, tme.tmd.londonElektricity,
				tme.tmd.robick);
		assertIterableEquals(actual, expected);
	}

	@Test
	void testPlaylists()  {
		Bundle bundle = tme.tmd.londonElektricity;
		Set<Playlist> actual = musicbase.playlists(bundle);
		// TODO test if bundle == null

		String allTracksPlaylistName = "all tracks";
		Playlist allTracks = musicbase.playlistOfName(bundle, allTracksPlaylistName);
		
		Iterable<?> expected = Arrays.asList(allTracks, tme.tmd.bestTracks, tme.tmd.syncopatedCity, tme.tmd.yikes);
		assertIterableEquals(actual, expected);
	}

	@Test
	void testTracks()  {
		Set<Track> actual = musicbase.tracks(tme.tmd.robick);
		// TODO test if bundle == null

		Iterable<?> expected = Arrays.asList(tme.tmd.atZijiDuchove, tme.tmd.ladyCarneval, tme.tmd.neniNutno,
				tme.tmd.znamkaPunku);
		assertIterableEquals(actual, expected);
	}

	@Test
	void testCreateNewBundle()  {
		Bundle bundle = musicbase.createNewBundle("deadmau5");

		assertEquals("deadmau5", bundle.getName());
	}

	@Test
	void testRenameBundle()  {
		Bundle bundle = tme.tmd.londonElektricity;
		musicbase.renameBundle(bundle, "Tony Collman");

		assertEquals("Tony Collman", bundle.getName());
	}

	@Test
	void testRemoveBundle()  {
		Bundle bundle = tme.tmd.robick;

		musicbase.removeBundle(bundle);

		Bundle noBundle = musicbase.bundleOfName("robick");
		assertNull(noBundle);
	}

	@Test
	void testCreateNewPlaylist()  {
		Bundle bundle = tme.tmd.londonElektricity;
		Playlist playlist = musicbase.createNewPlaylist(bundle, "worst tracks");

		assertEquals("worst tracks", playlist.getName());
	}

	@Test
	void testRenamePlaylist()  {
		Playlist playlist = tme.tmd.bestTracks;

		musicbase.renamePlaylist(playlist, "absolutelly the best tracks");

		assertEquals("absolutelly the best tracks", playlist.getName());
	}

	@Test
	void testMovePlaylist()  {
		Playlist playlist = tme.tmd.bestTracks;
		Bundle oldBundle = playlist.getBundle();
		Bundle newBundle = tme.tmd.daftPunk;

		musicbase.movePlaylist(playlist, newBundle, false);
		//TODO test with copyTracks := true

		Playlist noPlaylist = musicbase.playlistOfName(oldBundle, playlist.getName());
		assertNull(noPlaylist);

		Playlist newPlaylist = musicbase.playlistOfName(newBundle, playlist.getName());
		assertNotNull(newPlaylist);
		// TODO verify name and bundle
	}

	@Test
	void testRemovePlaylist()  {
		Playlist playlist = tme.tmd.bestTracks;
		Bundle bundle = playlist.getBundle();

		musicbase.removePlaylist(playlist);

		Playlist noPlaylist = musicbase.playlistOfName(bundle, playlist.getName());
		assertNull(noPlaylist);
	}

	@Test
	void testCreateNewTrack()  {
		Bundle bundle = tme.tmd.daftPunk;
		TrackData data = new TrackData("HBFS", "Harder, Better, Faster, Stronger", "Discovery, 2001",
				DurationUtilities.createDuration(0, 3, 48));
		
		File contents;
		try {
			contents = File.createTempFile("track-", ".mp3"); 
			
			TestingTrackFilesCreator creator = new TestingTrackFilesCreator();
			creator.prepare(TrackFileFormat.MP3, contents);
		} catch (IOException e) {
			assumeFalse(e == null, "Could not create track file");
			return;
		}
		
		Track track = musicbase.createNewTrack(bundle, data, TrackFileCreationWay.COPY_FILE, contents);

		assertEquals("Harder, Better, Faster, Stronger", track.getTitle());
		//TODO check all the other properties
	}

	@Test
	void testRenameTrack()  {
		Track track = tme.tmd.atZijiDuchove;

		musicbase.renameTrack(track, "Ať žijí duchové!");

		assertEquals("Ať žijí duchové!", track.getTitle());
	}

	@Test
	void testMoveTrack()  {
		Track track = tme.tmd.oneMoreTime;
		Bundle oldBundle = track.getBundle();
		Bundle newBundle = tme.tmd.londonElektricity;

		musicbase.moveTrack(track, newBundle);

		Track noTrack = musicbase.trackOfTitle(oldBundle, track.getTitle());
		assertNull(noTrack);

		Track newTrack = musicbase.trackOfTitle(newBundle, track.getTitle());
		assertNotNull(newTrack);
		// TODO verify title and bundle
	}

	@Test
	void testRemoveTrack()  {
		Track track = tme.tmd.dancingWithTheElepthant;
		Bundle bundle = track.getBundle();

		musicbase.removeTrack(track);
		
		Track noTrack = musicbase.trackOfTitle(bundle, track.getTitle());
		assertNull(noTrack);
	}

	@Test
	void testUpdateTrack()  {
		Track track = tme.tmd.ladyCarneval;
//		Bundle bundle = track.getBundle();

		TrackData data = new TrackData("KGLC", track.getTitle(), "Great one!", DurationUtilities.createDuration(0, 4, 12));
		
		musicbase.updateTrack(track, data);
		
		assertEquals("Lady Carneval", track.getTitle());
		assertEquals("Great one!", track.getDescription());
	}

}
