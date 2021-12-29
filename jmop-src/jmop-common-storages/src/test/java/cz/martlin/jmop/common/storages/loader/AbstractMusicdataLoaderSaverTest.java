package cz.martlin.jmop.common.storages.loader;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.fs.DefaultFileSystemAccessor;
import cz.martlin.jmop.common.storages.musicdatasaver.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.musicdatasaver.BaseMusicdataSaver.SaveReason;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.extensions.TestingRootDirExtension;
import cz.martlin.jmop.common.testing.resources.TestingRootDir;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public abstract class AbstractMusicdataLoaderSaverTest {
	private final BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();

	@RegisterExtension
	public TestingRootDirExtension root = new TestingRootDirExtension(this);

	@RegisterExtension
	public TestingMusicdataExtension tme = TestingMusicdataExtension.withMusicbase(() -> musicbase, TrackFileFormat.MP3);

	private BaseMusicdataSaver saver;
	private BaseMusicdataLoader loader;

	private BaseFileSystemAccessor fs;

	@BeforeEach
	public void before() {
		File root = this.root.getFile();
		Entry<BaseMusicdataSaver, BaseMusicdataLoader> las = createLoaderAndSaver(root, musicbase);

		saver = las.getKey();
		loader = las.getValue();
		
		fs = new DefaultFileSystemAccessor();
	}

	protected abstract Entry<BaseMusicdataSaver, BaseMusicdataLoader> createLoaderAndSaver(File root,
			BaseInMemoryMusicbase inmemory);

	/////////////////////////////////////////////////////////////////

	@Test
	void checkEnvironment() {
		assertNotNull(saver);
		assertNotNull(loader);
	}
	

	@Test
	void testCreateAndLoadBundle() throws JMOPPersistenceException {
		// save
		saver.saveBundleData(tme.tmd.daftPunk, SaveReason.CREATED);
		
		// check load
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		loader.load(inmemory);
		
		Bundle loadedDaftPunk = inmemory.bundles().stream().findAny().get();
		System.out.println(loadedDaftPunk);
		assertEquals(tme.tmd.daftPunk, loadedDaftPunk);
	}
	
	@Test
	void testCreateAndLoadPlaylist() throws JMOPPersistenceException {
		// save
		saver.saveBundleData(tme.tmd.cocolinoDeep, SaveReason.CREATED);
		saver.savePlaylistData(tme.tmd.seventeen, SaveReason.CREATED);
		
		// check load
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		loader.load(inmemory);
		
		Bundle loadedCocolinoDeep = inmemory.bundles().stream().findAny().get();
		Playlist loadedSeventeen = inmemory.playlists(loadedCocolinoDeep).stream().findAny().get();
		
		System.out.println(loadedSeventeen);
		assertEquals(tme.tmd.seventeen, loadedSeventeen);
	}

	@Test
	void testCreateAndLoadTrack() throws JMOPPersistenceException {
		// save
		saver.saveBundleData(tme.tmd.robick, SaveReason.CREATED);
		saver.saveTrackData(tme.tmd.ladyCarneval, SaveReason.CREATED);
		
		// check load
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		loader.load(inmemory);
		
		Bundle loadedRobick = inmemory.bundles().stream().findAny().get();
		Track loadedLadyCarneval = inmemory.tracks(loadedRobick).stream().findAny().get();
		
		System.out.println(loadedLadyCarneval);
		assertEquals(tme.tmd.ladyCarneval, loadedLadyCarneval);
	}
	
	//TODO add all the move/rename/remove/update test methods here
	/////////////////////////////////////////////////////////////////
	
	protected void createDir(String name) {
		File dirFile = new File(root.getFile(), name);
		
		try {
			fs.createDirectory(dirFile);
		} catch (JMOPPersistenceException e) {
			assumeTrue(e == null, "Could not create dir: " + dirFile.getAbsolutePath());
		}
	}
	
}
