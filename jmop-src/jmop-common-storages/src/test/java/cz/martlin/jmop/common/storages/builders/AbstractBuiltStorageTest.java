package cz.martlin.jmop.common.storages.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.storages.configs.BaseStorageConfiguration;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.extensions.TestingRootDirExtension;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;

public abstract class AbstractBuiltStorageTest {
	
	@RegisterExtension
	public TestingRootDirExtension root = new TestingRootDirExtension(this);
	@RegisterExtension
	public TestingMusicdataExtension musicdata = TestingMusicdataExtension.withStorageAndMusicbase(this::createMusibase, this::createTheStorage, true);

	//TODO you would have to use musicdata.tbd.storage to obtain the storage instance.
	public AbstractBuiltStorageTest() {
	}
	
	///////////////////////////////////////////////////////////////////////////
	private BaseInMemoryMusicbase createMusibase() {
		return new DefaultInMemoryMusicbase();
	}
	
	private BaseMusicbaseStorage createTheStorage(BaseInMemoryMusicbase inmemory) {
		BaseStorageConfiguration config = prepareTheConfig();
		BaseErrorReporter reporter = new SimpleErrorReporter();
		
		File root = this.root.getRoot();
		return buildTheStorage(root, inmemory, config, reporter);
	}


	protected abstract BaseStorageConfiguration prepareTheConfig();

	protected abstract BaseMusicbaseStorage buildTheStorage(File root, BaseInMemoryMusicbase inmemory, BaseStorageConfiguration config,
			BaseErrorReporter reporter);


	///////////////////////////////////////////////////////////////////////////
	
	@Test
	void testJustCreateTestingMusicbase() throws Exception {
		System.out.println(musicdata);
		
		checkStoredTestingMusicbase(musicdata);
	}

	protected abstract void checkStoredTestingMusicbase(TestingMusicdataExtension musicdata);

	@Test
	void testCreateAndLoadSimplyCheck() throws Exception {
		BaseInMemoryMusicbase targetMusicbase = tryLoad();
		
		System.out.println(targetMusicbase);
		
		Bundle daftPunk = targetMusicbase.bundles().stream().filter(b -> b.getName().equals("Daft Punk")).findAny().get();
		assertEquals(daftPunk, musicdata.tmd.daftPunk);
		assertEquals(daftPunk.getMetadata(), musicdata.tmd.daftPunk.getMetadata());
		
		Playlist discovery = targetMusicbase.playlists(daftPunk).stream().filter(p -> p.getName().equals("Discovery")).findAny().get();
		assertEquals(discovery, musicdata.tmd.discovery);
		assertEquals(discovery.getTracks(), musicdata.tmd.discovery.getTracks());
		assertEquals(discovery.getMetadata(), musicdata.tmd.discovery.getMetadata());
		
		Track aerodynamic = targetMusicbase.tracks(daftPunk).stream().filter(t -> t.getTitle().equals("Aerodynamic")).findAny().get();
		assertEquals(aerodynamic, musicdata.tmd.aerodynamic);
		assertEquals(aerodynamic.getMetadata(), musicdata.tmd.aerodynamic.getMetadata());
	}

	@Test
	void testCreateLoadAndCheckEquality() throws Exception {
		BaseInMemoryMusicbase musicbase = (BaseInMemoryMusicbase) musicdata.getMusicbase();
		BaseInMemoryMusicbase targetMusicbase = tryLoad();
		
		assertEquals(musicbase.bundles(), targetMusicbase.bundles());
		
//		assertEquals(musicbase.toString(), targetMusicbase.toString());
		assertEquals(musicbase, targetMusicbase);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	protected BaseInMemoryMusicbase tryLoad() {
		BaseMusicbaseStorage storage = musicdata.getStorage();
		
		BaseInMemoryMusicbase targetMusicbase = new DefaultInMemoryMusicbase();
		storage.load(targetMusicbase);
	
		return targetMusicbase;
	}
	
	
	protected void checkIsFile(String ...segments) {
		String subpath = Arrays.stream(segments).collect(Collectors.joining(File.separator));
		File file = new File(root.getRoot(), subpath);
		assertTrue(file.isFile(), "The " + file.getAbsolutePath() + " does not exist or not a file.");		
	}
	
	protected void checkIsDir(String ...segments) {
		String subpath = Arrays.stream(segments).collect(Collectors.joining(File.separator));
		File file = new File(root.getRoot(), subpath);
		assertTrue(file.isDirectory(), "The " + file.getAbsolutePath() + " does not exist or not a dir.");		
	}
	
}
