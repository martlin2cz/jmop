package cz.martlin.jmop.common.storages.config;

import cz.martlin.jmop.common.storages.components.AllTracksPlaylistStorageComponent;
import cz.martlin.jmop.common.storages.components.BaseStorageConfiguration;

/**
 * The configuration of the all tracks playlist storage.
 * 
 * @author martin
 *
 */
public interface AllTracksPlaylistStorageConfig extends BaseStorageConfiguration, AllTracksPlaylistStorageComponent {

	/**
	 * Returns the name of the all tracks playlist.
	 * 
	 * @return
	 */
	public String getAllTracksPlaylistName();
}
