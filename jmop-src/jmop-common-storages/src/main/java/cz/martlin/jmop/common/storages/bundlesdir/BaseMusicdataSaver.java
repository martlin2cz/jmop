package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicdataSaver {
	public enum SaveReason {
		CREATED, UPDATED, RENAMED, MOVED;
		//Note: no need for save if DELETED
	}

	void saveBundleData(File bundleDir, Bundle bundle, SaveReason reason) throws JMOPSourceException;

	void savePlaylistData(File playlistFile, Playlist playlist, SaveReason reason) throws JMOPSourceException;;

	void saveTrackData(File trackFile, Track track, SaveReason reason) throws JMOPSourceException;

}
