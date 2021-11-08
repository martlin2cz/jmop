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
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

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
	
	public void load()  {
		musicbase.load();
	}

	public void reload()  {
		musicbase.load();
	}
	
	public void terminate() {
		musicbase.terminate();
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////

	public Bundle createNewBundle(String name)  {
		return musicbase.createNewBundle(name);
	}

	public void renameBundle(Bundle bundle, String newName)  {
		musicbase.renameBundle(bundle, newName);
	}

	public void removeBundle(Bundle bundle)  {
		listing.tracks(bundle).forEach((t) -> removeTrack(t));
		listing.playlists(bundle).forEach((p) -> removePlaylist(p));
		
		musicbase.removeBundle(bundle);
	}

	public void bundleUpdated(Bundle bundle)  {
		musicbase.bundleUpdated(bundle);
	}

/////////////////////////////////////////////////////////////////////////////////////

	public Playlist createNewPlaylist(Bundle bundle, String name)  {
		return musicbase.createNewPlaylist(bundle, name);
	}

	public void renamePlaylist(Playlist playlist, String newName)  {
		musicbase.renamePlaylist(playlist, newName);
	}

	public void movePlaylist(Playlist playlist, Bundle newBundle, boolean copyTracks)  {
		if (copyTracks) {
			listing.tracks(playlist).forEach((t) -> copyTrack(t, newBundle));
		} else {
			listing.tracks(playlist).forEach((t) -> moveTrack(t, newBundle));
		}
		
		musicbase.movePlaylist(playlist, newBundle);
	}


	public void removePlaylist(Playlist playlist)  {
		musicbase.removePlaylist(playlist);
	}

	public void playlistUpdated(Playlist playlist)  {
		musicbase.playlistUpdated(playlist);
	}


	private void removeTrackFromPlaylist(Track track, Playlist playlist) {
		PlaylistModifier modifier = new PlaylistModifier(playlist);
		modifier.removeAll(track);
	}
	
/////////////////////////////////////////////////////////////////////////////////////	
	
	public Track createNewTrack(Bundle bundle, TrackData data, TrackFileCreationWay trackFileHow, File trackSourceFile)  {
		return musicbase.createNewTrack(bundle, data, trackFileHow, trackSourceFile);
	}


	public void copyTrack(Track track, Bundle newBundle)  {
		//TODO utilise
		TrackData data = new TrackData(track.getIdentifier(), track.getTitle(), track.getDescription(), track.getDuration());
		
		File trackFile = track.getFile();
		if (!trackFile.exists()) {
			trackFile = null;
		}
		
		createNewTrack(newBundle, data, TrackFileCreationWay.COPY_FILE, trackFile);
	}
	
	public void renameTrack(Track track, String newTitle)  {
		musicbase.renameTrack(track, newTitle);
		
		listing.playlistsContaining(track).forEach((p) -> playlistUpdated(p));
		Bundle bundle = track.getBundle();
		musicbase.bundleUpdated(bundle);
	}

	public void moveTrack(Track track, Bundle newBundle)  {
		listing.playlistsContaining(track).forEach((p) -> {
			removeTrackFromPlaylist(track, p);
		});

		Bundle oldBundle = track.getBundle();
		musicbase.moveTrack(track, newBundle);

		musicbase.bundleUpdated(oldBundle );
		musicbase.bundleUpdated(newBundle);
	}


	public void removeTrack(Track track)  {
		listing.playlistsContaining(track).forEach((p) -> {
			removeTrackFromPlaylist(track, p);
		});
		
		musicbase.removeTrack(track);
	}

	/**
	 * @deprecated replaced by {@link #updateTrack(Track, TrackData)}
	 * @param track
	 * @
	 */
	@Deprecated
	public void trackUpdated(Track track)  {
		musicbase.trackUpdated(track);
	}

	public void updateTrack(Track track, TrackData newData)  {
		track.setIdentifier(newData.getIdentifier());
		track.setDescription(newData.getDescription());
		track.setDuration(newData.getDuration());
		
		musicbase.trackUpdated(track);
	}


/////////////////////////////////////////////////////////////////////////////////////

}
