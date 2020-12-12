package cz.martlin.jmop.common.musicbase.dflt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.dflt.DefaultStorage;
import cz.martlin.jmop.common.storages.utils.LoggingMusicbaseStorage;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

class DefaultMusicbaseTest {

//	@TempDir
//	public File root;
	
	public File root = new File(System.getProperty("java.io.tmpdir"), "jmop");
	
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Running with " + root.getAbsolutePath());

		assumeTrue(root.isDirectory(), "The root dir is not dir");
		assumeTrue(root.list().length == 0, "The root dir is not empty");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Done, won't delete the testing dir contents");
	}

	///////////////////////////////////////////////////////////////////////////
	
	@Test
	void test() throws JMOPSourceException {
		BaseMusicbase musicbase = prepareMusicbase();
		
		Bundle fooBundle = musicbase.createNewBundle("FooBundle");
		assertEquals("FooBundle", fooBundle.getName());
		
		Playlist loremPlaylist = musicbase.createNewPlaylist(fooBundle, "lorem-playlist");
		assertEquals("lorem-playlist", loremPlaylist.getName());
		
		Track helloTrack = musicbase.createNewTrack(fooBundle, td("hello"));
		assertEquals("hello", helloTrack.getTitle());
		
		loremPlaylist.addTrack(helloTrack);
		musicbase.playlistUpdated(loremPlaylist);
		
		helloTrack.setMetadata(helloTrack.getMetadata().played());
		musicbase.trackUpdated(helloTrack);
		
		MusicbaseDebugPrinter.print(musicbase);
	}
	
	///////////////////////////////////////////////////////////////////////////

	private TrackData td(String string) {
		Duration duration = DurationUtilities.createDuration(0, 1, 11);
		return new TrackData(string, string, string, duration);
	}

	private BaseMusicbase prepareMusicbase() {
		String allTracksPlaylistName = "all the tracks";
		TrackFileFormat format = TrackFileFormat.MP3;
		
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		
		DefaultStorage storage = DefaultStorage.create(root, allTracksPlaylistName, format, inmemory);
		LoggingMusicbaseStorage logging = new LoggingMusicbaseStorage(storage);
		
		BaseMusicbase musicbase = new PersistentMusicbase(inmemory, logging);
		
		System.out.println("Musicbase ready: " + musicbase);
		System.out.println("Working with " + root.getAbsolutePath());
		
		return musicbase;
	}

}
