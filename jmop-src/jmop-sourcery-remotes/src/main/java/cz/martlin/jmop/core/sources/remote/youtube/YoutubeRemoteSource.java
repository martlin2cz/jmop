package cz.martlin.jmop.core.sources.remote.youtube;

import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.empty.TestingDownloader;
import cz.martlin.jmop.core.sources.remote.ffmpeg.FFMPEGConverter;
import cz.martlin.jmop.core.sources.remote.youtubedl.YoutubeDLDownloader;
import cz.martlin.jmop.sourcery.remote.BaseConverter;
import cz.martlin.jmop.sourcery.remote.BaseDownloader;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.sourcery.remote.BaseRemoteStatusHandler;
import cz.martlin.jmop.sourcery.remote.BaseRemotesConfiguration;

public class YoutubeRemoteSource extends AbstractRemoteSource {

	private YoutubeRemoteSource(BaseRemoteStatusHandler statuser, BaseRemoteSourceQuerier querier,
			BaseDownloader downloader, BaseConverter converter) {
		super(statuser, querier, downloader, converter);
	}

	public static YoutubeRemoteSource create(BaseRemotesConfiguration config, BaseProgressListener listener) {

		BaseRemoteSourceQuerier querier = new YoutubeQuerier(config);
		BaseDownloader downloader = new YoutubeDLDownloader(listener); //new TestingDownloader(TrackFileFormat.MP3);	
		BaseConverter converter = new FFMPEGConverter(listener);
		BaseRemoteStatusHandler statuser = new YoutubeStatuser(querier, downloader, converter);

		return new YoutubeRemoteSource(statuser, querier, downloader, converter);

	}

}
