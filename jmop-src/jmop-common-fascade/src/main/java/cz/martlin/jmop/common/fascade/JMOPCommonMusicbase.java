package cz.martlin.jmop.common.fascade;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.misc.AutoSavingPlaylistModifier;
import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;
import cz.martlin.jmop.common.musicbase.stats.MusicbaseStats;

/**
 * The JMOP fascade of the musicbase operations.
 * 
 * @author martin
 *
 */
public class JMOPCommonMusicbase {

	/**
	 * The musicbase (whole).
	 */
	protected final BaseMusicbase musicbase;
	/**
	 * The musicbase (listing).
	 */
	protected final MusicbaseListingEncapsulator listing;
	/**
	 * The musicbase (modifiing).
	 */
	protected final MusicbaseModyfiingEncapsulator modyfiing;

	/**
	 * Creates.
	 * 
	 * @param musicbase
	 * @param listing
	 * @param modyfiing
	 */
	public JMOPCommonMusicbase(BaseMusicbase musicbase, MusicbaseListingEncapsulator listing,
			MusicbaseModyfiingEncapsulator modyfiing) {
		super();
		this.musicbase = musicbase;
		this.listing = listing;
		this.modyfiing = modyfiing;
	}

	/**
	 * Use only in tests!
	 * 
	 * @return
	 */
	public BaseMusicbase getMusicbase() {
		return musicbase;
	}

	/**
	 * Returns bundle of given name.
	 * 
	 * @param bundleNameOrNot
	 * @return
	 */
	public Bundle bundleOfName(String bundleNameOrNot) {
		return listing.getBundle(bundleNameOrNot);
	}

	/**
	 * Returns the playlist of given bundle (optional) and name.
	 * 
	 * @param bundleOrNull
	 * @param playlistNameOrNot
	 * @return
	 */
	public Playlist playlistOfName(Bundle bundleOrNull, String playlistNameOrNot) {
		return listing.getPlaylist(bundleOrNull, playlistNameOrNot);
	}

	/**
	 * Returns the track of given bundle (optional) and title.
	 * 
	 * @param bundleOrNull
	 * @param trackTitleOrNot
	 * @return
	 */
	public Track trackOfTitle(Bundle bundleOrNull, String trackTitleOrNot) {
		return listing.getTrack(bundleOrNull, trackTitleOrNot);
	}

	/**
	 * Returns all the bundles.
	 * 
	 * @return
	 */
	public Set<Bundle> bundles() {
		return listing.bundles();
	}

	/**
	 * Returns all the playlists (or just of the given bundle).
	 * 
	 * @param bundleOrNull
	 * @return
	 */
	public Set<Playlist> playlists(Bundle bundleOrNull) {
		return listing.playlists(bundleOrNull);
	}

	/**
	 * Returns all the tracks (or just of the given bundle).
	 * 
	 * @param bundleOrNull
	 * @return
	 */
	public Set<Track> tracks(Bundle bundleOrNull) {
		return listing.tracks(bundleOrNull);
	}

	/**
	 * Returns the musicbase stats.
	 * 
	 * @return
	 */
	public MusicbaseStats getStats() {
		return new MusicbaseStats(musicbase);
	}

	/**
	 * Creates new bundle of the given name.
	 * 
	 * @param name
	 * @return
	 */
	public Bundle createNewBundle(String name) {
		return modyfiing.createNewBundle(name);
	}

	/**
	 * Renames the given bundle to new name.
	 * 
	 * @param bundle
	 * @param newName
	 */
	public void renameBundle(Bundle bundle, String newName) {
		modyfiing.renameBundle(bundle, newName);
	}

	/**
	 * Removes the given bundle.
	 * 
	 * @param bundle
	 */
	public void removeBundle(Bundle bundle) {
		modyfiing.removeBundle(bundle);
		// TODO stop if current?
	}

	/**
	 * Returns true if the given bundle is empty (i.e. has no tracks).
	 * 
	 * @param bundle
	 * @return
	 */
	public boolean isEmpty(Bundle bundle) {
		return tracks(bundle).isEmpty();
	}

	/**
	 * Creates new playlist in the given bundle.
	 * 
	 * @param bundle
	 * @param name
	 * @return
	 */
	public Playlist createNewPlaylist(Bundle bundle, String name) {
		return modyfiing.createNewPlaylist(bundle, name);
	}

	/**
	 * Renames the playlist to the new name.
	 * 
	 * @param playlist
	 * @param newName
	 */
	public void renamePlaylist(Playlist playlist, String newName) {
		modyfiing.renamePlaylist(playlist, newName);
	}

	/**
	 * Moves the playlist to the new bundle. If speficied, moves all its tracks with
	 * it.
	 * 
	 * @param playlist
	 * @param newBundle
	 * @param copyTracks
	 */
	public void movePlaylist(Playlist playlist, Bundle newBundle, boolean copyTracks) {
		modyfiing.movePlaylist(playlist, newBundle, copyTracks);
		// TODO stop if current?
	}

	/**
	 * Removes the given playlist.
	 * 
	 * @param playlist
	 */
	public void removePlaylist(Playlist playlist) {
		modyfiing.removePlaylist(playlist);
		// TODO stop if current?
	}

	/**
	 * Returns the playlist modifier of the given playlist. Automatically saves
	 * changes done in the playlist.
	 * 
	 * @param playlist
	 * @return
	 */
	public PlaylistModifier modifyPlaylist(Playlist playlist) {
		return new AutoSavingPlaylistModifier(playlist, modyfiing);
	}

	/**
	 * Returns true if the given playlist is empty (has no tracks).
	 * 
	 * @param playlist
	 * @return
	 */
	public boolean isEmpty(Playlist playlist) {
		return playlist.getTracks().count() == 0;
	}

	/**
	 * Creates new track, specified how to prepare the track file (if any).
	 * 
	 * @param bundle
	 * @param data
	 * @param trackFileHow
	 * @param trackFile
	 * @return
	 */
	public Track createNewTrack(Bundle bundle, TrackData data, TrackFileCreationWay trackFileHow, File trackFile) {
		return modyfiing.createNewTrack(bundle, data, trackFileHow, trackFile);
	}

	/**
	 * Renames the track to the new name.
	 * 
	 * @param track
	 * @param newTitle
	 */
	public void renameTrack(Track track, String newTitle) {
		modyfiing.renameTrack(track, newTitle);
	}

	/**
	 * Moves track to the new bundle.
	 * 
	 * @param track
	 * @param newBundle
	 */
	public void moveTrack(Track track, Bundle newBundle) {
		modyfiing.moveTrack(track, newBundle);
		// TODO what if in some playlist?
		// TODO to next if current?
	}

	/**
	 * Removes the given track.
	 * 
	 * @param track
	 */
	public void removeTrack(Track track) {
		modyfiing.removeTrack(track);
		// TODO to next if current?
	}

	/**
	 * Updates the data of the track to the given ones.
	 * 
	 * @param track
	 * @param newData
	 */
	public void updateTrack(Track track, TrackData newData) {
		modyfiing.updateTrack(track, newData);
	}

}