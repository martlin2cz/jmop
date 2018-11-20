package cz.martlin.jmop.core.playlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

public class PlaylistRuntimeTest {
	private final Bundle bundle = new Bundle(SourceKind.YOUTUBE, "testing bundle"); //$NON-NLS-1$
	private final Duration duration = DurationUtilities.createDuration(0, 10, 11);

	private final Track trackFoo = bundle.createTrack("foo", "Foo", "foo bar", duration); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private final Track trackBar = bundle.createTrack("bar", "Bar", "bar baz", duration); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private final Track trackBaz = bundle.createTrack("baz", "Baz", "baz aux", duration); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private final Track trackAux = bundle.createTrack("aux", "Aux", "aux qux", duration); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private final Track trackQux = bundle.createTrack("Qux", "Qux", "qux qux", duration); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	@Before
	public void setUp() throws Exception {
		System.out.println();
	}

	@Test
	public void testTracks() {

		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux, trackQux);
		PlaylistRuntime runtime = new PlaylistRuntime(tracks);

		check(runtime, null, trackFoo, trackBar);
		runtime.toNext();

		check(runtime, trackFoo, trackBar, trackBaz);
		runtime.toNext();

		check(runtime, trackBar, trackBaz, trackAux);
		runtime.toNext();

		check(runtime, trackBaz, trackAux, trackQux);
		runtime.toNext();

