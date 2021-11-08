package cz.martlin.jmop.common.musicbase.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.core.misc.DurationUtilities;


public class DefaultInMemoryMusicbaseTest {

	private BaseInMemoryMusicbase musicbase;

	private Bundle testingBundle;
	private Playlist testingPlaylist;
	private Track testingTrack;

	@BeforeEach
	public void before() {
		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();

		prepareDefaultTestingContents(musicbase);

		this.musicbase = musicbase;
	}

	@AfterEach
	public void after()  {
		MusicbaseDebugPrinter.print(musicbase);

		this.musicbase = null;
	}

	private void prepareDefaultTestingContents(BaseMusicbaseModifing musicbase)  {
		testingBundle = musicbase.createNewBundle("TestingBundle");

		testingPlaylist = musicbase.createNewPlaylist(testingBundle, "testing-playlist");

		testingTrack = musicbase.createNewTrack(testingBundle, td("tt", "testing_track"), TrackFileCreationWay.NO_FILE, null);
		testingPlaylist.addTrack(testingTrack);

	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testBundles()  {
		Assumptions.assumeTrue(musicbase != null, "the musicbase is null");
		Bundle fooBundle = musicbase.createNewBundle("FooBundle");
		assertTrue(musicbase.bundles().contains(fooBundle));

		musicbase.renameBundle(fooBundle, "BarBundle");
		assertTrue(musicbase.bundles().contains(fooBundle));
		assertEquals("BarBundle", fooBundle.getName());
		
		musicbase.removeBundle(fooBundle);
		assertFalse(musicbase.bundles().contains(fooBundle));
	}

	@Test
	public void testPlaylists()  {
		Playlist loremPlaylist = musicbase.createNewPlaylist(testingBundle, "lorem-playlist");
		assertTrue(musicbase.playlists(testingBundle).contains(loremPlaylist));
		
		musicbase.renamePlaylist(loremPlaylist, "ipsumPlaylist");
		assertTrue(musicbase.playlists(testingBundle).contains(loremPlaylist));
		assertEquals("ipsumPlaylist", loremPlaylist.getName());
		
		Bundle fooBundle = musicbase.createNewBundle("FooBundle");
		musicbase.movePlaylist(loremPlaylist, fooBundle);
		assertFalse(musicbase.playlists(testingBundle).contains(loremPlaylist));
		assertTrue(musicbase.playlists(fooBundle).contains(loremPlaylist));
		assertEquals(fooBundle, loremPlaylist.getBundle());
		musicbase.removeBundle(fooBundle);
		
		musicbase.removePlaylist(loremPlaylist);
		assertFalse(musicbase.playlists(testingBundle).contains(loremPlaylist));
	}

	@Test
	public void testTracks()  {
		Track holaTrack = musicbase.createNewTrack(testingBundle, td("ho2", "hola"), TrackFileCreationWay.NO_FILE, null);
		assertTrue(musicbase.tracks(testingBundle).contains(holaTrack));
		
		musicbase.renameTrack(holaTrack, "aloha");
		assertTrue(musicbase.tracks(testingBundle).contains(holaTrack));
		assertEquals("aloha", holaTrack.getTitle());
		
		Bundle fooBundle = musicbase.createNewBundle("FooBundle");
		musicbase.moveTrack(holaTrack, fooBundle);
		assertFalse(musicbase.tracks(testingBundle).contains(holaTrack));
		assertTrue(musicbase.tracks(fooBundle).contains(holaTrack));
		assertEquals(fooBundle, holaTrack.getBundle());
		musicbase.removeBundle(fooBundle);
		
		musicbase.removeTrack(holaTrack);
		assertFalse(musicbase.tracks(testingBundle).contains(holaTrack));
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	private TrackData td(String id, String title) {
		return new TrackData(id, title, "Desc", DurationUtilities.createDuration(0, 4, 11));
	}

}
