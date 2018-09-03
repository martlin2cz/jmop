package cz.martlin.jmop.core.config;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class Configuration {

	public TrackFileFormat getSaveFormat() {
		return TrackFileFormat.MP3;
	}

	public String getAllTracksPlaylistName() {
		return "all tracks";
	}

}
