package cz.martlin.jmop.common.musicbase.misc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

/**
 * The main entry point for the musicbase. This class encapsulates the modyfiing
 * operations.
 * 
 * @author martin
 *
 */
public class MusicbaseModyfiingEncapsulator {
	private final BaseMusicbase musicbase;

	public MusicbaseModyfiingEncapsulator(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
	}

/////////////////////////////////////////////////////////////////////////////////////
	
	public void load() throws JMOPMusicbaseException {
		musicbase.load();
	}

	public void reload() throws JMOPMusicbaseException {
		musicbase.load();
	}
	
/////////////////////////////////////////////////////////////////////////////////////

	public Bundle createNewBundle(String name) throws JMOPMusicbaseException {
		return musicbase.createNewBundle(name);
	}

	public void renameBundle(Bundle bundle, String newName) throws JMOPMusicbaseException {
		musicbase.renameBundle(bundle, newName);
	}

	public void removeBundle(Bundle bundle) throws JMOPMusicbaseException {
		musicbase.removeBundle(bundle);
	}

	public void bundleUpdated(Bundle bundle) throws JMOPMusicbaseException {
		musicbase.bundleUpdated(bundle);
	}

/////////////////////////////////////////////////////////////////////////////////////

	public Playlist createNewPlaylist(Bundle bundle, String name) throws JMOPMusicbaseException {
		return musicbase.createNewPlaylist(bundle, name);
	}

	public void renamePlaylist(Playlist playlist, String newName) throws JMOPMusicbaseException {
		musicbase.renamePlaylist(playlist, newName);
	}

	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPMusicbaseException {
		musicbase.movePlaylist(playlist, newBundle);
	}

	public void removePlaylist(Playlist playlist) throws JMOPMusicbaseException {
		musicbase.removePlaylist(playlist);
	}

	public void playlistUpdated(Playlist playlist) throws JMOPMusicbaseException {
		musicbase.playlistUpdated(playlist);
	}

/////////////////////////////////////////////////////////////////////////////////////	
	
	public Track createNewTrack(Bundle bundle, TrackData data, File contentsFile) throws JMOPMusicbaseException {
		if (contentsFile == null) {
			return musicbase.createNewTrack(bundle, data, null);
		}
		
		try (InputStream contentsStream = new BufferedInputStream(new FileInputStream(contentsFile))) {
			return musicbase.createNewTrack(bundle, data, contentsStream);
		} catch (IOException e) {
			throw new JMOPMusicbaseException("Could not load track contents", e);
		}
	}

	public void renameTrack(Track track, String newTitle) throws JMOPMusicbaseException {
		musicbase.renameTrack(track, newTitle);

	}

	public void moveTrack(Track track, Bundle newBundle) throws JMOPMusicbaseException {
		musicbase.moveTrack(track, newBundle);
		//TODO what to do with the playlist containing that track?
	}

	public void removeTrack(Track track) throws JMOPMusicbaseException {
		musicbase.removeTrack(track);
		//TODO update all the playlists containing that track
	}

	/**
	 * @deprecated replaced by {@link #updateTrack(Track, TrackData)}
	 * @param track
	 * @throws JMOPMusicbaseException
	 */
	@Deprecated
	public void trackUpdated(Track track) throws JMOPMusicbaseException {
		musicbase.trackUpdated(track);
	}

	public void updateTrack(Track track, TrackData newData) throws JMOPMusicbaseException {
		track.setIdentifier(newData.getIdentifier());
		track.setDescription(newData.getDescription());
		track.setDuration(newData.getDuration());
		
		musicbase.trackUpdated(track);
	}
	
/////////////////////////////////////////////////////////////////////////////////////

}
