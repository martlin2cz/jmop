package cz.martlin.jmop.core.config;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public interface BaseConfiguration {

	TrackFileFormat getSaveFormat();

	String getAllTracksPlaylistName();

	int getOfflineRetryTimeout();

}