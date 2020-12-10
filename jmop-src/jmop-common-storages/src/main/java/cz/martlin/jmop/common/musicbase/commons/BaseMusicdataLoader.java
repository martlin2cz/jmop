package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;
import java.util.List;
import java.util.Map;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicdataLoader {

	List<String> loadBundlesNames() throws JMOPSourceException;

	Bundle loadBundleData(File bundleDir, String bundleName) throws JMOPSourceException;;

	List<String> loadPlaylistsNames(File bundleDir, Bundle bundle, String bundleName) throws JMOPSourceException;;

	Playlist loadPlaylistData(File playlistFile, Bundle bundle, Map<String, Track> tracks, String playlistName) throws JMOPSourceException;

	List<String> loadTracksTitles(File bundleDir, Bundle bundle, String bundleName) throws JMOPSourceException;;

	Track loadTrackData(File trackFile, Bundle bundle, String trackTitle) throws JMOPSourceException;

}
