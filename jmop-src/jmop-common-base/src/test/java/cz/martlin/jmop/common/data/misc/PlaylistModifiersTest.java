package cz.martlin.jmop.common.data.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.TestingPrinter;

class PlaylistModifiersTest {

	private Bundle bundle;
	private Track fooTrack;
	private Track barTrack;
	private Track bazTrack;
	private Track auxTrack;
	private Track quxTrack;
	private Track quuxTrack;
	private Track quuuxTrack;

	@BeforeEach
	public void prepare() {
		bundle = new Bundle("testing", Metadata.createNew());

		fooTrack = track(bundle, "foo");
		barTrack = track(bundle, "bar");
		bazTrack = track(bundle, "baz");
		auxTrack = track(bundle, "aux");
		quxTrack = track(bundle, "qux");
		quuxTrack = track(bundle, "quux");
		quuuxTrack = track(bundle, "quuux");
	}

	@Test
	void testFind() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);
		modifier.append(barTrack);
		
		List<TrackIndex> indexesOfBar = modifier.find(barTrack);
		Iterable<?> expectedIndexes = List.of(TrackIndex.ofIndex(1), TrackIndex.ofIndex(5));
		assertIterableEquals(expectedIndexes, indexesOfBar);
	}
	
	@Test
	void testAppend() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		// append one
		modifier.append(quuxTrack);
		checkTracks(playlist, fooTrack, barTrack, bazTrack, auxTrack, quxTrack, quuxTrack);
		checkCurrent(playlist, 2, bazTrack);

		// append another
		modifier.append(quuuxTrack);
		checkTracks(playlist, fooTrack, barTrack, bazTrack, auxTrack, quxTrack, quuxTrack, quuuxTrack);
		checkCurrent(playlist, 2, bazTrack);
	}

	@Test
	void testInsert() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		// insert AFTER current
		modifier.insertBefore(quuxTrack, ti(4));
		checkTracks(playlist, fooTrack, barTrack, bazTrack, auxTrack, quuxTrack, quxTrack);
		checkCurrent(playlist, 2, bazTrack);

		// insert BEFORE current
		modifier.insertBefore(quuuxTrack, ti(2));
		checkTracks(playlist, fooTrack, barTrack, quuuxTrack, bazTrack, auxTrack, quuxTrack, quxTrack);
		checkCurrent(playlist, 3, bazTrack);
	}

	@Test
	void testRemove() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		// remove from AFTER current
		modifier.remove(ti(3));
		checkTracks(playlist, fooTrack, barTrack, bazTrack, quxTrack);
		checkCurrent(playlist, 2, bazTrack);

		// remove from BEFORE current
		modifier.remove(ti(1));
		checkTracks(playlist, fooTrack, bazTrack, quxTrack);
		checkCurrent(playlist, 1, bazTrack);

		// remove from CURRENT
		modifier.remove(ti(1));
		checkTracks(playlist, fooTrack, quxTrack);
		checkCurrent(playlist, 1, quxTrack);
	}

	@Test
	void testMove() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		// move from AFTER to BEFORE
		modifier.move(ti(3), ti(1));
		checkTracks(playlist, fooTrack, auxTrack, barTrack, bazTrack, quxTrack);
		checkCurrent(playlist, 3, bazTrack);

		// move from BEFORE to AFTER
		modifier.move(ti(0), ti(4));
		checkTracks(playlist, auxTrack, barTrack, bazTrack, fooTrack, quxTrack);
		checkCurrent(playlist, 2, bazTrack);

		// move CURRENT to AFTER
		modifier.move(ti(2), ti(4));
		checkTracks(playlist, auxTrack, barTrack, fooTrack, bazTrack, quxTrack);
		checkCurrent(playlist, 3, bazTrack);

		// move CURRENT to BEFORE
		modifier.move(ti(3), ti(0));
		checkTracks(playlist, bazTrack, auxTrack, barTrack, fooTrack, quxTrack);
		checkCurrent(playlist, 0, bazTrack);
	}

	@Test
	void testMoveToEnd() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		// move from BEFORE to END
		modifier.moveToEnd(ti(1));
		checkTracks(playlist, fooTrack, bazTrack, auxTrack, quxTrack, barTrack);
		checkCurrent(playlist, 1, bazTrack);

		// move from AFTER to END
		modifier.moveToEnd(ti(3));
		checkTracks(playlist, fooTrack, bazTrack, auxTrack, barTrack, quxTrack);
		checkCurrent(playlist, 1, bazTrack);

		// move CURRENT to END
		modifier.moveToEnd(ti(1));
		checkTracks(playlist, fooTrack, auxTrack, barTrack, quxTrack, bazTrack);
		checkCurrent(playlist, 4, bazTrack);
	}
	
	@Test
	void testShuffle() {
		Playlist playlist = playlist();
		Random random = new Random(42);
		ExtendedPlaylistModifier modifier = new ExtendedPlaylistModifier(playlist, random);

		// shake it, baby!
		modifier.shuffle(42);
		// run, verify the actual, deterministic order and update following lines:
		checkTracks(playlist, fooTrack, bazTrack, quxTrack, auxTrack, barTrack);
		checkCurrent(playlist, 1, bazTrack);
	}

	///////////////////////////////////////////////////////////////////////////

	private void checkTracks(Playlist playlist, Track... expectedTracks) {
		System.out.println(TestingPrinter.print(playlist));
		
		List<Track> actualTracksList = playlist.getTracks().getTracks();
		List<Track> expectedTracksList = Arrays.asList(expectedTracks);

		assertIterableEquals(expectedTracksList, actualTracksList);
	}

	private void checkCurrent(Playlist playlist, int expectedCurrentIndex, Track expectedCurrentTrack) {
		int actualCurrentIndex = playlist.getCurrentTrackIndex().getIndex();
		if (actualCurrentIndex >= playlist.getTracks().count()) {
			fail("No such current index: " + expectedCurrentIndex);
		}
		
		Track actualCurrentTrack = playlist.getTracks().getTrack(playlist.getCurrentTrackIndex());

		assertEquals(expectedCurrentIndex, actualCurrentIndex);
		assertEquals(expectedCurrentTrack, actualCurrentTrack);
	}

	///////////////////////////////////////////////////////////////////////////

	private Playlist playlist() {
		Playlist playlist = emptyPlaylist();

		playlist.getTracks().getTracks().add(fooTrack);
		playlist.getTracks().getTracks().add(barTrack);
		playlist.getTracks().getTracks().add(bazTrack);
		playlist.getTracks().getTracks().add(auxTrack);
		playlist.getTracks().getTracks().add(quxTrack);

		playlist.setCurrentTrackIndex(ti(2));

		checkTracks(playlist, fooTrack, barTrack, bazTrack, auxTrack, quxTrack);
		checkCurrent(playlist, 2, bazTrack);

		return playlist;
	}

	private Playlist emptyPlaylist() {
		Playlist playlist = new Playlist(bundle, "testing-playlist", Metadata.createNew());
		return playlist;
	}

	private Track track(Bundle bundle, String title) {
		Track fooTrack = new Track(bundle, title, title, title, DurationUtilities.createDuration(0, 3, 15),
				Metadata.createNew());
		return fooTrack;
	}

	

	private TrackIndex ti(int i) {
		return TrackIndex.ofIndex(i);
	}

}
