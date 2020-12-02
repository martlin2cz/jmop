package cz.martlin.jmop.common.storages.simplefs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.electronic.impls.ElectronicFileSystemAccessor;

public class SimpleFileSystemedMusicbaseTest {

	@TempDir
	public File root;
	
	private BaseMusicbase musicbase;

	private Bundle testingBundle;
	private Playlist testingPlaylist;
	private Track testingTrack;

	@BeforeEach
	public void before() {
		System.out.println("Our root is " + root);
		
		BaseFileSystemAccessor fs = new ElectronicFileSystemAccessor();
		
		BaseMusicbase musicbase = new SimpleFileSystemedMusicbase(root, fs);

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

		testingTrack = musicbase.createTrack(testingBundle, td("tt"));
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
		Track holaTrack = musicbase.createTrack(testingBundle, td("hola"));
		assertEquals(holaTrack.toString(), musicbase.getTrack(testingBundle, "hola").toString());
		assertEquals(holaTrack, musicbase.getTrack(testingBundle, "hola"));

		Track alohaTrack = musicbase.createTrack(testingBundle, td("aloha"));
		assertEquals(alohaTrack.toString(), musicbase.getTrack(testingBundle, "aloha").toString());
		assertEquals(alohaTrack, musicbase.getTrack(testingBundle, "aloha"));

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
