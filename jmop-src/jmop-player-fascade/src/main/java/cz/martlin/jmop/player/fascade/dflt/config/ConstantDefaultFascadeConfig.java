package cz.martlin.jmop.player.fascade.dflt.config;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.fascade.dflt.BaseDefaultJMOPConfig;

public class ConstantDefaultFascadeConfig implements BaseDefaultJMOPConfig {

	@Override
	public TrackFileFormat trackFileFormat() {
		return TrackFileFormat.MP3;
	}

	@Override
	public String getAllTracksPlaylistName() {
		return "all tracks";
	}

	@Override
	public int getMarkAsPlayedAfter() {
		return 10;
	}

	@Override
	public NonExistingFileStrategy getNonexistingFileStrategy() {
		return NonExistingFileStrategy.SKIP;
	}

}
