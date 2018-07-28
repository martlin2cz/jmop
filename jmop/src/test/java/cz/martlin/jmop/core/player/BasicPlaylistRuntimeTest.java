package cz.martlin.jmop.core.player;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

public class BasicPlaylistRuntimeTest {

	private final Bundle bundle = new Bundle(SourceKind.YOUTUBE, "testing bundle");
	private final Duration duration = DurationUtilities.createDuration(0, 10, 11);

	private final Track trackFoo = bundle.createTrack("foo", "Foo", "foo bar", duration);
	private final Track trackBar = bundle.createTrack("bar", "Bar", "bar baz", duration);
	private final Track trackBaz = bundle.createTrack("baz", "Baz", "baz aux", duration);
	private final Track trackAux = bundle.createTrack("aux", "Aux", "aux qux", duration);
	private final Track trackQux = bundle.createTrack("Qux", "Qux", "qux qux", duration);

	@Before
	public void setUp() {
		// okay
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testSimplyCreateAndAppend() {

		BasicPlaylistRuntime runtime = new BasicPlaylistRuntime(trackFoo, trackBar);

		checkPlayed(runtime);
		checkCurrent(runtime, null);
		checkRemaining(runtime, trackFoo, trackBar);

		runtime.append(trackAux);
		runtime.append(trackQux);

		checkPlayed(runtime);
		checkCurrent(runtime, null);
		checkRemaining(runtime, trackFoo, trackBar, trackAux, trackQux);
	}

	@Test
	public void testPlayAndStop() {

		BasicPlaylistRuntime runtime = new BasicPlaylistRuntime(trackFoo, trackBar, trackBaz);

		checkPlayed(runtime);
		checkCurrent(runtime, null);
		checkRemaining(runtime, trackFoo, trackBar, trackBaz);

		runtime.startToPlay();

		checkPlayed(runtime);
		checkCurrent(runtime, trackFoo);
		checkRemaining(runtime, trackBar, trackBaz);

		runtime.stopPlaying();

		checkPlayed(runtime);
		checkCurrent(runtime, null);
		checkRemaining(runtime, trackFoo, trackBar, trackBaz);

	}

	@Test
	public void testToNextIfPlayed() {

		BasicPlaylistRuntime runtime = new BasicPlaylistRuntime(trackFoo, trackBar, trackBaz);

		checkPlayed(runtime);
		checkCurrent(runtime, null);
		checkRemaining(runtime, trackFoo, trackBar, trackBaz);

		runtime.startToPlay();

		checkPlayed(runtime);
		checkCurrent(runtime, trackFoo);
		checkRemaining(runtime, trackBar, trackBaz);

		runtime.toNext();

		checkPlayed(runtime, trackFoo);
		checkCurrent(runtime, trackBar);
		checkRemaining(runtime, trackBaz);

		runtime.toNext();

		checkPlayed(runtime, trackFoo, trackBar);
		checkCurrent(runtime, trackBaz);
		checkRemaining(runtime);
	}

	@Test
	public void testToPrevIfPlayed() {

		BasicPlaylistRuntime runtime = new BasicPlaylistRuntime(trackFoo, trackBar, trackBaz);

		runtime.startToPlay();
		runtime.toNext();
		runtime.toNext();

		checkPlayed(runtime, trackFoo, trackBar);
		checkCurrent(runtime, trackBaz);
		checkRemaining(runtime);

		runtime.toPrevious();

		checkPlayed(runtime, trackFoo);
		checkCurrent(runtime, trackBar);
		checkRemaining(runtime, trackBaz);

		runtime.toPrevious();

		checkPlayed(runtime);
		checkCurrent(runtime, trackFoo);
		checkRemaining(runtime, trackBar, trackBaz);
	}

	@Test
	public void testToNextIfNotPlayed() {

		BasicPlaylistRuntime runtime = new BasicPlaylistRuntime(trackFoo, trackBar, trackBaz);

		checkPlayed(runtime);
		checkCurrent(runtime, null);
		checkRemaining(runtime, trackFoo, trackBar, trackBaz);

		runtime.toNext();

		checkPlayed(runtime);
		checkCurrent(runtime, trackFoo);
		checkRemaining(runtime, trackBar, trackBaz);

		runtime.toNext();

		checkPlayed(runtime, trackFoo);
		checkCurrent(runtime, trackBar);
		checkRemaining(runtime, trackBaz);
	}

	@Test
	public void testToPrevIfNotPlayed() {

		BasicPlaylistRuntime runtime = new BasicPlaylistRuntime(trackFoo, trackBar, trackBaz, trackAux);
		
		runtime.startToPlay();
		runtime.toNext();
		runtime.toNext();
		runtime.toNext();
		runtime.stopPlaying();

		// no one can simply push all tracks to played
		checkPlayed(runtime, trackFoo, trackBar, trackBaz);
		checkCurrent(runtime, null);
		checkRemaining(runtime, trackAux);

		runtime.toPrevious();

		checkPlayed(runtime, trackFoo, trackBar);
		checkCurrent(runtime, trackBaz);
		checkRemaining(runtime, trackAux);

		runtime.toPrevious();

		checkPlayed(runtime, trackFoo);
		checkCurrent(runtime, trackBar);
		checkRemaining(runtime, trackBaz, trackAux);

	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void checkCurrent(BasicPlaylistRuntime runtime, Track track) {
		assertEquals("Current mismatch", track, runtime.getCurrent());

	}

	private void checkRemaining(BasicPlaylistRuntime runtime, Track... tracks) {
		List<Track> expectedRemaining = Arrays.asList(tracks);
		assertEquals("Remaining mismatch", expectedRemaining, runtime.getRemaining());
	}

	private void checkPlayed(BasicPlaylistRuntime runtime, Track... tracks) {
		List<Track> expectedPlayed = Arrays.asList(tracks);
		assertEquals("Played mismatch", expectedPlayed, runtime.getPlayed());

	}
}
