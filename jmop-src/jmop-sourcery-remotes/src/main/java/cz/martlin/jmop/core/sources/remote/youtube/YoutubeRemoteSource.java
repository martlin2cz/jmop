package cz.martlin.jmop.core.sources.remote.youtube;

import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.ffmpeg.FFMPEGConverter;
import cz.martlin.jmop.core.sources.remote.youtubedl.YoutubeDLDownloader;
import cz.martlin.jmop.sourcery.remote.BaseConverter;
import cz.martlin.jmop.sourcery.remote.BaseDownloader;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.sourcery.remote.BaseRemoteStatusHandler;
import cz.martlin.jmop.sourcery.remote.BaseRemotesConfiguration;

/**
 * The YOUTUBE remote source.
 * 
 * @author martin
 *
 */
public class YoutubeRemoteSource extends AbstractRemoteSource {

	/**
	 * Use the {@link #create(BaseRemotesConfiguration, BaseProgressListener)} to
	 * get instance.
	 * 
	 * @param statuser
	 * @param querier
	 * @param downloader
	 * @param converter
	 */
	private YoutubeRemoteSource(BaseRemoteStatusHandler statuser, BaseRemoteSourceQuerier querier,
			BaseDownloader downloader, BaseConverter converter) {
		super(statuser, querier, downloader, converter);
	}

	/**
	 * Create.
	 * 
	 * @param config
	 * @param listener
	 * @return
	 */
	public static YoutubeRemoteSource create(BaseRemotesConfiguration config, BaseProgressListener listener) {

		BaseRemoteSourceQuerier querier = new YoutubeQuerier(config);
		BaseDownloader downloader = new YoutubeDLDownloader(listener); // new TestingDownloader(TrackFileFormat.MP3);
		BaseConverter converter = new FFMPEGConverter(listener);
		BaseRemoteStatusHandler statuser = new YoutubeStatuser(querier, downloader, converter);

		return new YoutubeRemoteSource(statuser, querier, downloader, converter);

	}

}
