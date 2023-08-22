package cz.martlin.jmop.core.sources.remote.youtubedl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;

@Tag(value = "IDE_ONLY")
public class YoutubeDLDownloaderTest {

	public static void main(String[] args) throws IOException {
		new YoutubeDLDownloaderTest().testDownloadAdele();
		new YoutubeDLDownloaderTest().testDownloadCru();
	}
	
	@Test
	public void testDownloadAdele() throws IOException {
		testDownload("https://www.youtube.com/watch?v=U3ASj1L6_sY");
	}
	
	@Test
	public void testDownloadCru() throws IOException {
		testDownload("https://www.youtube.com/watch?v=RoS28yRgnDw");
	}

	
	private void testDownload(String url) throws IOException {
		BaseProgressListener listener = new PrintingListener(System.out);
		YoutubeDLDownloader downloader = new YoutubeDLDownloader(listener );
		
		File target = File.createTempFile("youtube-dl-", ".mp3");
		target.delete();
		System.out.println("Will save to: " + target);
		
		assertFalse(target.exists());
		downloader.download(url, target);
		assertTrue(target.exists());
	}

}
