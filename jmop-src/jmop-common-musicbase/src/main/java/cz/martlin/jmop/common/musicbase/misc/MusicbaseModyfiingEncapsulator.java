package cz.martlin.jmop.common.musicbase.misc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
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
	private final MusicbaseListingEncapsulator listing;

	public MusicbaseModyfiingEncapsulator(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
		this.listing = new MusicbaseListingEncapsulator(musicbase);
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
		listing.tracks(bundle).forEach((t) -> {
			try { removeTrack(t); } catch (JMOPMusicbaseException e) { throw new RuntimeException(e); }
		});
		listing.playlists(bundle).forEach((p) -> {
			try { removePlaylist(p); } catch (JMOPMusicbaseException e) { throw new RuntimeException(e); }
		});
		
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

	public void movePlaylist(Playlist playlist, Bundle newBundle, boolean copyTracks) throws JMOPMusicbaseException {
		if (copyTracks) {
			listing.tracks(playlist).forEach((t) -> {
				try { copyTrack(t, newBundle); } catch (JMOPMusicbaseException e) { throw new RuntimeException(e); }
			});
		} else {
			listing.tracks(playlist).forEach((t) -> {
				try { moveTrack(t, newBundle); } catch (JMOPMusicbaseException e) { throw new RuntimeException(e); }
			});
		}
		
		musicbase.movePlaylist(playlist, newBundle);
	}


	public void removePlaylist(Playlist playlist) throws JMOPMusicbaseException {
		musicbase.removePlaylist(playlist);
	}

	public void playlistUpdated(Playlist playlist) throws JMOPMusicbaseException {
		musicbase.playlistUpdated(playlist);
	}


	private void removeTrackFromPlaylist(Track track, Playlist playlist) {
		PlaylistModifier modifier = new PlaylistModifier(playlist);
		modifier.removeAll(track);
	}
	
/////////////////////////////////////////////////////////////////////////////////////	
	
	public Track createNewTrack(Bundle bundle, TrackData data, File contentsFile) throws JMOPMusicbaseException {
		if (contentsFile == null) {
			return musicbase.createNewTrack(bundle, data, null);
			
		} else {
			try (InputStream contentsStream = new BufferedInputStream(new FileInputStream(contentsFile))) {
				return musicbase.createNewTrack(bundle, data, contentsStream);
			} catch (IOException e) {
				throw new JMOPMusicbaseException("Could not load track contents", e);
			}
		}
	}


	public void copyTrack(Track track, Bundle newBundle) throws JMOPMusicbaseException {
		//TODO utilise
		TrackData data = new TrackData(track.getIdentifier(), track.getTitle(), track.getDescription(), track.getDuration());
		
		File trackFile = listing.trackFile(track);
		if (!trackFile.exists()) {
			trackFile = null;
		}
		
		createNewTrack(newBundle, data, trackFile);
	}
	
	public void renameTrack(Track track, String newTitle) throws JMOPMusicbaseException {
		musicbase.renameTrack(track, newTitle);

	}

	public void moveTrack(Track track, Bundle newBundle) throws JMOPMusicbaseException {
		listing.playlistsContaining(track).forEach((p) -> {
			removeTrackFromPlaylist(track, p);
		});
		
		musicbase.moveTrack(track, newBundle);
	}


	public void removeTrack(Track track) throws JMOPMusicbaseException {
		listing.playlistsContaining(track).forEach((p) -> {
			removeTrackFromPlaylist(track, p);
		});
		
		musicbase.removeTrack(track);
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
