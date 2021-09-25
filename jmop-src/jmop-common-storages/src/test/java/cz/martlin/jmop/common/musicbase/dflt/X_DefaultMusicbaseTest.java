package cz.martlin.jmop.common.musicbase.dflt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.common.storages.dflt.X_DefaultStorage;
import cz.martlin.jmop.common.storages.utils.LoggingMusicbaseStorage;
import cz.martlin.jmop.common.testing.extensions.TestingRootDirExtension;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

@Deprecated
@TestMethodOrder(OrderAnnotation.class)
class X_DefaultMusicbaseTest {

	@RegisterExtension
	public TestingRootDirExtension root = new TestingRootDirExtension(this);

	public class DefaultConfig implements BaseDefaultStorageConfig {

		@Override
		public TrackFileFormat trackFileFormat() {
			return 	TrackFileFormat.MP3;
		}

		@Override
		public String getAllTracksPlaylistName() {
			return ALL_TRACKS_PLAYLIST_NAME;
		}
	}

	private static final String ALL_TRACKS_PLAYLIST_NAME = "all the tracks";


	///////////////////////////////////////////////////////////////////////////

	@Order(value = 1)
	@Test
	void testSave()  {
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
	void testLoad()  {
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
	void testReload()  {
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
	void testRenameBundle()  {
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
	void testMoveTrack()  {
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

		X_DefaultStorage storage = X_DefaultStorage.create(root.getRoot(), new DefaultConfig(), new SimpleErrorReporter(), inmemory);
		LoggingMusicbaseStorage logging = new LoggingMusicbaseStorage(storage);

		BaseMusicbase musicbase = new PersistentMusicbase(inmemory, logging);

		System.out.println("Musicbase ready: " + musicbase);
		System.out.println("Working with " + root.getRoot());

		return musicbase;
	}

}
