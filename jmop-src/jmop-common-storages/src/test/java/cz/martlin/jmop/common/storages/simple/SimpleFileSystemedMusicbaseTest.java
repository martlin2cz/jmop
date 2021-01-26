package cz.martlin.jmop.common.storages.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class SimpleFileSystemedMusicbaseTest {

	private BaseMusicbaseModifing musicbase;

	private Bundle testingBundle;
	private Playlist testingPlaylist;
	private Track testingTrack;

	@BeforeEach
	public void before() {
		BaseMusicbaseModifing musicbase = new DefaultInMemoryMusicbase();

		prepareDefaultTestingContents(musicbase);

		this.musicbase = musicbase;
	}

	@AfterEach
	public void after()  {
//		MusicbaseDebugPrinter.print(musicbase);

		this.musicbase = null;
	}

	private void prepareDefaultTestingContents(BaseMusicbaseModifing musicbase)  {
		testingBundle = musicbase.createNewBundle("TestingBundle");

		testingPlaylist = musicbase.createNewPlaylist(testingBundle, "testing-playlist");

		testingTrack = musicbase.createNewTrack(testingBundle, td("tt"), null);
		testingPlaylist.addTrack(testingTrack);

	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testBundles()  {
		Bundle fooBundle = musicbase.createNewBundle("FooBundle");
		assertEquals("FooBundle", fooBundle.getName());

		Bundle barBundle = musicbase.createNewBundle("BarBundle");
		assertEquals("BarBundle", barBundle.getName());
	}

	@Test
	public void testPlaylists()  {
		// playlists
		Playlist loremPlaylist = musicbase.createNewPlaylist(testingBundle, "lorem-playlist");
		assertEquals(testingBundle, loremPlaylist.getBundle());
		assertEquals("lorem-playlist", loremPlaylist.getName());

		
		Playlist ipsumPlaylist = musicbase.createNewPlaylist(testingBundle, "ipsum-playlist");
		assertEquals(testingBundle, ipsumPlaylist.getBundle());
		assertEquals("ipsum-playlist", ipsumPlaylist.getName());
	}

	@Test
	public void testTracks()  {
		// tracks
		Track holaTrack = musicbase.createNewTrack(testingBundle, td("hola"), null);
		assertEquals(testingBundle, holaTrack.getBundle());
		assertEquals("hola", holaTrack.getTitle());

		Track alohaTrack = musicbase.createNewTrack(testingBundle, td("aloha"), null);
		assertEquals(testingBundle, alohaTrack.getBundle());
		assertEquals("aloha", alohaTrack.getTitle());

//		// fill the playlists
//		testingPlaylist.addTrack(holaTrack);
//		testingPlaylist.addTrack(alohaTrack);
//		// FIXME: assertThat(loremPlaylist.getTracks().getTracks(), contains(holaTrack,
//		// alohaTrack, olaTrack));

	}
	/////////////////////////////////////////////////////////////////////////////////////

	private TrackData td(String id) {
		return new TrackData(id, id, id, DurationUtilities.createDuration(0, 3, 15));
	}


}
