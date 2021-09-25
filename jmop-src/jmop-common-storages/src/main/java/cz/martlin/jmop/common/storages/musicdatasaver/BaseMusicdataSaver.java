package cz.martlin.jmop.common.storages.musicdatasaver;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

/**
 * An component responsible for the actual saving of the particular modified
 * elements in the musicbase, to the particular persistent database or other
 * storage.
 * 
 * @author martin
 *
 */
public interface BaseMusicdataSaver {
	public enum SaveReason {
		CREATED, UPDATED, RENAMED, MOVED;
		// Note: no need for save if DELETED
	}

	/**
	 * Saves the created or updated bundle because of the given reason.
	 * 
	 * @param bundle
	 * @param reason
	 */
	void saveBundleData(Bundle bundle, SaveReason reason);

	/**
	 * Saves the created or updated playlist because of the given reason.
	 * 
	 * @param playlist
	 * @param reason
	 */
	void savePlaylistData(Playlist playlist, SaveReason reason);

	/**
	 * Saves the created or updated track because of the given reason.
	 * 
	 * @param track
	 * @param reason
	 */
	void saveTrackData(Track track, SaveReason reason);

}
