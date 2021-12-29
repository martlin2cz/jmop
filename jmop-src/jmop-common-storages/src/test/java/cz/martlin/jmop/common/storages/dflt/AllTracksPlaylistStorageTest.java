package cz.martlin.jmop.common.storages.dflt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.simples.InMemoryStorage;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * An test for the {@link AllTracksPlaylistStorage}.
 * 
 * @author martin
 *
 */
class AllTracksPlaylistStorageTest {

	private static final String ATP_PLAYLIST_NAME = "all the tracks";

	@RegisterExtension
	public final TestingMusicdataExtension tme;

	private final BaseMusicbase musicbase;

	public AllTracksPlaylistStorageTest() {

		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		BaseMusicbaseStorage storage = new InMemoryStorage(new DefaultInMemoryMusicbase());
		BaseMusicbaseStorage atps = new AllTracksPlaylistStorage(storage, inmemory, ATP_PLAYLIST_NAME);
		musicbase = new PersistentMusicbase(inmemory, atps);

		tme = TestingMusicdataExtension.withMusicbase(() -> musicbase, TrackFileFormat.MP3);
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testBundle() {
		// create bundle
		Bundle bundle = musicbase.createNewBundle("Lorem");

		// verify the ATP
		Playlist atp = getAllTracksPlaylist(bundle);
		assertNotNull(atp);

		// rename bundle
		musicbase.renameBundle(bundle, "Ipsum");

		// save updated bundle
		bundle.played(DurationUtilities.createDuration(0, 0, 1));
		musicbase.bundleUpdated(bundle);

		// remove bundle
		musicbase.removeBundle(bundle);
	}

	@Test
	void testPlaylist() {
		// NOTE: does nothing extra actually, but it's good to have it verified

		// create playlist
		Playlist playlist = musicbase.createNewPlaylist(tme.tmd.londonElektricity, "worst tracks");

		// rename playlist
		musicbase.renamePlaylist(playlist, "Tony");

		// move playlist
		musicbase.movePlaylist(playlist, tme.tmd.cocolinoDeep);

		// save updated playlist
		playlist.played(DurationUtilities.createDuration(0, 0, 2));
		musicbase.playlistUpdated(playlist);

		// remove playlist
		musicbase.removePlaylist(playlist);
	}

	@Test
	void testTracks() {
		Playlist daftPunkATP = getAllTracksPlaylist(tme.tmd.daftPunk);
		Playlist cocolinoATP = getAllTracksPlaylist(tme.tmd.cocolinoDeep);

		// create track
		TrackData data = new TrackData("id", "Harder faster stronger", "...", //
				DurationUtilities.createDuration(0, 4, 11));
		Track track = musicbase.createNewTrack(tme.tmd.daftPunk, data, TrackFileCreationWay.NO_FILE, null);

		assertTrue(daftPunkATP.getTracks().getTracks().contains(track));

		// rename track
		musicbase.renameTrack(track, "Faster stronger harder");
		assertTrue(daftPunkATP.getTracks().getTracks().contains(track));

		// move track
		musicbase.moveTrack(track, tme.tmd.cocolinoDeep);

		assertFalse(daftPunkATP.getTracks().getTracks().contains(track));
		assertTrue(cocolinoATP.getTracks().getTracks().contains(track));

		// save updated track
		track.played(DurationUtilities.createDuration(0, 0, 3));
		musicbase.trackUpdated(track);

		assertTrue(cocolinoATP.getTracks().getTracks().contains(track));

		// remove track
		musicbase.removeTrack(track);

		assertFalse(cocolinoATP.getTracks().getTracks().contains(track));
	}

/////////////////////////////////////////////////////////////////////////////////////

	private Playlist getAllTracksPlaylist(Bundle bundle) {
		return musicbase.playlists(bundle).stream() //
				.filter(p -> ATP_PLAYLIST_NAME.equals(p.getName())) //
				.findAny().orElse(null);
	}

}
