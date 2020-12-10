package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;
import java.util.List;
import java.util.Map;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicdataSaver {

	void saveBundleData(File bundleDir, Bundle bundle) throws JMOPSourceException;

	void savePlaylistData(File playlistFile, Playlist playlist) throws JMOPSourceException;;

	void saveTrackData(File trackFile, Track track) throws JMOPSourceException;;

	List<String> loadBundlesNames() throws JMOPSourceException;;

	Bundle loadBundleData(File bundleDir, String bundleName) throws JMOPSourceException;;

	List<String> loadPlaylistsNames(File bundleDir, String bundleName) throws JMOPSourceException;;

	Playlist loadPlaylistData(File playlistFile, Bundle bundle, Map<String, Track> tracks, String playlistName)
			throws JMOPSourceException;;

	//TODO here should be somehow the load and save track data, ain't it?
}
