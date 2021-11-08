package cz.martlin.jmop.common.musicbase.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseLoading;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.stats.MusicbaseStats.ListStatsSpecifier;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * Tests the {@link #MusicbaseStats} against the testing musicdata
 * ({@link AbstractTestingMusicdata}).
 * 
 * @author martin
 *
 */
class MusicbaseStatsTest {

	@RegisterExtension
	public TestingMusicdataExtension tme;

	private MusicbaseStats stats;

	public MusicbaseStatsTest() {
		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();
		stats = new MusicbaseStats(musicbase);

		tme = TestingMusicdataExtension.withMusicbase(() -> musicbase, false);
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	void testMostPlayedBundle() {
		Queue<Bundle> mostPlayedBundles = stats.bundles(ListStatsSpecifier.MOST_PLAYED, null);
		printBundles(mostPlayedBundles, (b) -> b.getMetadata().getNumberOfPlays());

		Bundle firstBundle = mostPlayedBundles.poll();
		Bundle secondBundle = mostPlayedBundles.poll();

		assertTrue(firstBundle.getMetadata().getNumberOfPlays() > secondBundle.getMetadata().getNumberOfPlays());
	}

	@Test
	void testMostPlayedBundleTime() {
		Queue<Bundle> mostPlayedBundles = stats.bundles(ListStatsSpecifier.MOST_TIME_PLAYED, null);
		printBundles(mostPlayedBundles, (b) -> b.getMetadata().getTotalTime());

		Bundle firstBundle = mostPlayedBundles.poll();
		Bundle secondBundle = mostPlayedBundles.poll();

		assertTrue(firstBundle.getMetadata().getTotalTime().greaterThan(secondBundle.getMetadata().getTotalTime()));
		assertEquals(tme.tmd.daftPunk, firstBundle);
	}
	
	@Test
	void testLastPlayedBundle() {
		Queue<Bundle> lastPlayedBundles = stats.bundles(ListStatsSpecifier.LAST_PLAYED, 2);
		printBundles(lastPlayedBundles, (b) -> b.getMetadata().getLastPlayed());

		Bundle firstBundle = lastPlayedBundles.poll();
		Bundle secondBundle = lastPlayedBundles.poll();

		assertNotNull(firstBundle.getMetadata().getLastPlayed());
		assertNull(secondBundle.getMetadata().getLastPlayed());
	}

	@Test
	void testMostPlayedTrack() {
		Queue<Track> mostPlayedTracks = stats.tracks(null, ListStatsSpecifier.MOST_PLAYED, 10);
		printTracks(mostPlayedTracks, (t) -> t.getMetadata().getNumberOfPlays());

		Track firstTrack = mostPlayedTracks.poll();
		Track secondTrack = mostPlayedTracks.poll();
		Track thirdTrack = mostPlayedTracks.poll();

		assertTrue(firstTrack.getMetadata().getNumberOfPlays() > secondTrack.getMetadata().getNumberOfPlays());
		assertTrue(secondTrack.getMetadata().getNumberOfPlays() > thirdTrack.getMetadata().getNumberOfPlays());

		assertEquals(tme.tmd.oneMoreTime, firstTrack);
		assertEquals(tme.tmd.aerodynamic, secondTrack);
		assertEquals(tme.tmd.getLucky, thirdTrack);

		assertEquals(3, firstTrack.getMetadata().getNumberOfPlays());
		assertEquals(2, secondTrack.getMetadata().getNumberOfPlays());
		assertEquals(1, thirdTrack.getMetadata().getNumberOfPlays());
	}
	
	@Test
	void testMostPlayedTrackTime() {
		Queue<Track> mostPlayedTracks = stats.tracks(null, ListStatsSpecifier.MOST_TIME_PLAYED, 10);
		printTracks(mostPlayedTracks, (t) -> t.getMetadata().getTotalTime());

		Track firstTrack = mostPlayedTracks.poll();
		Track secondTrack = mostPlayedTracks.poll();
		Track thirdTrack = mostPlayedTracks.poll();

		assertTrue(firstTrack.getMetadata().getTotalTime().greaterThan(secondTrack.getMetadata().getTotalTime()));
		assertTrue(secondTrack.getMetadata().getTotalTime().greaterThan(thirdTrack.getMetadata().getTotalTime()));

		assertEquals(tme.tmd.aerodynamic, firstTrack);
		assertEquals(tme.tmd.verdisQuo, secondTrack);
		assertEquals(tme.tmd.getLucky, thirdTrack);

		assertEquals(DurationUtilities.createDuration(0, 6, 54), firstTrack.getMetadata().getTotalTime());
		assertEquals(DurationUtilities.createDuration(0, 5, 44), secondTrack.getMetadata().getTotalTime());
		assertEquals(DurationUtilities.createDuration(0, 0, 21), thirdTrack.getMetadata().getTotalTime());
	}

	@Test
	void testLastPlayedPlaylist() {
		Queue<Playlist> lastPlayedPlaylist = stats.playlists(tme.tmd.cocolinoDeep, ListStatsSpecifier.LAST_PLAYED, 999);

		Playlist firstPlaylist = lastPlayedPlaylist.poll();
		Playlist secondPlaylist = lastPlayedPlaylist.poll();

		assertEquals(0, firstPlaylist.getMetadata().getNumberOfPlays());
		assertNull(secondPlaylist);
	}

///////////////////////////////////////////////////////////////////////////////
	@Test
	void testBiggestBundlesByTracks() {
		Queue<Bundle> bundles = stats.biggestBundlesByTracks(100);
		printBundles(bundles, (b) -> ((BaseMusicbaseLoading) tme.getMusicbase()).tracks(b).size());

		Bundle firstBundle = bundles.poll();
		assertEquals(tme.tmd.cocolinoDeep, firstBundle);
	}

	@Test
	void tesLongestPlaylistByNumberOfTracks() {
		Queue<Playlist> playlists = stats.longestPlaylistByNumberOfTracks(null, null);
		printPlaylists(playlists, (p) -> p.getTracks().getTracks().size());

		Playlist firstPlaylist = playlists.poll();
		assertEquals(tme.tmd.seventeen, firstPlaylist);
	}

	@Test
	void testLongestPlaylistByTotalDuration() {
		Queue<Playlist> playlists = stats.longestPlaylistByTotalDuration(null, 5);
		printPlaylists(playlists, (p) -> DurationUtilities.toHumanString(p.getTotalDuration()));

		Playlist firstPlaylist = playlists.poll();
		assertEquals(tme.tmd.seventeen, firstPlaylist);
	}

///////////////////////////////////////////////////////////////////////////////
	@Test
	void testNumberOfTracks() {
		int number = stats.numberOfTracks(null);
		System.out.println(number);
		assertEquals(21, number);
	}

	@Test
	void testTotalPlayedTracks() {
		int number = stats.totalPlayedTracks(null);
		System.out.println(number);
		assertEquals(7, number);
	}

	@Test
	void testTotalDuration() {
		Duration totalDuration = stats.totalDuration(null);
		System.out.println(DurationUtilities.toHumanString(totalDuration));
		assertEquals(DurationUtilities.createDuration(10, 1, 54), totalDuration);
	}

	@Test
	void testTotalPlayedTime() {
		Duration totalDuration = stats.totalPlayedTime(null);
		System.out.println(DurationUtilities.toHumanString(totalDuration));
		assertEquals(DurationUtilities.createDuration(0, 14, 8), totalDuration);
	}

///////////////////////////////////////////////////////////////////////////////

	private static void printBundles(Queue<Bundle> bundles, Function<Bundle, Object> extraInfoPrinter) {
		System.out.println("---------------");
		System.out.println(bundles.stream() //
				.map(b -> b.getName() + ": " + extraInfoPrinter.apply(b)) //
				.collect(Collectors.joining("\n"))); //
	}

	private static void printPlaylists(Queue<Playlist> playlists, Function<Playlist, Object> extraInfoPrinter) {
		System.out.println("---------------");
		System.out.println(playlists.stream() //
				.map(p -> p.getName() + ": " + extraInfoPrinter.apply(p)) //
				.collect(Collectors.joining("\n"))); //
	}

	private static void printTracks(Queue<Track> tracks, Function<Track, Object> extraInfoPrinter) {
		System.out.println("---------------");
		System.out.println(tracks.stream() //
				.map(t -> t.getTitle() + ": " + extraInfoPrinter.apply(t)) //
				.collect(Collectors.joining("\n"))); //
	}
}
