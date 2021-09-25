package cz.martlin.jmop.common.musicbase.dflt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.function.Supplier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.utils.TestingMusicbase;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.DurationUtilities;

class VerifiingInMemoryMusicbaseTest {

	
	private BaseInMemoryMusicbase musicbase;
	private TestingMusicbase tm;
	
//	private Bundle unknowns;
//	private Playlist worstTracks;
//	private Playlist untitledAlbum;
//	private Track differentDrum;
//	private Track badBuy;

	@BeforeEach
	public void before() {
		prepareTestingMusicbase();
//		prepareNonExistingMusicbase();
	}

	@AfterEach
	public void after() {
		terminateTestingMusicbase();
//		terminateNonexistingMusicbase();
	}

	private void prepareTestingMusicbase() {
		BaseInMemoryMusicbase delegee = new DefaultInMemoryMusicbase();
		musicbase = new VerifiingInMemoryMusicbase(delegee); //delegee; 
		tm = new TestingMusicbase(musicbase, false);
	}

//	private void prepareNonExistingMusicbase() {
//		unknowns = new Bundle("Unknowns", Metadata.createNew());
//
//		worstTracks = new Playlist(tm.londonElektricity, "Worst tracks", Metadata.createNew());
//		untitledAlbum = new Playlist(unknowns, "Untitled album", Metadata.createNew());
//
//		differentDrum = new Track(tm.londonElektricity, "DD", "Different drum", //
//				"Billion Dollar Gravy, 2003", DurationUtilities.createDuration(0, 7, 24), Metadata.createNew());
//
//		badBuy = new Track(unknowns, "BG", "Bad Guy", //
//				"I'm a baad guyy", DurationUtilities.createDuration(0, 4, 22), Metadata.createNew());
//	}

	private void terminateTestingMusicbase() {
		try {
			tm.close();
		} catch (Exception e) {
			assumeFalse(true, e.toString());
		}
	}

//	private void terminateNonexistingMusicbase() {
//		unknowns = null;
//
//		worstTracks = null;
//		untitledAlbum = null;
//
//		differentDrum = null;
//		badBuy = null;
//	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	void testJustTheTestingMusicbase() {
		// if this test passes, we know the VMMb alread works
	}

	@Test
	void testList() {
		Bundle unknowns = new Bundle("Unknowns", Metadata.createNew());
		
		assertOK(() -> musicbase.bundles());

		assertOK(() -> musicbase.playlists(tm.daftPunk));
		asrtFail(() -> musicbase.playlists(unknowns));

		assertOK(() -> musicbase.tracks(tm.daftPunk));
		asrtFail(() -> musicbase.tracks(unknowns));
	}

	@Test
	void testBundlesSimply() {
		Bundle deadmau5 = assertOK(() -> musicbase.createNewBundle("deadmau5"));
		asrtFail(() -> musicbase.createNewBundle("deadmau5"));

		assertOK(() -> musicbase.renameBundle(deadmau5, "The Deadmau5"));
		asrtFail(() -> musicbase.renameBundle(deadmau5, "The Deadmau5"));

		assertOK(() -> musicbase.removeBundle(deadmau5));
		asrtFail(() -> musicbase.removeBundle(deadmau5));
	}

	@Test
	void testPlaylistsSimply() {
		Playlist worstTracks = assertOK(() -> musicbase.createNewPlaylist(tm.londonElektricity, "Worst tracks"));
		asrtFail(() -> musicbase.createNewPlaylist(tm.londonElektricity, "Worst tracks"));

		assertOK(() -> musicbase.renamePlaylist(worstTracks, "okay tracks"));
		asrtFail(() -> musicbase.renamePlaylist(worstTracks, "okay tracks"));

		assertOK(() -> musicbase.removePlaylist(worstTracks));
		asrtFail(() -> musicbase.removePlaylist(worstTracks));
	}

	@Test
	void testTracksSimply() {
		TrackData data = new TrackData("DD", "Different Drum", "Billion Dollar Gravy",
				DurationUtilities.createDuration(0, 7, 42));

		Track differentDrum = assertOK(() -> musicbase.createNewTrack(tm.londonElektricity, data, null));
		asrtFail(() -> musicbase.createNewTrack(tm.londonElektricity, data, null));

		assertOK(() -> musicbase.renameTrack(differentDrum, "DiffDrum"));
		asrtFail(() -> musicbase.renameTrack(differentDrum, "DiffDrum"));

		assertOK(() -> musicbase.removeTrack(differentDrum));
		asrtFail(() -> musicbase.removeTrack(differentDrum));
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void assertOK(Executable run) {
		try {
			run.execute();
		} catch (Throwable e) {
			fail(e);
		}
	}

	private <T> T assertOK(Supplier<T> run) {
		try {
			return run.get();
		} catch (Throwable e) {
			fail(e);
			return null;
		}
	}

	private void asrtFail(Executable run) {
		Exception ex = assertThrows(JMOPRuntimeException.class, run);
		
		System.err.println(ex.getMessage());
	}
}
