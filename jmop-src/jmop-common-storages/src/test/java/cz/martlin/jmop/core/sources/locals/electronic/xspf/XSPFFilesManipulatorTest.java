package cz.martlin.jmop.core.sources.locals.electronic.xspf;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.xpfs.XSPFPlaylistFilesManipulator;
import cz.martlin.jmop.common.utils.TestingMusicbase;
import cz.martlin.jmop.common.utils.TestingTracksSource;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

public class XSPFFilesManipulatorTest {

	private final TestingMusicbase tmb;
	private final TestingTracksSource tracks;

	
	//@TempDir
	//public File basedir;
	private final File basedir = new File("/tmp/jmop");
	private final XSPFPlaylistFilesManipulator manipulator;
	

	
	public XSPFFilesManipulatorTest() {
		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();
		this.tmb = new TestingMusicbase(musicbase, false);
		this.tracks = new TestingTracksSource(TrackFileFormat.MP3);

		this.manipulator = new XSPFPlaylistFilesManipulator( //
				new SimpleErrorReporter()); //
	}
	
///////////////////////////////////////////////////////////////////////////


	@BeforeEach
	public void before() {
		tmb.oneMoreTime.played(DurationUtilities.createDuration(0, 0, 30));
		tmb.verdisQuo.played(DurationUtilities.createDuration(0, 0, 40));
	}
	
	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testPlaylist() throws JMOPPersistenceException  {

		File playlistFile = new File(basedir, "playlist.xspf");
		System.out.println("Working with: " + playlistFile);
		
		manipulator.saveOnlyPlaylist(tmb.discovery, playlistFile, tracks);
		assertTrue(playlistFile.exists());

		Map<String, Track> tracks = Map.of( //
				tmb.aerodynamic.getTitle(), tmb.aerodynamic, // 
				tmb.verdisQuo.getTitle(), tmb.verdisQuo,  //
				tmb.oneMoreTime.getTitle(), tmb.oneMoreTime); //
		
		Playlist loaded = manipulator.loadOnlyPlaylist(tmb.daftPunk, tracks, playlistFile);
		assertEquals(tmb.discovery.toString(), loaded.toString());
		assertEquals(tmb.discovery, loaded);
	}

	@Test
	public void testBundle() throws JMOPPersistenceException  {
		File bundleFile = new File(basedir, "bundle.xspf");
		System.out.println("Working with: " + bundleFile);
		
		manipulator.savePlaylistWithBundle(tmb.discovery, bundleFile, tracks);
		assertTrue(bundleFile.exists());

		Bundle loaded = manipulator.loadOnlyBundle(bundleFile);
		assertEquals(tmb.daftPunk.toString(), loaded.toString());
		assertEquals(tmb.daftPunk, loaded);
	}

	///////////////////////////////////////////////////////////////////////////



}
