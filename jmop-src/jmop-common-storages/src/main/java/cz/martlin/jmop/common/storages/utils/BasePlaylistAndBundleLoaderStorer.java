package cz.martlin.jmop.common.storages.utils;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

/**
 * @deprecated replaced by {@link BaseExtendedPlaylistManipulator}
 * @author martin
 *
 */
@Deprecated
public interface BasePlaylistAndBundleLoaderStorer {

	Bundle loadBundle(File file) ;

	Playlist loadPlaylist(Bundle bundle, File file) ;

	void savePlaylist(Playlist playlist, File file, boolean withBundleInfo, boolean withTrackInfo)
			;

}