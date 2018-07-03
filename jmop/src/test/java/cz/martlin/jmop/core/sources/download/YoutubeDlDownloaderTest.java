package cz.martlin.jmop.core.sources.download;

import static org.junit.Assert.*;

import org.junit.Test;

public class YoutubeDlDownloaderTest {

	private static final double EPSILON = 0.1;

	@Test
	public void testParsePercent() {
		String line1 = "[youtube] 1eQCzql3dzI: Downloading webpage";
		String line2 = "[download]   0.0% of 60.02MiB at Unknown speed ETA Unknown ETA";
		String line3 = "[download]  42.9% of 60.02MiB at  9.07MiB/s ETA 00:05";
		String line4 = "[download] unknown";

		assertEquals(null, YoutubeDlDownloader.tryToParsePercentFromLine(line1));
		assertEquals(null, YoutubeDlDownloader.tryToParsePercentFromLine(line4));

		assertEquals(0.0, YoutubeDlDownloader.tryToParsePercentFromLine(line2).doubleValue(), EPSILON);
		assertEquals(42.9, YoutubeDlDownloader.tryToParsePercentFromLine(line3).doubleValue(), EPSILON);
	}

	@Test
	public void testRemoveSuffix() {
		assertEquals("foo", YoutubeDlDownloader.removeSuffix("foo.xxx"));
		assertEquals("foo.bar", YoutubeDlDownloader.removeSuffix("foo.bar.42"));
	}
	

}
