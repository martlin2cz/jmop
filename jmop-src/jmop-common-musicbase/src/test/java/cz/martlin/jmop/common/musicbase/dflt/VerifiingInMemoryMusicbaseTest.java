package cz.martlin.jmop.common.musicbase.dflt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.function.Supplier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class VerifiingInMemoryMusicbaseTest {
	
	private BaseInMemoryMusicbase musicbase;
	
	@RegisterExtension
	public TestingMusicdataExtension tme;
	
	public VerifiingInMemoryMusicbaseTest() {
		BaseInMemoryMusicbase delegee = new DefaultInMemoryMusicbase();
		musicbase = new VerifiingInMemoryMusicbase(delegee); //delegee; 
		tme = TestingMusicdataExtension.withMusicbase(() -> musicbase, TrackFileFormat.MP3);
	}
	
//	private Bundle unknowns;
//	private Playlist worstTracks;
//	private Playlist untitledAlbum;
//	private Track differentDrum;
//	private Track badBuy;

	///////////////////////////////////////////////////////////////////////////

	@Test
	void testJustTheTestingMusicbase() {
		// if this test passes, we know the VMMb alread works
	}

	@Test
	void testList() {
		Bundle unknowns = new Bundle("Unknowns", Metadata.createNew());
		
		assertOK(() -> musicbase.bundles());

		assertOK(() -> musicbase.playlists(tme.tmd.daftPunk));
		asrtFail(() -> musicbase.playlists(unknowns));

		assertOK(() -> musicbase.tracks(tme.tmd.daftPunk));
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
		Playlist worstTracks = musicbase.createNewPlaylist(tme.tmd.londonElektricity, "Worst tracks");
		asrtFail(() -> musicbase.createNewPlaylist(tme.tmd.londonElektricity, "Worst tracks"));

		assertOK(() -> musicbase.renamePlaylist(worstTracks, "okay tracks"));
		asrtFail(() -> musicbase.renamePlaylist(worstTracks, "okay tracks"));

		assertOK(() -> musicbase.removePlaylist(worstTracks));
		asrtFail(() -> musicbase.removePlaylist(worstTracks));
	}

	@Test
	void testTracksSimply() {
		TrackData data = new TrackData("Different Drum", "Billion Dollar Gravy",
				DurationUtilities.createDuration(0, 7, 42), null, null);

		Track differentDrum = musicbase.createNewTrack(tme.tmd.londonElektricity, data, TrackFileCreationWay.NO_FILE, null);
		asrtFail(() -> musicbase.createNewTrack(tme.tmd.londonElektricity, data, TrackFileCreationWay.NO_FILE, null));

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
