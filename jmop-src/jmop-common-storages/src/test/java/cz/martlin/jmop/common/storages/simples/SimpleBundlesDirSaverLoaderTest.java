package cz.martlin.jmop.common.storages.simples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.fs.DefaultFileSystemAccessor;
//import cz.martlin.jmop.common.storages.locators.AbstractBundlesDirLocatorWithMusicdataFiles;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.musicdatasaver.BaseMusicdataSaver.SaveReason;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.extensions.TestingRootDirExtension;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

@Disabled
@Deprecated
class SimpleBundlesDirSaverLoaderTest {

	@RegisterExtension
	public TestingRootDirExtension root = new TestingRootDirExtension(this);

	@RegisterExtension
	public TestingMusicdataExtension tmb;

	private /*final*/ SimpleBundlesDirListerLoader loader;
	private /*final*/ SimpleBundlesDirSaver saver;

	public SimpleBundlesDirSaverLoaderTest() {
		BaseMusicbaseModifing musicbase = new DefaultInMemoryMusicbase();
//		tmb = new TestingMusicdataExtension(musicbase, true);

		BaseFileSystemAccessor fs = new DefaultFileSystemAccessor();
		BaseMusicdataFileManipulator manipulator = new SimpleMusicdataFileManipulator(fs);
//		BaseBundlesDirLocator locator = new AbstractBundlesDirLocatorWithMusicdataFiles( //
//				TestingRootDir.getFile(), fs, manipulator, "txt") {
//
//			@Override
//			protected String bundleTracksFileName(String bundleName) {
//				return "bundle-tracks.txt";
//			}
//
//			@Override
//			protected String bundleDataFileName(String bundleName) {
//				return "bundle-data.txt";
//			}
//		};
//		loader = new SimpleBundlesDirListerLoader(TestingRootDir.getFile(), fs, locator);
//		saver = new SimpleBundlesDirSaver(fs, locator);
	}

	@Test
	void testSaveAndLoad() throws JMOPPersistenceException {
//		saver.saveBundleData(tmb.tm.daftPunk, SaveReason.CREATED);
//		saver.savePlaylistData(tmb.tm.discovery, SaveReason.CREATED);

		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();
//		loader.load(musicbase);
		MusicbaseDebugPrinter.print(musicbase);

		Bundle actualDaftPunk = musicbase.bundles().stream().findAny().get();
		assertEquals("Daft punk", actualDaftPunk.getName());
	}

}
