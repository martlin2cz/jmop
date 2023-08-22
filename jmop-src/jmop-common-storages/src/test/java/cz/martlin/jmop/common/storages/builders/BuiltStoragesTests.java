package cz.martlin.jmop.common.storages.builders;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.storages.builder.LocatorsBuilder.BundleDataFile;
import cz.martlin.jmop.common.storages.builder.MusicdataManipulatorBuilder.PlaylistFileFormat;
import cz.martlin.jmop.common.storages.builder.StorageBuilder;
import cz.martlin.jmop.common.storages.builder.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.components.BaseStorageConfiguration;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

public class BuiltStoragesTests {
	
	@Nested
	public static class ATP_XSPF_BundlesDir_StorageTest extends AbstractBuiltStorageTest {

		@Override
		protected BaseStorageConfiguration prepareTheConfig() {
			return new GenericTestingConstantConfig();
		}

		@Override
		protected BaseMusicbaseStorage buildTheStorage(File root, BaseInMemoryMusicbase inmemory, BaseStorageConfiguration config,
				BaseErrorReporter reporter) {
			
			StorageBuilder builder = new StorageBuilder();
			return builder.create(DirsLayout.BUNDLES_DIR, BundleDataFile.ALL_TRACKS_PLAYLIST, false, PlaylistFileFormat.XSPF, reporter, root, config, inmemory);
		}
		
		@Override
		protected void checkStoredTestingMusicbase(TestingMusicdataExtension musicdata) {
			checkIsDir("Daft Punk");
			checkIsFile("London Elektricity", "Just One Second.mp3");
			checkIsFile("Cocolino deep", "Seventeen.xspf");
			checkIsFile("Robick", GenericTestingConstantConfig.ATP_NAME + ".xspf");
		}		
		
		@Test
		void testAllTracksPlaylistIsPresent() {
			checkIsFile("Daft Punk", GenericTestingConstantConfig.ATP_NAME + ".xspf");
			
			BaseInMemoryMusicbase loaded = tryLoad();
			
			Bundle daftPunk = loaded.bundles().stream().filter(b -> b.getName().equals("Daft Punk")).findAny().get();
			Playlist daftPunkATP = loaded.playlists(daftPunk).stream() //
					.filter(p -> p.getName().equals(GenericTestingConstantConfig.ATP_NAME)) //
					.findAny().get();
			
			assertNotNull(daftPunkATP);
			
		}
	}

	@Nested
	public static class SimpleBundleData_SimplePlaylist_BundlesDir_StorageTest extends AbstractBuiltStorageTest {

		@Override
		protected BaseStorageConfiguration prepareTheConfig() {
			return new GenericTestingConstantConfig();
		}

		@Override
		protected BaseMusicbaseStorage buildTheStorage(File root, BaseInMemoryMusicbase inmemory, BaseStorageConfiguration config,
				BaseErrorReporter reporter) {
			
			StorageBuilder builder = new StorageBuilder();
			return builder.create(DirsLayout.BUNDLES_DIR, BundleDataFile.SIMPLE, false, PlaylistFileFormat.TXT, reporter, root, config, inmemory);
		}
		
		@Override
		protected void checkStoredTestingMusicbase(TestingMusicdataExtension musicdata) {
			checkIsDir("Daft Punk");
			checkIsFile("London Elektricity", "Just One Second.mp3");
			checkIsFile("Cocolino deep", "Seventeen.txt");
			checkIsFile("Robick", "bundle.txt");
		}		
		
		@Disabled("Simple storage doesn't store metadata")
		@Override
		void testCreateAndLoadSimplyCheckWithMetadata() throws Exception {
			super.testCreateAndLoadSimplyCheckWithMetadata();
		}
		
		@Disabled("Simple storage doesn't store metadata")
		@Override
		void testCreateLoadAndCheckEquality() throws Exception {
			super.testCreateLoadAndCheckEquality();
		}
	}
	
	
	@Nested
	@Disabled
	public static class ATP_XSPF_AllInOneDir_StorageTest extends AbstractBuiltStorageTest {

		@Override
		protected BaseStorageConfiguration prepareTheConfig() {
			return new GenericTestingConstantConfig();
		}

		@Override
		protected BaseMusicbaseStorage buildTheStorage(File root, BaseInMemoryMusicbase inmemory, BaseStorageConfiguration config,
				BaseErrorReporter reporter) {
			
			StorageBuilder builder = new StorageBuilder();
			return builder.create(DirsLayout.ALL_IN_ONE_DIR, BundleDataFile.ALL_TRACKS_PLAYLIST, false, PlaylistFileFormat.XSPF, reporter, root, config, inmemory);
		}
		
		@Override
		protected void checkStoredTestingMusicbase(TestingMusicdataExtension musicdata) {
			checkIsFile("Just One Second.mp3");
			checkIsFile("Seventeen.xspf");
		}
	}
	
	
}
