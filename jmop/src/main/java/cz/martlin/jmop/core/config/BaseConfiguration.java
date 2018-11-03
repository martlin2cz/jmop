package cz.martlin.jmop.core.config;

import cz.martlin.jmop.core.sources.locals.TrackFileFormat;

public interface BaseConfiguration {

	TrackFileFormat getSaveFormat();

	String getAllTracksPlaylistName();

	int getOfflineRetryTimeout();

}