package cz.martlin.jmop.core.sources.remote;

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
