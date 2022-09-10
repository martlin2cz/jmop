package cz.martlin.jmop.core.sources.remote.youtube;

import java.net.URI;

import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.core.sources.remote.checker.AbstractRemoteStatusHandler;

public class YoutubeStatuser extends AbstractRemoteStatusHandler {

	public YoutubeStatuser(BaseRemoteSourceQuerier querier, BaseDownloader downloader, BaseConverter converter) {
		super(querier, downloader, converter);
	}

	@Override
	protected String prepareTestingQuery() {
		return "sample youtube";
	}

	@Override
	protected URI prepareTestingTrackURI() {
		return URI.create("https://youtube.com/?v=U3ASj1L6_sY");
	}

}
