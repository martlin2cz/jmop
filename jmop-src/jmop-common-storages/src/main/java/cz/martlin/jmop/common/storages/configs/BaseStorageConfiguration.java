package cz.martlin.jmop.common.storages.configs;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

public interface BaseStorageConfiguration extends BaseConfiguration {
	
	public TrackFileFormat trackFileFormat();
}
