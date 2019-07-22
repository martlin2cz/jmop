package cz.martlin.jmop.core.sources.remote;

public interface BaseRemoteSource {
	public BaseRemoteStatusHandler statuser();
	
	public BaseRemoteSourceQuerier querier();
	
	public BaseDownloader downloader();
	
	public BaseConverter converter();
}
