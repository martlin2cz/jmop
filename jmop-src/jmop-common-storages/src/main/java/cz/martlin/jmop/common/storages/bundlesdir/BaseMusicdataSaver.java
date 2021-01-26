package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseMusicdataSaver {
	public enum SaveReason {
		CREATED, UPDATED, RENAMED, MOVED;
		//Note: no need for save if DELETED
	}

	void saveBundleData(File bundleDir, Bundle bundle, SaveReason reason) ;

	void savePlaylistData(File playlistFile, Playlist playlist, SaveReason reason) ;;

	void saveTrackData(File trackFile, Track track, SaveReason reason) ;

}
