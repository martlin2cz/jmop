package cz.martlin.jmop.common.musicbase.commons;

import java.util.List;
import java.util.Map;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicdataSaver {

	void saveBundleData(Bundle bundle) throws JMOPSourceException;

	void savePlaylistData(Playlist playlist) throws JMOPSourceException;;

	void saveTrackData(Track track) throws JMOPSourceException;;

	List<String> loadBundlesNames() throws JMOPSourceException;;

	Bundle loadBundleData(String bundleName) throws JMOPSourceException;;

	List<String> loadPlaylistsNames(String bundleName) throws JMOPSourceException;;

	Playlist loadPlaylistData(Bundle bundle, Map<String, Track> tracks, String playlistName)
			throws JMOPSourceException;;

	List<String> loadTracksTitles(String bundleName) throws JMOPSourceException;;

	Track loadTrackData(Bundle bundle, String trackTitle) throws JMOPSourceException;;

}
