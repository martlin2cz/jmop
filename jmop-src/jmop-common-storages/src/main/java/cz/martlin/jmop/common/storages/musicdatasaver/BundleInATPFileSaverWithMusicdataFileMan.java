package cz.martlin.jmop.common.storages.musicdatasaver;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;

/**
 * The musicdata saver delegating to the {@link BaseMusicdataFileManipulator}.
 * 
 * This instance works with the "all tracks playlist" concept, thus, the bundle
 * data are stored in the same file as the "all tracks playlist" file is stored.
 * 
 * @author martin
 *
 */
@Deprecated
public class BundleInATPFileSaverWithMusicdataFileMan extends SaverWithMusicdataFileMan {

	private final BaseInMemoryMusicbase musicbase;
	private final String allTracksPlaylistName;

	public BundleInATPFileSaverWithMusicdataFileMan(BaseBundlesDirLocator locator, BaseMusicdataFileManipulator manipulator,
			BaseInMemoryMusicbase musicbase, String allTracksPlaylistName) {
		super(locator, manipulator, null);
		this.musicbase = musicbase;
		this.allTracksPlaylistName = allTracksPlaylistName;
	}

/////////////////////////////////////////////////////////////////////////////////////

//	@Override
//	protected Set<Track> obtainBundleTracks(Bundle bundle) {
//		return musicbase.tracks(bundle);
//	}

	protected File obtainBundleFile(File bundleDir, Bundle bundle) {
		throw new UnsupportedOperationException();
//		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);
//		File allTracksPlaylistFile = locator.playlistFile(allTracksPlaylist);
//		return allTracksPlaylistFile;
	}

/////////////////////////////////////////////////////////////////////////////////////

	private Playlist obtainAllTracksPlaylist(Bundle bundle) {
		return musicbase.playlists(bundle).stream() //
				.filter(p -> isAllTracksPlaylist(p)) //
				.findAny()//
				.orElseGet(() -> musicbase.createNewPlaylist(bundle, allTracksPlaylistName));
	}

	private boolean isAllTracksPlaylist(Playlist playlist) {
		return playlist.getName().equals(allTracksPlaylistName);
	}
}
