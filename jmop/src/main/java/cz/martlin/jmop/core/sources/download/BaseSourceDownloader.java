package cz.martlin.jmop.core.sources.download;

import java.io.File;

import cz.martlin.jmop.core.tracks.TrackIdentifier;

public interface BaseSourceDownloader {

	public boolean download(TrackIdentifier identifier) throws Exception;

	// TODO stopDownloading
	// TODO getProgressProperty, isRunningProperty ?
}
