package cz.martlin.jmop.common.storages.builders;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.builders.LocatorsBuilder.BundleDataFile;
import cz.martlin.jmop.common.storages.builders.MusicdataManipulatorBuilder.PlaylistFileFormat;
import cz.martlin.jmop.common.storages.builders.StorageBuilder.DirsLayout;
import cz.martlin.jmop.common.storages.configs.BaseStorageConfiguration;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.common.testing.extensions.TestingRootDirExtension;
import cz.martlin.jmop.common.testing.resources.TestingRootDir;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.TestingMusicdataWithMusicbase;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * @deprecated Replaced by {@link BuiltStoragesTests}.
 * @author martin
 *
 */
@Deprecated
class StorageBuilderTest {

	public class TestingConfig implements BaseStorageConfiguration, BaseDefaultStorageConfig {

		@Override
		public String getAllTracksPlaylistName() {
			return "all the tracks";
		}

		@Override
		public TrackFileFormat trackFileFormat() {
			return TrackFileFormat.MP3;
		}

	}

	@RegisterExtension
	public TestingRootDirExtension root = new TestingRootDirExtension(this);
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() throws Exception {
		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();
		
		BaseStorageConfiguration config = new TestingConfig();
		BaseErrorReporter reporter = new SimpleErrorReporter();
		
		StorageBuilder builder = new StorageBuilder();
		BaseMusicbaseStorage storage = builder.create(DirsLayout.BUNDLES_DIR, BundleDataFile.ALL_TRACKS_PLAYLIST, false, PlaylistFileFormat.XSPF, reporter, root.getRoot(), config, TrackFileFormat.MP3, inmemory);
		System.out.println(storage);
		
		BaseMusicbaseModifing musicbase = new PersistentMusicbase(inmemory, storage);
		AbstractTestingMusicdata tmb = new TestingMusicdataWithMusicbase(musicbase, TrackFileFormat.MP3);
		
		System.out.println(tmb);
		
//		tmb.close(); // or not
	}

}
