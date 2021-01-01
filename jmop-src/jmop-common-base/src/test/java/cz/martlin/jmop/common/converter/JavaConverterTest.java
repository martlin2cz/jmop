package cz.martlin.jmop.common.converter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.Test;

class JavaConverterTest {

	@Test
	void testMp3ToWav() throws UnsupportedAudioFileException, IOException {
//		File mp3 = new File("~/music/dnb-n-dubstep/B-Complex/B-Complex - Ghost.mp3");
//		File mp3 = new File("~/music/dnb-n-dubstep/B-Complex/B-Complex - Different_Kind_Of_Masochist.mp3");
		
		//TODO FIXME HAACK
		File mp3 = new File("../jmop-common-testing/src/main/resources/cz/martlin/jmop/common/testing/sample.mp3");
		assumeTrue(mp3.exists());
		
		File wav = new File("/tmp/out.wav");
		
		JavaConverter converter = new JavaConverter();
		converter.mp3ToWav(mp3, wav);
		
		assertTrue(wav.exists());
		
	}

}
