package cz.martlin.jmop.core.sources.remotes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import cz.martlin.jmop.core.sources.remotes.FFMPEGConverter;

public class FFMPEGConverterTest {

	@Test
	public void testParseDurationFromLine() {
		assertEquals("00:11:22", FFMPEGConverter.inferDuration("00:11:22"));
		assertEquals("99:88:77", FFMPEGConverter.inferDuration("99:88:77"));

		assertEquals("00:11:22", FFMPEGConverter.inferDuration("00:11:22:33:44"));

		assertEquals("00:11:22", FFMPEGConverter.inferDuration("the time is 00:11:22"));
		assertEquals("00:11:22", FFMPEGConverter.inferDuration("time=00:11:22"));

		assertEquals("19:10:22", FFMPEGConverter.inferDuration("date=03.6.18 time=19:10:22"));
	}

	@Test
	public void testConvertDuration() {
		assertEquals(0, FFMPEGConverter.parseDuration("00:00:00"));
		assertEquals(1, FFMPEGConverter.parseDuration("00:00:01"));
		assertEquals((1 * 60 * 60) + (1 * 60) + (1), FFMPEGConverter.parseDuration("01:01:01"));

		// works fine also with general sequence
		assertEquals((1 * 60 * 60 * 60) + (2 * 60 * 60) + (3 * 60) + (4), FFMPEGConverter.parseDuration("01:02:03:04"));
	}

	@Test
	public void testTryToProcessAsInputDuration() {
		assertNull(FFMPEGConverter.tryToProcessAsInputDuration("  libavutil      55. 34.101 / 55. 78.100"));
		assertNull(FFMPEGConverter.tryToProcessAsInputDuration("    encoder         : Lavf57.83.100"));
		assertNull(FFMPEGConverter
				.tryToProcessAsInputDuration("    Stream #0:0: Audio: mp3, 48000 Hz, stereo, s16p, 128 kb/s"));

		assertNull(FFMPEGConverter
				.tryToProcessAsInputDuration("size=    2355kB time=00:03:25.03 bitrate=  94.1kbits/s speed=15.7x"));
		assertNotNull(FFMPEGConverter
				.tryToProcessAsInputDuration("  Duration: 00:03:25.06, start: 0.023021, bitrate: 128 kb/s"));

	}

	@Test
	public void testTryToProcessAsProgress() {
		assertNull(FFMPEGConverter.tryToProcessAsProgress("  libavutil      55. 34.101 / 55. 78.100"));
		assertNull(FFMPEGConverter.tryToProcessAsProgress("    encoder         : Lavf57.83.100"));
		assertNull(FFMPEGConverter
				.tryToProcessAsProgress("    Stream #0:0: Audio: mp3, 48000 Hz, stereo, s16p, 128 kb/s"));

		assertNull(
				FFMPEGConverter.tryToProcessAsProgress("  Duration: 00:03:25.06, start: 0.023021, bitrate: 128 kb/s"));
		assertNotNull(FFMPEGConverter
				.tryToProcessAsProgress("size=    2355kB time=00:03:25.03 bitrate=  94.1kbits/s speed=15.7x"));
	}

	// TODO test the rest
}
