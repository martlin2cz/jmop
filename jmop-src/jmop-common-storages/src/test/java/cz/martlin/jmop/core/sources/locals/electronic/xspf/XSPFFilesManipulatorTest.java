package cz.martlin.jmop.core.sources.locals.electronic.xspf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.rules.TemporaryFolder;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.xpfs.XSPFFilesManipulator;
import cz.martlin.jmop.common.utils.TestingDataCreator;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class XSPFFilesManipulatorTest {

	@TempDir
	public File basedir;
	private final XSPFFilesManipulator manipulator = new XSPFFilesManipulator();

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testPlaylist() throws JMOPSourceException {
		Playlist playlist = prepare();
		Bundle bundle = playlist.getBundle();

		File playlistFile = new File(basedir, "playlist.xspf");
		manipulator.saveOnlyPlaylist(playlist, playlistFile);
		assertTrue(playlistFile.exists());

		Playlist loaded = manipulator.loadOnlyPlaylist(bundle, playlistFile);
		assertEquals(playlist.toString(), loaded.toString());
		assertEquals(playlist, loaded);
	}

	@Test
	public void testBundle() throws JMOPSourceException {
		Playlist playlist = prepare();
		Bundle bundle = playlist.getBundle();

		File bundleFile = new File(basedir, "bundle.xspf");
		manipulator.savePlaylistWithBundle(playlist, bundleFile);
		assertTrue(bundleFile.exists());

		Bundle loaded = manipulator.loadOnlyBundle(bundleFile);
		assertEquals(bundle.toString(), loaded.toString());
		assertEquals(bundle, loaded);
	}

	///////////////////////////////////////////////////////////////////////////

	private Playlist prepare() {
		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();
		Bundle bundle = TestingDataCreator.bundle(musicbase);
		Playlist playlist = TestingDataCreator.playlist(musicbase, bundle);
		Track trackFirst = TestingDataCreator.track(musicbase, bundle, "first track");
		Track trackSecond = TestingDataCreator.track(musicbase, bundle, "second track");

		playlist.addTrack(trackFirst);
		playlist.addTrack(trackSecond);
		return playlist;
	}

}
