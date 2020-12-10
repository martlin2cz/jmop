package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicdataSaver {

	void saveBundleData(File bundleDir, Bundle bundle) throws JMOPSourceException;

	void savePlaylistData(File playlistFile, Playlist playlist) throws JMOPSourceException;;

	void saveTrackData(File trackFile, Track track) throws JMOPSourceException;;


}
