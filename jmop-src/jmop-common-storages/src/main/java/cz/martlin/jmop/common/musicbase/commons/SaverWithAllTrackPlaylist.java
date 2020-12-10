package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
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
	public void saveBundleData(File bundleDir, Bundle bundle) throws JMOPSourceException {
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);
		File allTracksPlaylistFile = locator.playlistFile(allTracksPlaylist);
		save(allTracksPlaylist, allTracksPlaylistFile);
	}

	@Override
	public void savePlaylistData(File playlistFile, Playlist playlist) throws JMOPSourceException {
		save(playlist, playlistFile);
	}

	@Override
	public void saveTrackData(File trackFile, Track track) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);

		if (!allTracksPlaylist.getTracks().getTracks().contains(track)) { //FIXME HACK
			System.err.println("Warning, re(adding) track into all tracks playlist"); 
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
