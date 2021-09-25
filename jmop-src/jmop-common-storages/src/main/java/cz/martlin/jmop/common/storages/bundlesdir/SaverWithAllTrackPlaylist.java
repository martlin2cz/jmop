package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.common.storages.utils.FilesLocatorExtension;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

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
	public void saveBundleData(File bundleDir, Bundle bundle, SaveReason reason)  {
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);
		File allTracksPlaylistFile = locator.playlistFile(allTracksPlaylist);
		
		try {
			save(allTracksPlaylist, allTracksPlaylistFile);
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not save bundle", e);
		}
	}

	@Override
	public void savePlaylistData(File playlistFile, Playlist playlist, SaveReason reason)  {
		try {
			save(playlist, playlistFile);
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not save playlist", e);
		}
	}

	@Override
	public void saveTrackData(File trackFile, Track track, SaveReason reason)  {
		Bundle bundle = track.getBundle();
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);

		if (reason == SaveReason.CREATED || reason == SaveReason.MOVED) { 
			allTracksPlaylist.addTrack(track);
		}
		
		File allTracksPlaylistFile = locator.playlistFile(allTracksPlaylist);
		try {
			save(allTracksPlaylist, allTracksPlaylistFile);
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not save track", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean isAllTracksPlaylist(Playlist playlist) {
		return playlist.getName().equals(allTracksPlaylistName);
	}

	private Playlist obtainAllTracksPlaylist(Bundle bundle)  {
		return musicbase.playlists(bundle).stream() //
				.filter(p -> isAllTracksPlaylist(p)) //
				.findAny()//
				.orElseGet(() ->  musicbase.createNewPlaylist(bundle, allTracksPlaylistName));
	}

	private void save(Playlist playlist, File playlistFile) throws JMOPPersistenceException  {
		TracksSource tracks = locator;
		
		if (isAllTracksPlaylist(playlist)) {
			manipulator.savePlaylistWithBundle(playlist, playlistFile, tracks);
		} else {
			manipulator.saveOnlyPlaylist(playlist, playlistFile, tracks);
		}
	}

}
