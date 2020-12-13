package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.common.storages.utils.FilesLocatorExtension;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class SaverWithAllTrackPlaylist implements BaseMusicdataSaver {

	private final String allTracksPlaylistName;
	private final BaseInMemoryMusicbase musicbase;
	private final BaseExtendedPlaylistManipulator manipulator;
	private final FilesLocatorExtension locator;

	public SaverWithAllTrackPlaylist(String allTracksPlaylistName, BaseInMemoryMusicbase musicbase,
			BaseExtendedPlaylistManipulator manipulator, BaseFilesLocator locator) {
		super();
		this.allTracksPlaylistName = allTracksPlaylistName;
		this.musicbase = musicbase;
		this.manipulator = manipulator;
		this.locator = new FilesLocatorExtension(locator);
	}

///////////////////////////////////////////////////////////////////////////////

	@Override
	public void saveBundleData(File bundleDir, Bundle bundle, SaveReason reason) throws JMOPSourceException {
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);
		File allTracksPlaylistFile = locator.playlistFile(allTracksPlaylist);
		save(allTracksPlaylist, allTracksPlaylistFile);
	}

	@Override
	public void savePlaylistData(File playlistFile, Playlist playlist, SaveReason reason) throws JMOPSourceException {
		save(playlist, playlistFile);
	}

	@Override
	public void saveTrackData(File trackFile, Track track, SaveReason reason) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);

		if (reason == SaveReason.CREATED || reason == SaveReason.MOVED) { 
			allTracksPlaylist.addTrack(track);
		}
		
		File allTracksPlaylistFile = locator.playlistFile(allTracksPlaylist);
		save(allTracksPlaylist, allTracksPlaylistFile);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean isAllTracksPlaylist(Playlist playlist) {
		return playlist.getName().equals(allTracksPlaylistName);
	}

	private Playlist obtainAllTracksPlaylist(Bundle bundle) throws JMOPSourceException {
		return musicbase.playlists(bundle).stream() //
				.filter(p -> isAllTracksPlaylist(p)) //
				.findAny().orElseGet(() -> { //
					//TODO little tricky, but works perfectly
					try {
						return musicbase.createNewPlaylist(bundle, allTracksPlaylistName);
					} catch (JMOPSourceException e) {
						throw new RuntimeException("Could not create all tracks playlist", e);
					}
				});
	}

	private void save(Playlist playlist, File playlistFile) throws JMOPSourceException {
		if (isAllTracksPlaylist(playlist)) {
			manipulator.savePlaylistWithBundle(playlist, playlistFile);
		} else {
			manipulator.saveOnlyPlaylist(playlist, playlistFile);
		}
	}

}
