package cz.martlin.jmop.common.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.TestingMusicdataWithMusicbase;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

class TestingMusicbaseTest {

	@Test
	void test() throws Exception {
		BaseInMemoryMusicbase musicbase = new DefaultInMemoryMusicbase();

		try (AbstractTestingMusicdata tmb = new TestingMusicdataWithMusicbase(musicbase, TrackFileFormat.WAV)) {

			assertNotNull(tmb.daftPunk);
			assertNotNull(tmb.londonElektricity);
			assertNotNull(tmb.cocolinoDeep);
			assertNotNull(tmb.robick);

			MusicbaseDebugPrinter.print(musicbase);
		}

	}

}
