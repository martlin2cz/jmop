package cz.martlin.jmop.common.storages.components;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The base storage configuration object.
 * 
 * @author martin
 *
 */
public interface BaseStorageConfiguration extends BaseConfiguration {
	
	/**
	 * Returns the track format.
	 * @return
	 */
	public TrackFileFormat trackFileFormat();
}
