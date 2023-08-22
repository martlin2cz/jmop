package cz.martlin.jmop.player.fascade.dflt.config;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.fascade.dflt.BaseJMOPPlayerConfig;

/**
 * The default implementation of the configuration. It's just constants.
 * 
 * @author martin
 *
 */
public class ConstantDefaultFascadeConfig implements BaseJMOPPlayerConfig {

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
