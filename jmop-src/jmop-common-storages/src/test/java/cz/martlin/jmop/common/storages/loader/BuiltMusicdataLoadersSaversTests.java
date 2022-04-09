package cz.martlin.jmop.common.storages.loader;

import java.io.File;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;

import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.builders.GenericTestingConstantConfig;
import cz.martlin.jmop.common.storages.builders.LoaderBuilder;
import cz.martlin.jmop.common.storages.builders.LocatorsBuilder;
import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.BundleDataFile;
import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.Locators;
import cz.martlin.jmop.common.storages.builders.MusicdataManipulatorBuilder;
import cz.martlin.jmop.common.storages.builders.SaverBuilder;
import cz.martlin.jmop.common.storages.builders.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.configs.BaseStorageConfiguration;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.fs.DefaultFileSystemAccessor;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.musicdatasaver.BaseMusicdataSaver;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;

public class BuiltMusicdataLoadersSaversTests {

	//FIXME fix this test
	@Disabled("The test saves just bundle info, but loads all the storage, including playlists, "
			+ "hence failing on not-having mandatory playlist information avaiable")
	@Nested
	public static class BundlesDir_ATP_XSPF_LoaderSaverTest extends AbstractMusicdataLoaderSaverTest {

		@BeforeEach
		public void beforeTest() {
			createDir("Daft Punk");
			// London elektricity goes here
			createDir("Cocolino deep");
			createDir("Robick");
		}
		
		@Override
		protected Entry<BaseMusicdataSaver, BaseMusicdataLoader> createLoaderAndSaver(File root, BaseInMemoryMusicbase inmemory) {
			BaseFileSystemAccessor fs = new DefaultFileSystemAccessor();
			BaseStorageConfiguration config = new GenericTestingConstantConfig();

			BaseErrorReporter reporter = new SimpleErrorReporter();
			BaseMusicdataFileManipulator manipulator = new MusicdataManipulatorBuilder().createXSPFmanipulator(false, reporter);
			Locators locators = new LocatorsBuilder().build(root, DirsLayout.BUNDLES_DIR, BundleDataFile.ALL_TRACKS_PLAYLIST, config, "XSPF");
			BaseMusicdataLoader loader = new LoaderBuilder().createBundlesDir(locators, manipulator, fs, root, false, reporter);
			BaseMusicdataSaver saver = new SaverBuilder().create(manipulator, locators, inmemory);
			
			return new SimpleImmutableEntry<>(saver, loader);
		}
	}
	
	@Disabled
	@Nested
	public static class Simple_TXT_AllInOneDir_LoaderSaverTest extends AbstractMusicdataLoaderSaverTest {

		@Override
		protected Entry<BaseMusicdataSaver, BaseMusicdataLoader> createLoaderAndSaver(File root, BaseInMemoryMusicbase inmemory) {
			BaseFileSystemAccessor fs = new DefaultFileSystemAccessor();
			BaseStorageConfiguration config = new GenericTestingConstantConfig();
			BaseErrorReporter reporter = new SimpleErrorReporter();
			
			Locators locators = new LocatorsBuilder().build(root, DirsLayout.ALL_IN_ONE_DIR, BundleDataFile.SIMPLE, config, "TXT");
			BaseMusicdataFileManipulator manipulator = new MusicdataManipulatorBuilder().createSimpleManipulator(fs);
			
			BaseMusicdataLoader loader = new LoaderBuilder().create(DirsLayout.ALL_IN_ONE_DIR, root, false, locators, manipulator, fs, reporter );
			BaseMusicdataSaver saver = new SaverBuilder().create(manipulator, locators, inmemory);
			
			return new SimpleImmutableEntry<>(saver, loader);
		}
		
	}
}
