package cz.martlin.jmop.common.storages.utils;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.core.misc.JMOPSourceException;

/**
 * @deprecated replaced by {@link BaseExtendedPlaylistManipulator}
 * @author martin
 *
 */
@Deprecated
public interface BasePlaylistAndBundleLoaderStorer {

	Bundle loadBundle(File file) throws JMOPSourceException;

	Playlist loadPlaylist(Bundle bundle, File file) throws JMOPSourceException;

	void savePlaylist(Playlist playlist, File file, boolean withBundleInfo, boolean withTrackInfo)
			throws JMOPSourceException;

}