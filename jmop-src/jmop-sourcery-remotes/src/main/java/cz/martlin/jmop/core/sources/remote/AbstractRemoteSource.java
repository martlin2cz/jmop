package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.sourcery.remote.BaseConverter;
import cz.martlin.jmop.sourcery.remote.BaseDownloader;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSource;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.sourcery.remote.BaseRemoteStatusHandler;

public class AbstractRemoteSource implements BaseRemoteSource {

	private final BaseRemoteStatusHandler statuser;
	private final BaseRemoteSourceQuerier querier;
	private final BaseDownloader downloader;
	private final BaseConverter converter;

	public AbstractRemoteSource(BaseRemoteStatusHandler statuser, BaseRemoteSourceQuerier querier,
			BaseDownloader downloader, BaseConverter converter) {
		super();
		this.statuser = statuser;
		this.querier = querier;
		this.downloader = downloader;
		this.converter = converter;
	}

	@Override
	public BaseRemoteStatusHandler statuser() {
		return statuser;
	}

	@Override
	public BaseRemoteSourceQuerier querier() {
		return querier;
	}

	@Override
	public BaseDownloader downloader() {
		return downloader;
	}

	@Override
	public BaseConverter converter() {
		return converter;
	}

}
