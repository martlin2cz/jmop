package cz.martlin.jmop.core.config;

import cz.martlin.jmop.core.sources.locals.TrackFileFormat;

public class DefaultConfiguration implements BaseConfiguration {

	@Override
	public TrackFileFormat getSaveFormat() {
		return TrackFileFormat.MP3;
	}

	@Override
	public String getAllTracksPlaylistName() {
		return "all tracks";
	}

	@Override
	public int getOfflineRetryTimeout() {
		return 60;
	}

}
