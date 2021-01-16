package cz.martlin.jmop.player.fascade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.utils.TestingMusicbaseExtension;
import cz.martlin.jmop.common.utils.TestingTrackFilesCreator;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.fascade.dflt.DefaultJMOPPlayerBuilder;

class JMOPMusicbaseTest {

	@RegisterExtension
	TestingMusicbaseExtension tmb;

	private JMOPMusicbase musicbase;

	public JMOPMusicbaseTest() {
		this.musicbase = DefaultJMOPPlayerBuilder.createTesting().musicbase();

		BaseMusicbaseModifing musicbase = this.musicbase.getMusicbase();
		this.tmb = new TestingMusicbaseExtension(musicbase, false);
	}

	@Test
	void testBundleOfName() throws JMOPMusicbaseException {
		Bundle daftPunk = musicbase.bundleOfName("Daft Punk");

		assertEquals(tmb.tm.daftPunk, daftPunk);
	}

	@Test
	void testPlaylistOfName() throws JMOPMusicbaseException {
		Playlist seventeen = musicbase.playlistOfName(tmb.tm.cocolinoDeep, "Seventeen");

		assertEquals(tmb.tm.seventeen, seventeen);
	}

	@Test
	void testTrackOfTitle() throws JMOPMusicbaseException {
		Track atZijiDuchove = musicbase.trackOfTitle(tmb.tm.robick, "At ziji duchove");

		assertEquals(tmb.tm.atZijiDuchove, atZijiDuchove);
	}

	@Test
	void testBundles() throws JMOPMusicbaseException {
		Set<Bundle> actual = musicbase.bundles();

		Iterable<?> expected = Arrays.asList(tmb.tm.cocolinoDeep, tmb.tm.daftPunk, tmb.tm.londonElektricity,
				tmb.tm.robick);
		assertIterableEquals(actual, expected);
	}

	@Test
	void testPlaylists() throws JMOPMusicbaseException {
		Bundle bundle = tmb.tm.londonElektricity;
		Set<Playlist> actual = musicbase.playlists(bundle);
		// TODO test if bundle == null

		String allTracksPlaylistName = "all tracks";
		Playlist allTracks = musicbase.playlistOfName(bundle, allTracksPlaylistName);
		
		Iterable<?> expected = Arrays.asList(allTracks, tmb.tm.bestTracks, tmb.tm.syncopatedCity, tmb.tm.yikes);
		assertIterableEquals(actual, expected);
	}

	@Test
	void testTracks() throws JMOPMusicbaseException {
		Set<Track> actual = musicbase.tracks(tmb.tm.robick);
		// TODO test if bundle == null

		Iterable<?> expected = Arrays.asList(tmb.tm.atZijiDuchove, tmb.tm.ladyCarneval, tmb.tm.neniNutno,
				tmb.tm.znamkaPunku);
		assertIterableEquals(actual, expected);
	}

	@Test
	void testCreateNewBundle() throws JMOPMusicbaseException {
		Bundle bundle = musicbase.createNewBundle("deadmau5");

		assertEquals("deadmau5", bundle.getName());
	}

	@Test
	void testRenameBundle() throws JMOPMusicbaseException {
		Bundle bundle = tmb.tm.londonElektricity;
		musicbase.renameBundle(bundle, "Tony Collman");

		assertEquals("Tony Collman", bundle.getName());
	}

	@Test
	void testRemoveBundle() throws JMOPMusicbaseException {
		Bundle bundle = tmb.tm.robick;

		musicbase.removeBundle(bundle);

		Bundle noBundle = musicbase.bundleOfName("robick");
		assertNull(noBundle);
	}

	@Test
	void testCreateNewPlaylist() throws JMOPMusicbaseException {
		Bundle bundle = tmb.tm.londonElektricity;
		Playlist playlist = musicbase.createNewPlaylist(bundle, "worst tracks");

		assertEquals("worst tracks", playlist.getName());
	}

	@Test
	void testRenamePlaylist() throws JMOPMusicbaseException {
		Playlist playlist = tmb.tm.bestTracks;

		musicbase.renamePlaylist(playlist, "absolutelly the best tracks");

		assertEquals("absolutelly the best tracks", playlist.getName());
	}

	@Test
	void testMovePlaylist() throws JMOPMusicbaseException {
		Playlist playlist = tmb.tm.bestTracks;
		Bundle oldBundle = playlist.getBundle();
		Bundle newBundle = tmb.tm.daftPunk;

		musicbase.movePlaylist(playlist, newBundle);

		Playlist noPlaylist = musicbase.playlistOfName(oldBundle, playlist.getName());
		assertNull(noPlaylist);

		Playlist newPlaylist = musicbase.playlistOfName(newBundle, playlist.getName());
		assertNotNull(newPlaylist);
		// TODO verify name and bundle
	}

	@Test
	void testRemovePlaylist() throws JMOPMusicbaseException {
		Playlist playlist = tmb.tm.bestTracks;
		Bundle bundle = playlist.getBundle();

		musicbase.removePlaylist(playlist);

		Playlist noPlaylist = musicbase.playlistOfName(bundle, playlist.getName());
		assertNull(noPlaylist);
	}

	@Test
	void testCreateNewTrack() throws JMOPMusicbaseException {
		Bundle bundle = tmb.tm.daftPunk;
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
		
		Track track = musicbase.createNewTrack(bundle, data, contents);

		assertEquals("Harder, Better, Faster, Stronger", track.getTitle());
		//TODO check all the other properties
	}

	@Test
	void testRenameTrack() throws JMOPMusicbaseException {
		Track track = tmb.tm.atZijiDuchove;

		musicbase.renameTrack(track, "Ať žijí duchové!");

		assertEquals("Ať žijí duchové!", track.getTitle());
	}

	@Test
	void testMoveTrack() throws JMOPMusicbaseException {
		Track track = tmb.tm.oneMoreTime;
		Bundle oldBundle = track.getBundle();
		Bundle newBundle = tmb.tm.londonElektricity;

		musicbase.moveTrack(track, newBundle);

		Track noTrack = musicbase.trackOfTitle(oldBundle, track.getTitle());
		assertNull(noTrack);

		Track newTrack = musicbase.trackOfTitle(newBundle, track.getTitle());
		assertNotNull(newTrack);
		// TODO verify title and bundle
	}

	@Test
	void testRemoveTrack() throws JMOPMusicbaseException {
		Track track = tmb.tm.dancingWithTheElepthant;
		Bundle bundle = track.getBundle();

		musicbase.removeTrack(track);
		
		Track noTrack = musicbase.trackOfTitle(bundle, track.getTitle());
		assertNull(noTrack);
	}

	@Test
	void testUpdateTrack() throws JMOPMusicbaseException {
		Track track = tmb.tm.ladyCarneval;
//		Bundle bundle = track.getBundle();

		TrackData data = new TrackData("KGLC", track.getTitle(), "Great one!", DurationUtilities.createDuration(0, 4, 12));
		
		musicbase.updateTrack(track, data);
		
		assertEquals("Lady Carneval", track.getTitle());
		assertEquals("Great one!", track.getDescription());
	}

}
