package cz.martlin.jmop.core.sources.remotes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class YoutubeDlDownloaderTest {

	private static final double EPSILON = 0.1;

	@Test
	public void testParsePercent() {
		String line1 = "[youtube] 1eQCzql3dzI: Downloading webpage"; //$NON-NLS-1$
		String line2 = "[download]   0.0% of 60.02MiB at Unknown speed ETA Unknown ETA"; //$NON-NLS-1$
		String line3 = "[download]  42.9% of 60.02MiB at  9.07MiB/s ETA 00:05"; //$NON-NLS-1$
		String line4 = "[download] unknown"; //$NON-NLS-1$

		assertEquals(null, YoutubeDlDownloader.tryToParsePercentFromLine(line1));
		assertEquals(null, YoutubeDlDownloader.tryToParsePercentFromLine(line4));

		assertEquals(0.0, YoutubeDlDownloader.tryToParsePercentFromLine(line2).doubleValue(), EPSILON);
		assertEquals(42.9, YoutubeDlDownloader.tryToParsePercentFromLine(line3).doubleValue(), EPSILON);
	}

	@Test
	public void testRemoveSuffix() {
		assertEquals("foo", YoutubeDlDownloader.removeSuffix("foo.xxx")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("foo.bar", YoutubeDlDownloader.removeSuffix("foo.bar.42")); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
