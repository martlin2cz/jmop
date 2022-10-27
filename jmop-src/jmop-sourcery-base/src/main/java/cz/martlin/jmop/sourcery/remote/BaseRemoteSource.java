package cz.martlin.jmop.sourcery.remote;

public interface BaseRemoteSource {

	BaseRemoteStatusHandler statuser();

	BaseRemoteSourceQuerier querier();

	BaseDownloader downloader();

	BaseConverter converter();

}
