package cz.martlin.jmop.common.musicbase.dflt;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.common.storages.dflt.X_DefaultStorage;
import cz.martlin.jmop.common.storages.utils.LoggingMusicbaseStorage;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.common.testing.extensions.TestingRootDirExtension;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.TestingDataCreator;
import cz.martlin.jmop.common.testing.testdata.TestingMusicdataWithMusicbase;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

@Deprecated
public class DefaultMusicbaseWithTestingMusicbaseTest {

	@RegisterExtension
	public TestingRootDirExtension root = new TestingRootDirExtension(this);
	
	public class DefaultConfig implements BaseDefaultStorageConfig {

		@Override
		public String getAllTracksPlaylistName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TrackFileFormat trackFileFormat() {
			// TODO Auto-generated method stub
			return null;
		}


	}
	
	@Test
	void test() throws Exception {
		BaseMusicbase musicbase = prepareMusicbase();
		
		try (AbstractTestingMusicdata tmb = new TestingMusicdataWithMusicbase(musicbase, true)) {
			assertNotNull(tmb.daftPunk);
			assertNotNull(tmb.londonElektricity);
			assertNotNull(tmb.cocolinoDeep);
			assertNotNull(tmb.robick);
		}
		
		musicbase.load();
	}
	
	private BaseMusicbase prepareMusicbase() {
		//TODO copied from DefaultMusicbaseTest

		BaseInMemoryMusicbase inmemory = new DefaultInMemoryMusicbase();

		X_DefaultStorage storage = X_DefaultStorage.create(root.getRoot(), new DefaultConfig(), new SimpleErrorReporter(), inmemory);
		LoggingMusicbaseStorage logging = new LoggingMusicbaseStorage(storage);

		BaseMusicbase musicbase = new PersistentMusicbase(inmemory, logging);

		System.out.println("Musicbase ready: " + musicbase);
		System.out.println("Working with " + root.getRoot().getAbsolutePath());

		return musicbase;
	}
}
