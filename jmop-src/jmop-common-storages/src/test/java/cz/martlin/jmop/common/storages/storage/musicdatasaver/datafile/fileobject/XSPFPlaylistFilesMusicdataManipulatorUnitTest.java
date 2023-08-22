package cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.XSPFPlaylistFilesMusicdataManipulator;
import cz.martlin.jmop.common.testing.extensions.TestingRootDirExtension;
import cz.martlin.jmop.common.testing.resources.TestingTracksSource;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.SimpleTestingMusicdata;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class XSPFPlaylistFilesMusicdataManipulatorUnitTest {
	
	@RegisterExtension
	public final TestingRootDirExtension root = new TestingRootDirExtension(this);
	
	private final AbstractTestingMusicdata tmb;
	private final TestingTracksSource tracksSource;

	private final  XSPFPlaylistFilesMusicdataManipulator manipulator;
	

	
	public XSPFPlaylistFilesMusicdataManipulatorUnitTest() {
		this.tmb = new SimpleTestingMusicdata();
		this.tracksSource = new TestingTracksSource(TrackFileFormat.MP3);

		BaseErrorReporter reporter = new SimpleErrorReporter();
		this.manipulator =  XSPFPlaylistFilesMusicdataManipulator.createFailsave(reporter);
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

		File rootDir = root.getFile();
		File playlistFile = new File(rootDir , "playlist.xspf");
		System.out.println("Working with: " + playlistFile);
		
		manipulator.savePlaylistData(tmb.discovery, playlistFile);
		assertTrue(playlistFile.exists());

		Map<String, Track> tracks = Map.of( //
				tmb.aerodynamic.getTitle(), tmb.aerodynamic, // 
				tmb.verdisQuo.getTitle(), tmb.verdisQuo,  //
				tmb.oneMoreTime.getTitle(), tmb.oneMoreTime); //
		
		Playlist loaded = manipulator.loadPlaylistData(tmb.daftPunk, tracks, playlistFile);
		assertEquals(tmb.discovery.toString(), loaded.toString());
		assertEquals(tmb.discovery, loaded);
	}

	@Test
	public void testBundle() throws JMOPPersistenceException  {
		File rootDir = root.getFile();
		File bundleFile = new File(rootDir, "bundle.xspf");
		System.out.println("Working with: " + bundleFile);
		
		Set<Track> tracksSet = Set.of(tmb.oneMoreTime, tmb.verdisQuo, tmb.aerodynamic, tmb.getLucky);
		manipulator.saveBundleData(tmb.daftPunk, tracksSet, bundleFile);
		assertTrue(bundleFile.exists());

		Bundle loadedBundle = manipulator.loadBundleData(bundleFile);
		assertEquals(tmb.daftPunk.toString(), loadedBundle.toString());
		assertEquals(tmb.daftPunk, loadedBundle);
		
		Set<Track> loadedTracks = manipulator.loadBundleTracks(bundleFile, tmb.daftPunk);
		System.out.println(loadedTracks);
		
		//TODO equals sets
//		assertEquals(tracksSet.toString(), loadedTracks.toString());
		assertEquals(new TreeSet<>(tracksSet), new TreeSet<>(loadedTracks));
	}

	
	
	///////////////////////////////////////////////////////////////////////////



}
