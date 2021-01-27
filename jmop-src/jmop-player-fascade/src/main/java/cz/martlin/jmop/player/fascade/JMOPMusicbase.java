package cz.martlin.jmop.player.fascade;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.misc.AutoSavingPlaylistModifier;
import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;

public class JMOPMusicbase {
	private final BaseMusicbase musicbase;
	private final MusicbaseListingEncapsulator listing;
	private final MusicbaseModyfiingEncapsulator modyfiing;
	
	public JMOPMusicbase(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
		
		this.listing = new MusicbaseListingEncapsulator(musicbase);
		this.modyfiing = new MusicbaseModyfiingEncapsulator(musicbase);
	}


	/**
	 * Use only in tests!
	 * @return
	 */
	public BaseMusicbase getMusicbase() {
		return musicbase;
	}
	
/////////////////////////////////////////////////////////////////

	public Bundle bundleOfName(String bundleNameOrNot)  {
		return listing.getBundle(bundleNameOrNot);
	}

	public Playlist playlistOfName(Bundle bundleOrNull, String playlistNameOrNot)  {
		return listing.getPlaylist(bundleOrNull, playlistNameOrNot);
	}

	public Track trackOfTitle(Bundle bundleOrNull, String trackTitleOrNot)  {
		return listing.getTrack(bundleOrNull, trackTitleOrNot);
	}

/////////////////////////////////////////////////////////////////
	public Set<Bundle> bundles()  {
		return listing.bundles();
	}

	public Set<Playlist> playlists(Bundle bundleOrNull)  {
		return listing.playlists(bundleOrNull);
	}

	public Set<Track> tracks(Bundle bundleOrNull)  {
		return listing.tracks(bundleOrNull);
	}

/////////////////////////////////////////////////////////////////

	public Bundle createNewBundle(String name)  {
		return modyfiing.createNewBundle(name);
	}

	public void renameBundle(Bundle bundle, String newName)  {
		modyfiing.renameBundle(bundle, newName);
	}

	public void removeBundle(Bundle bundle)  {
		modyfiing.removeBundle(bundle);
		//TODO stop if current?
	}

	public Playlist createNewPlaylist(Bundle bundle, String name)  {
		return modyfiing.createNewPlaylist(bundle, name);
	}

	public void renamePlaylist(Playlist playlist, String newName)  {
		modyfiing.renamePlaylist(playlist, newName);
	}

	public void movePlaylist(Playlist playlist, Bundle newBundle, boolean copyTracks)  {
		modyfiing.movePlaylist(playlist, newBundle, copyTracks);
		//TODO stop if current?
	}

	public void removePlaylist(Playlist playlist)  {
		modyfiing.removePlaylist(playlist);
		//TODO stop if current?
	}

	public PlaylistModifier modifyPlaylist(Playlist playlist) {
		return new AutoSavingPlaylistModifier(playlist, modyfiing);
	}
	
	public Track createNewTrack(Bundle bundle, TrackData data, File contentsFile)  {
		return modyfiing.createNewTrack(bundle, data, contentsFile);
	}

	public void renameTrack(Track track, String newTitle)  {
		modyfiing.renameTrack(track, newTitle);
	}

	public void moveTrack(Track track, Bundle newBundle)  {
		modyfiing.moveTrack(track, newBundle);
		//TODO to next if current?
	}

	public void removeTrack(Track track)  {
		modyfiing.removeTrack(track);
		//TODO to next if current?
	}

	public void updateTrack(Track track, TrackData newData)  {
		modyfiing.updateTrack(track, newData);
	}




}
