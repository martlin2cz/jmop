package cz.martlin.jmop.core.sources.remote.youtube;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import cz.martlin.jmop.core.misc.BaseUIInterractor;
import cz.martlin.jmop.core.misc.ConsoleUIInteractor;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;
import cz.martlin.jmop.core.sources.remote.ffmpeg.FFMPEGConverter;
import cz.martlin.jmop.core.sources.remote.youtubedl.YoutubeDLDownloader;

@Tag(value = "IDE_ONLY")
class YoutubeStatuserTest {

	public static class TestingConfig implements BaseRemotesConfiguration {

		@Override
		public int getSearchCount() {
			return 10;
		}

	}

	private YoutubeStatuser statuser;

	private static YoutubeStatuser prepareStatuser() {
		BaseRemotesConfiguration config = new TestingConfig();
		BaseProgressListener listener = new PrintingListener(System.out);
		BaseConverter converter = new FFMPEGConverter(listener);
		BaseDownloader downloader = new YoutubeDLDownloader(listener);
		
		BaseRemoteSourceQuerier querier = new YoutubeQuerier(config);
		return new YoutubeStatuser(querier, downloader, converter);
	}
	
	@BeforeEach
	public void setUp() {
		statuser = prepareStatuser();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void testCheckConverter() {
		BaseUIInterractor interactor = new ConsoleUIInteractor();
		boolean checked = statuser.checkConverter(interactor);
		assertTrue(checked);
	}
	
	@Test
	void testCheckDownloader() {
		BaseUIInterractor interactor = new ConsoleUIInteractor();
		boolean checked = statuser.checkDownloader(interactor);
		assertTrue(checked);
	}
	

	@Test
	void testQuerier() {
		BaseUIInterractor interactor = new ConsoleUIInteractor();
		boolean checked = statuser.checkQuerier(interactor);
		assertTrue(checked);
	}
	

	


}
