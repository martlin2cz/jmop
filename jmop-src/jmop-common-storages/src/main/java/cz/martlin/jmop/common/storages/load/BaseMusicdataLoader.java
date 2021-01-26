package cz.martlin.jmop.common.storages.load;

import java.io.File;
import java.util.List;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseMusicdataLoader {

	void load(BaseInMemoryMusicbase inmemory);
	
//	List<String> loadBundlesNames() ;
//
//	Bundle loadBundle(File bundleDir, String bundleName) ;;
//
//	List<String> loadPlaylistsNames(File bundleDir, Bundle bundle, String bundleName) ;;
//
//	Playlist loadPlaylist(File playlistFile, Bundle bundle, Map<String, Track> tracks, String playlistName) ;
//
//	List<String> loadTracksTitles(File bundleDir, Bundle bundle, String bundleName) ;;
//
//	Track loadTrack(File trackFile, Bundle bundle, String trackTitle) ;

}
