package cz.martlin.jmop.core.sources.remotes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class FFMPEGConverterTest {

	@Test
	public void testParseDurationFromLine() {
		assertEquals("00:11:22", XXX_FFMPEGConverter.inferDuration("00:11:22")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("99:88:77", XXX_FFMPEGConverter.inferDuration("99:88:77")); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals("00:11:22", XXX_FFMPEGConverter.inferDuration("00:11:22:33:44")); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals("00:11:22", XXX_FFMPEGConverter.inferDuration("the time is 00:11:22")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("00:11:22", XXX_FFMPEGConverter.inferDuration("time=00:11:22")); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals("19:10:22", XXX_FFMPEGConverter.inferDuration("date=03.6.18 time=19:10:22")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testConvertDuration() {
		assertEquals(0, XXX_FFMPEGConverter.parseDuration("00:00:00")); //$NON-NLS-1$
		assertEquals(1, XXX_FFMPEGConverter.parseDuration("00:00:01")); //$NON-NLS-1$
		assertEquals((1 * 60 * 60) + (1 * 60) + (1), XXX_FFMPEGConverter.parseDuration("01:01:01")); //$NON-NLS-1$

		// works fine also with general sequence
		assertEquals((1 * 60 * 60 * 60) + (2 * 60 * 60) + (3 * 60) + (4), XXX_FFMPEGConverter.parseDuration("01:02:03:04")); //$NON-NLS-1$
	}

	@Test
	public void testTryToProcessAsInputDuration() {
		assertNull(XXX_FFMPEGConverter.tryToProcessAsInputDuration("  libavutil      55. 34.101 / 55. 78.100")); //$NON-NLS-1$
		assertNull(XXX_FFMPEGConverter.tryToProcessAsInputDuration("    encoder         : Lavf57.83.100")); //$NON-NLS-1$
		assertNull(XXX_FFMPEGConverter
				.tryToProcessAsInputDuration("    Stream #0:0: Audio: mp3, 48000 Hz, stereo, s16p, 128 kb/s")); //$NON-NLS-1$

		assertNull(XXX_FFMPEGConverter
				.tryToProcessAsInputDuration("size=    2355kB time=00:03:25.03 bitrate=  94.1kbits/s speed=15.7x")); //$NON-NLS-1$
		assertNotNull(XXX_FFMPEGConverter
				.tryToProcessAsInputDuration("  Duration: 00:03:25.06, start: 0.023021, bitrate: 128 kb/s")); //$NON-NLS-1$

	}

	@Test
	public void testTryToProcessAsProgress() {
		assertNull(XXX_FFMPEGConverter.tryToProcessAsProgress("  libavutil      55. 34.101 / 55. 78.100")); //$NON-NLS-1$
		assertNull(XXX_FFMPEGConverter.tryToProcessAsProgress("    encoder         : Lavf57.83.100")); //$NON-NLS-1$
		assertNull(XXX_FFMPEGConverter
				.tryToProcessAsProgress("    Stream #0:0: Audio: mp3, 48000 Hz, stereo, s16p, 128 kb/s")); //$NON-NLS-1$

		assertNull(
				XXX_FFMPEGConverter.tryToProcessAsProgress("  Duration: 00:03:25.06, start: 0.023021, bitrate: 128 kb/s")); //$NON-NLS-1$
		assertNotNull(XXX_FFMPEGConverter
				.tryToProcessAsProgress("size=    2355kB time=00:03:25.03 bitrate=  94.1kbits/s speed=15.7x")); //$NON-NLS-1$
	}

	// TODO test the rest
}