		check(runtime, trackAux, trackQux, null);
	}

	@Test
	public void testCounts() {
		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux, trackQux);
		PlaylistRuntime runtime = new PlaylistRuntime(tracks);

		check(runtime, 5, 0, 0, 4);
		runtime.toNext();

		check(runtime, 5, 1, 1, 3);
		runtime.toNext();

		check(runtime, 5, 2, 2, 2);
		runtime.toNext();

		check(runtime, 5, 3, 3, 1);
		runtime.toNext();

		check(runtime, 5, 4, 4, 0);
	}

	@Test
	public void testLists() {
		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux, trackQux);
		PlaylistRuntime runtime = new PlaylistRuntime(tracks);

		check(runtime, tracks, Arrays.asList(), trackFoo, Arrays.asList(trackBar, trackBaz, trackAux, trackQux));
		runtime.toNext();

		check(runtime, tracks, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackBaz, trackAux, trackQux));
		runtime.toNext();

		check(runtime, tracks, Arrays.asList(trackFoo, trackBar), trackBaz, Arrays.asList(trackAux, trackQux));
		runtime.toNext();

		check(runtime, tracks, Arrays.asList(trackFoo, trackBar, trackBaz), trackAux, Arrays.asList(trackQux));
		runtime.toNext();

		check(runtime, tracks, Arrays.asList(trackFoo, trackBar, trackBaz, trackAux), trackQux, Arrays.asList());
	}

	@Test
	public void testMarkPlayedUpTo() {
		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux, trackQux);
		PlaylistRuntime runtime = new PlaylistRuntime(tracks);

		runtime.markPlayedUpTo(1);
		check(runtime, tracks, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackBaz, trackAux, trackQux));

		runtime.markPlayedUpTo(1); // just identity
		check(runtime, tracks, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackBaz, trackAux, trackQux));

		runtime.markPlayedUpTo(3);
		check(runtime, tracks, Arrays.asList(trackFoo, trackBar, trackBaz), trackAux, Arrays.asList(trackQux));

		runtime.markPlayedUpTo(2); // go back
		check(runtime, tracks, Arrays.asList(trackFoo, trackBar), trackBaz, Arrays.asList(trackAux, trackQux));
	}

	@Test
	public void testPopUp() {
		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux, trackQux);
		PlaylistRuntime runtime = new PlaylistRuntime(tracks);

		runtime.toNext();
		runtime.toNext();

		List<Track> allTracks1 = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux, trackQux);
		check(runtime, allTracks1, Arrays.asList(trackFoo, trackBar), trackBaz, Arrays.asList(trackAux, trackQux));
		runtime.popUp(4);

		List<Track> allTracks2 = Arrays.asList(trackFoo, trackBar, trackQux, trackBaz, trackAux);
		check(runtime, allTracks2, Arrays.asList(trackFoo, trackBar), trackQux, Arrays.asList(trackBaz, trackAux));
		runtime.popUp(0);

		List<Track> allTracks3 = Arrays.asList(trackBar, trackFoo, trackQux, trackBaz, trackAux);
		check(runtime, allTracks3, Arrays.asList(trackBar), trackFoo, Arrays.asList(trackQux, trackBaz, trackAux));
		runtime.popUp(runtime.currentTrackIndex()); // just for duplicity

		List<Track> allTracks4 = Arrays.asList(trackBar, trackFoo, trackQux, trackBaz, trackAux);
		check(runtime, allTracks4, Arrays.asList(trackBar), trackFoo, Arrays.asList(trackQux, trackBaz, trackAux));
	}

	@Test
	public void testAppend() {
		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz);
		PlaylistRuntime runtime = new PlaylistRuntime(tracks);
		runtime.toNext();

		List<Track> allTracks1 = Arrays.asList(trackFoo, trackBar, trackBaz);
		check(runtime, allTracks1, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackBaz));
		runtime.append(trackAux);

		List<Track> allTracks2 = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux);
		check(runtime, allTracks2, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackBaz, trackAux));
		runtime.append(trackQux);

		List<Track> allTracks3 = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux, trackQux);
		check(runtime, allTracks3, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackBaz, trackAux, trackQux));
	}

	@Test
	public void testReplaceRest() {
		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux);
		PlaylistRuntime runtime = new PlaylistRuntime(tracks);

		runtime.toNext();

		List<Track> allTracks1 = Arrays.asList(trackFoo, trackBar, trackBaz, trackAux);
		check(runtime, allTracks1, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackBaz, trackAux));
		runtime.replaceRest(trackQux);

		List<Track> allTracks2 = Arrays.asList(trackFoo, trackBar, trackQux);
		check(runtime, allTracks2, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackQux));
		runtime.replaceRest(trackQux); // just identity

		List<Track> allTracks3 = Arrays.asList(trackFoo, trackBar, trackQux);
		check(runtime, allTracks3, Arrays.asList(trackFoo), trackBar, Arrays.asList(trackQux));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void check(PlaylistRuntime runtime, //
			Track expectedPreviousOrNull, Track expectedCurrent, Track expectedNextOrNull) {

		System.out.println("T: " + runtime); //$NON-NLS-1$
		if (expectedPreviousOrNull != null) {
			assertTrue(runtime.hasPlayed());
			Track expectedPrevious = runtime.lastWasPlayed();
			assertEquals(expectedPreviousOrNull, expectedPrevious);
		} else {
			assertFalse(runtime.hasPlayed());
		}

		Track actualCurrent = runtime.current();
		assertEquals(expectedCurrent, actualCurrent);

		if (expectedNextOrNull != null) {
			assertTrue(runtime.hasNextToPlay());
			Track actualNext = runtime.nextToBePlayed();
			assertEquals(expectedNextOrNull, actualNext);
		} else {
			assertFalse(runtime.hasNextToPlay());
		}
	}

	private void check(PlaylistRuntime runtime, //
			int expectedCount, int expectedCurrentIndex, int expectedPlayedCount, int expectedRemainingCount) {

		System.out.println("C: " + runtime); //$NON-NLS-1$

		assertEquals("Track count missmatch", expectedCount, runtime.count()); //$NON-NLS-1$
		assertEquals("Current index missmatch", expectedCurrentIndex, runtime.currentTrackIndex()); //$NON-NLS-1$
		assertEquals("Played count missmatch", expectedPlayedCount, runtime.playedCount()); //$NON-NLS-1$
		assertEquals("Remaining count missmatch", expectedRemainingCount, runtime.remainingCount()); //$NON-NLS-1$
	}

	private void check(PlaylistRuntime runtime, List<Track> expectedAllTracks, List<Track> expectedPlayedTracks,
			Track expectedCurrentTrack, List<Track> expectedToBePlayedTracks) {

		System.out.println("L: " + runtime); //$NON-NLS-1$

		assertEquals("All tracks missmatch", expectedAllTracks, runtime.listAll()); //$NON-NLS-1$
		assertEquals("Played tracks missmatch", expectedPlayedTracks, runtime.played()); //$NON-NLS-1$
		assertEquals("Current track missmatch", expectedCurrentTrack, runtime.current()); //$NON-NLS-1$
		assertEquals("Remaining tracks missmatch", expectedToBePlayedTracks, runtime.toBePlayed()); //$NON-NLS-1$
	}

}
