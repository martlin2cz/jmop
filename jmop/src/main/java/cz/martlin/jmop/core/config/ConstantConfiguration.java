package cz.martlin.jmop.core.config;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The simple configuration, simply returning constantly specified values.
 * 
 * @author martin
 *
 */
public class ConstantConfiguration implements BaseConfiguration {

	@Override
	public TrackFileFormat getSaveFormat() {
		return TrackFileFormat.MP3;
	}

	@Override
	public String getAllTracksPlaylistName() {
		return "all tracks"; //$NON-NLS-1$
	}

	@Override
	public int getOfflineRetryTimeout() {
		return 60;
	}

}
