package cz.martlin.jmop.core.sources.locals.electronic.xspf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.io.TempDir;

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

	private Bundle bundle;
	private Playlist playlist;
	private Track trackFirst;
	private Track trackSecond;
	private BaseInMemoryMusicbase musicbase;

	@Before
	public void before() {
		musicbase = new DefaultInMemoryMusicbase();
		
		bundle = TestingDataCreator.bundle(musicbase);
		playlist = TestingDataCreator.playlist(musicbase, bundle);
		trackFirst = TestingDataCreator.track(musicbase, bundle, "first track");
		trackSecond = TestingDataCreator.track(musicbase, bundle, "second track");

		playlist.addTrack(trackFirst);
		playlist.addTrack(trackSecond);
		
		trackFirst.setMetadata(trackFirst.getMetadata().played().played());
	}
	
	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testPlaylist() throws JMOPSourceException {

		File playlistFile = new File(basedir, "playlist.xspf");
		manipulator.saveOnlyPlaylist(playlist, playlistFile);
		assertTrue(playlistFile.exists());

		Map<String, Track> tracks = Map.of(trackFirst.getTitle(), trackFirst, trackSecond.getTitle(), trackSecond);
		Playlist loaded = manipulator.loadOnlyPlaylist(bundle, tracks, playlistFile);
		assertEquals(playlist.toString(), loaded.toString());
		assertEquals(playlist, loaded);
	}

	@Test
	public void testBundle() throws JMOPSourceException {
		Bundle bundle = playlist.getBundle();

		File bundleFile = new File(basedir, "bundle.xspf");
		manipulator.savePlaylistWithBundle(playlist, bundleFile);
		assertTrue(bundleFile.exists());

		Bundle loaded = manipulator.loadOnlyBundle(bundleFile);
		assertEquals(bundle.toString(), loaded.toString());
		assertEquals(bundle, loaded);
	}

	///////////////////////////////////////////////////////////////////////////



}
