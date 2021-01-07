package cz.martlin.jmop.common.data.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;

class PlaylistModifierTest {

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
	void testAppend() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		modifier.append(quuxTrack);
		checkTracks(playlist, fooTrack, barTrack, bazTrack, auxTrack, quxTrack, quuxTrack);
		checkCurrent(playlist, 2, bazTrack);
		
		modifier.append(quuuxTrack);
		checkTracks(playlist, fooTrack, barTrack, bazTrack, auxTrack, quxTrack, quuxTrack, quuuxTrack);
		checkCurrent(playlist, 2, bazTrack);
	}
	
	@Test
	void testInsert() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		modifier.insertBefore(quuxTrack, 4);
		checkTracks(playlist, fooTrack, barTrack, bazTrack, auxTrack, quuxTrack, quxTrack);
		checkCurrent(playlist, 2, bazTrack);
		
		modifier.insertBefore(quuuxTrack, 2);
		checkTracks(playlist, fooTrack, barTrack, quuuxTrack, bazTrack, auxTrack, quuxTrack, quxTrack);
		checkCurrent(playlist, 3, bazTrack);
	}
	
	@Test
	void testRemove() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		modifier.remove(3);
		checkTracks(playlist, fooTrack, barTrack, bazTrack, quxTrack);
		checkCurrent(playlist, 2, bazTrack);
		
		modifier.remove(1);
		checkTracks(playlist, fooTrack, bazTrack, quxTrack);
		checkCurrent(playlist, 1, bazTrack);
	}
	
	@Test
	void testMove() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		modifier.move(3, 1);
		checkTracks(playlist, fooTrack, auxTrack, barTrack, bazTrack, quxTrack);
		checkCurrent(playlist, 3, bazTrack);
	}

	@Test
	void testMoveToEnd() {
		Playlist playlist = playlist();
		PlaylistModifier modifier = new PlaylistModifier(playlist);

		modifier.moveToEnd(1);
		checkTracks(playlist, fooTrack, bazTrack, auxTrack, quxTrack, barTrack);
		checkCurrent(playlist, 1, bazTrack);
		
		modifier.moveToEnd(2);
		checkTracks(playlist, fooTrack, bazTrack,  quxTrack, barTrack, auxTrack);
		checkCurrent(playlist, 1, bazTrack);
	}

	

	///////////////////////////////////////////////////////////////////////////

	private void checkTracks(Playlist playlist, Track... expectedTracks) {
		List<Track> actualTracksList = playlist.getTracks().getTracks();
		List<Track> expectedTracksList = Arrays.asList(expectedTracks);
		
		assertIterableEquals(expectedTracksList, actualTracksList);
	}

	private void checkCurrent(Playlist playlist, int expectedCurrentIndex, Track expectedCurrentTrack) {
		int actualCurrentIndex = playlist.getCurrentTrackIndex();
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

		playlist.setCurrentTrackIndex(2);
		
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

}
