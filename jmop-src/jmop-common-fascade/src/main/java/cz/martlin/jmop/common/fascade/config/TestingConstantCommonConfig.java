package cz.martlin.jmop.common.fascade.config;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public class TestingConstantCommonConfig implements BaseJMOPCommonConfig {

	@Override
	public String getAllTracksPlaylistName() {
		return "all tracks";
	}

	@Override
	public TrackFileFormat trackFileFormat() {
		return TrackFileFormat.MP3;
	}

}
