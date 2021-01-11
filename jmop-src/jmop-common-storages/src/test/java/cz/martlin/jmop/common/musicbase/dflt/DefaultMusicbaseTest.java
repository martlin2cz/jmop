package cz.martlin.jmop.common.musicbase.dflt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.common.musicbase.dflt.DefaultMusicbaseTest.DefaultConfig;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.common.storages.dflt.DefaultStorage;
import cz.martlin.jmop.common.storages.utils.LoggingMusicbaseStorage;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

@TestMethodOrder(OrderAnnotation.class)
class DefaultMusicbaseTest {

//	@TempDir
	//	public File root;

	public class DefaultConfig implements BaseDefaultStorageConfig {

		@Override
		public TrackFileFormat getSaveFormat() {
			return 	TrackFileFormat.MP3;
		}

		@Override
		public String getAllTrackPlaylistName() {
			return ALL_TRACKS_PLAYLIST_NAME;
		}

	}

	private static final String ALL_TRACKS_PLAYLIST_NAME = "all the tracks";

	
	public static File root = new File(System.getProperty("java.io.tmpdir"), "jmop");
	
	@BeforeAll
	static void setUp() throws Exception {
		System.out.println("Running with " + root.getAbsolutePath());

		assumeTrue(root.isDirectory(), "The root dir is not dir");
		assumeTrue(root.list().length == 0, "The root dir is not empty");
	}

	@AfterAll
	static void tearDown() throws Exception {
		System.out.println("Done, won't delete the testing dir contents");
	}

	///////////////////////////////////////////////////////////////////////////

	@Order(value = 1)
	@Test
	void testSave() throws JMOPMusicbaseException {
		BaseMusicbase musicbase = prepareMusicbase();

		// create bundle
		Bundle fooBundle = musicbase.createNewBundle("FooBundle");
		assertEquals("FooBundle", fooBundle.getName());

		// create playlist
		Playlist loremPlaylist = musicbase.createNewPlaylist(fooBundle, "lorem-playlist");
		assertEquals("lorem-playlist", loremPlaylist.getName());

		// create tracks
		Track helloTrack = musicbase.createNewTrack(fooBundle, td("hello"), null);
		assertEquals("hello", helloTrack.getTitle());

		// update them
		loremPlaylist.addTrack(helloTrack);
		musicbase.playlistUpdated(loremPlaylist);

		helloTrack.setMetadata(helloTrack.getMetadata().played());
		musicbase.trackUpdated(helloTrack);

		// and print
		MusicbaseDebugPrinter.print(musicbase);
	}
	
	@Order(value = 2)
	@Test
	void testLoad() throws JMOPMusicbaseException {
		BaseMusicbase musicbase = prepareMusicbase();
		
		musicbase.load();
		MusicbaseDebugPrinter.print(musicbase);
		
		// verify bundles
		Bundle actualFooBundle = musicbase.bundles().stream().findAny().get();
		assertEquals("FooBundle", actualFooBundle.getName());
		
		// verify playlists
		assertTrue(musicbase.playlists(actualFooBundle).stream().anyMatch( //
				p -> p.getName().equals(ALL_TRACKS_PLAYLIST_NAME)));
		
		assertTrue(musicbase.playlists(actualFooBundle).stream().anyMatch( //
				p -> p.getName().equals("lorem-playlist")));
		
		// verify tracks
		Track actualHelloTrack = musicbase.tracks(actualFooBundle).stream().findAny().get();
		assertEquals("hello", actualHelloTrack.getTitle());
	}
	
	@Order(value = 3)
	@Test
	void testReload() throws JMOPMusicbaseException {
		BaseMusicbase musicbase = prepareMusicbase();
		
		// load 
		musicbase.load();
		MusicbaseDebugPrinter.print(musicbase);
		
		// (re)load once again
		musicbase.load();
		MusicbaseDebugPrinter.print(musicbase);
	}
	
	@Order(value = 4)
	@Test
	void testRenameBundle() throws JMOPMusicbaseException {
		BaseMusicbase musicbase = prepareMusicbase();
		
		musicbase.load();
		MusicbaseDebugPrinter.print(musicbase);
		
		// pick bundle ...
		Bundle fooBundle = musicbase.bundles().stream().findAny().get();
		assumeTrue("FooBundle".equals(fooBundle.getName()));
		
		// ... and rename it
		musicbase.renameBundle(fooBundle, "DefinetellyNotAFooBundle");
		assertEquals("DefinetellyNotAFooBundle", fooBundle.getName());
	}

	@Order(value = 5)
	@Test
	void testMoveTrack() throws JMOPMusicbaseException {
		BaseMusicbase musicbase = prepareMusicbase();
		
		musicbase.load();
		MusicbaseDebugPrinter.print(musicbase);
		
		// pick bundle and track
		Bundle notfFooBundle = musicbase.bundles().stream().findAny().get();
		assumeTrue("DefinetellyNotAFooBundle".equals(notfFooBundle.getName()));
		
		Track helloTrack = musicbase.tracks(notfFooBundle).stream().findAny().get();
		assumeTrue("hello".equals(helloTrack.getTitle()));

		// create another bundle
		Bundle barBundle = musicbase.createNewBundle("BarBundle");
		assumeTrue("BarBundle".equals(barBundle.getName()));
		
		// move the track
		musicbase.moveTrack(helloTrack, barBundle);
		
		// verify
		assertTrue(musicbase.tracks(notfFooBundle).isEmpty());
		
		Track newHelloTrack = musicbase.tracks(barBundle).stream().findAny().get();
		assertEquals("hello", newHelloTrack.getTitle());
	}

	
	///////////////////////////////////////////////////////////////////////////

	private TrackData td(String string) {
		Duration duration = DurationUtilities.createDuration(0, 1, 11);
		return new TrackData(string, string, string, duration);
	}

	private BaseMusicbase prepareMusicbase() {


		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();

		DefaultStorage storage = DefaultStorage.create(root, new DefaultConfig(),  inmemory);
		LoggingMusicbaseStorage logging = new LoggingMusicbaseStorage(storage);

		BaseMusicbase musicbase = new PersistentMusicbase(inmemory, logging);

		System.out.println("Musicbase ready: " + musicbase);
		System.out.println("Working with " + root.getAbsolutePath());

		return musicbase;
	}

}
