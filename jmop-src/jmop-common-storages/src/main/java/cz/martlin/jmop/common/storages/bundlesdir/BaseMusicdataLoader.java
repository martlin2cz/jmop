package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;
import java.util.List;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseMusicdataLoader {

	List<String> loadBundlesNames() throws JMOPMusicbaseException;

	Bundle loadBundle(File bundleDir, String bundleName) throws JMOPMusicbaseException;;

	List<String> loadPlaylistsNames(File bundleDir, Bundle bundle, String bundleName) throws JMOPMusicbaseException;;

	Playlist loadPlaylist(File playlistFile, Bundle bundle, Map<String, Track> tracks, String playlistName) throws JMOPMusicbaseException;

	List<String> loadTracksTitles(File bundleDir, Bundle bundle, String bundleName) throws JMOPMusicbaseException;;

	Track loadTrack(File trackFile, Bundle bundle, String trackTitle) throws JMOPMusicbaseException;

}
