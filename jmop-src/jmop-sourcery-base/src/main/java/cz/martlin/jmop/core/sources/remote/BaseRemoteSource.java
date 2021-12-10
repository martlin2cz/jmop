package cz.martlin.jmop.core.sources.remote;

public interface BaseRemoteSource {

	BaseRemoteStatusHandler statuser();

	BaseRemoteSourceQuerier querier();

	BaseDownloader downloader();

	BaseConverter converter();

}
