package cz.martlin.jmop.common.musicbase.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class SimpleInMemoryMusicbaseTest {

	private BaseMusicbase musicbase;

	private Bundle testingBundle;
	private Playlist testingPlaylist;
	private Track testingTrack;

	@BeforeEach
	public void before() {
		BaseMusicbase musicbase = new SimpleInMemoryMusicbase();

		try {
			prepareDefaultTestingContents(musicbase);
		} catch (JMOPSourceException e) {
			Assumptions.assumeFalse(e == null, e.getMessage());
		}

		this.musicbase = musicbase;
	}

	@AfterEach
	public void after() throws JMOPSourceException {
		MusicbaseDebugPrinter.print(musicbase);

		this.musicbase = null;
	}

	private void prepareDefaultTestingContents(BaseMusicbaseModifing musicbase) throws JMOPSourceException {
		testingBundle = musicbase.createBundle("TestingBundle");

		testingPlaylist = musicbase.createPlaylist(testingBundle, "testing-playlist");

		testingTrack = musicbase.createTrack(testingBundle, td("tt", "testing_track"));
		testingPlaylist.addTrack(testingTrack);

	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testBundles() throws JMOPSourceException {
		Bundle fooBundle = musicbase.createBundle("FooBundle");
		assertEquals(fooBundle, musicbase.getBundle("FooBundle"));

		Bundle barBundle = musicbase.createBundle("BarBundle");
		assertEquals(barBundle, musicbase.getBundle("BarBundle"));
	}

	@Test
	public void testPlaylists() throws JMOPSourceException {
		// playlists
		Playlist loremPlaylist = musicbase.createPlaylist(testingBundle, "lorem-playlist");
		assertEquals(loremPlaylist, musicbase.getPlaylist(testingBundle, "lorem-playlist"));

		Playlist ipsumPlaylist = musicbase.createPlaylist(testingBundle, "ipsum-playlist");
		assertEquals(ipsumPlaylist, musicbase.getPlaylist(testingBundle, "ipsum-playlist"));
	}

	@Test
	public void testTracks() throws JMOPSourceException {
		// tracks
		Track holaTrack = musicbase.createTrack(testingBundle, td("ho2", "hola"));
		assertEquals(holaTrack, musicbase.getTrack(testingBundle, "ho2"));

		Track alohaTrack = musicbase.createTrack(testingBundle, td("al3", "aloha"));
		assertEquals(alohaTrack, musicbase.getTrack(testingBundle, "al3"));

//		// fill the playlists
//		testingPlaylist.addTrack(holaTrack);
//		testingPlaylist.addTrack(alohaTrack);
//		// FIXME: assertThat(loremPlaylist.getTracks().getTracks(), contains(holaTrack,
//		// alohaTrack, olaTrack));

	}
	/////////////////////////////////////////////////////////////////////////////////////

	private TrackData td(String id, String title) {
		return new TrackData(id, title, "Desc", DurationUtilities.createDuration(0, 4, 11));
	}

}
