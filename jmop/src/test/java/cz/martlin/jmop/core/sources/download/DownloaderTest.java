package cz.martlin.jmop.core.sources.download;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public class DownloaderTest {

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

	public static void main(String[] args) {
		BaseSourceDownloader downloader = new YoutubeDlDownloader();

		SourceKind source = SourceKind.YOUTUBE;
		String id = "1eQCzql3dzI";
		TrackIdentifier identifier = new TrackIdentifier(source, id);

		try {
			boolean succes = downloader.download(identifier);
			System.err.println("Success? " + succes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
