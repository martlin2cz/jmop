package cz.martlin.jmop.common.musicbase.dflt;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.PersistentMusicbase;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.common.storages.dflt.DefaultStorage;
import cz.martlin.jmop.common.storages.utils.LoggingMusicbaseStorage;
import cz.martlin.jmop.common.utils.TestingMusicbase;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class DefaultMusicbaseWithTestingMusicbaseTest {

	public static File root = new File(System.getProperty("java.io.tmpdir"), "jmop");
	
	public class DefaultConfig implements BaseDefaultStorageConfig {

		@Override
		public TrackFileFormat getSaveFormat() {
			return 	TrackFileFormat.MP3;
		}

		@Override
		public String getAllTrackPlaylistName() {
			return "all tracks";
		}

	}
	
	@Test
	void test() throws Exception {
		BaseMusicbase musicbase = prepareMusicbase();
		
		try (TestingMusicbase tmb = new TestingMusicbase(musicbase, true)) {
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

		DefaultStorage storage = DefaultStorage.create(root, new DefaultConfig(), new SimpleErrorReporter(), inmemory);
		LoggingMusicbaseStorage logging = new LoggingMusicbaseStorage(storage);

		BaseMusicbase musicbase = new PersistentMusicbase(inmemory, logging);

		System.out.println("Musicbase ready: " + musicbase);
		System.out.println("Working with " + root.getAbsolutePath());

		return musicbase;
	}
}
