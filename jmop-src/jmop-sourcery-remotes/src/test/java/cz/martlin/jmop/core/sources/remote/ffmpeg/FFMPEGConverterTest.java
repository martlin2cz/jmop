package cz.martlin.jmop.core.sources.remote.ffmpeg;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class FFMPEGConverterTest {

	@RegisterExtension
	public TestingMusicdataExtension tme = TestingMusicdataExtension.simple(true);
	
	@Test
	public void test() throws Exception {
		BaseProgressListener listener = new PrintingListener(System.out);
		FFMPEGConverter converter = new FFMPEGConverter(listener);
		
		File fromFile = tme.tmd.aerodynamic.getFile();
		
		TrackFileFormat toFormat = TrackFileFormat.WAV;
		File toFile = File.createTempFile(tme.tmd.aerodynamic.getTitle() + "-", "." + toFormat.fileExtension());
		toFile.delete();
		
		assertFalse(toFile.exists());
		converter.convert(tme.tmd.aerodynamic, fromFile, toFile);
		assertTrue(toFile.exists());
	}
}
