package cz.martlin.jmop.common.storages.dflt;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public interface BaseDefaultStorageConfig extends BaseConfiguration {

	TrackFileFormat getSaveFormat();

	String getAllTrackPlaylistName();
}
