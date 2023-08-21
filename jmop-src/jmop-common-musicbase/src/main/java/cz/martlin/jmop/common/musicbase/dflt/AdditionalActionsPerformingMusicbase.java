package cz.martlin.jmop.common.musicbase.dflt;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;

/**
 * The inmemory musicbase wrapper which does all the extra work nescessary to
 * keep the musicbase consistent, i.e. when removing bundle removing its
 * playlists and tracks too, when renaming track updating all the playlists
 * containing that and so.
 * 
 * @author martin
 *
 */
public class AdditionalActionsPerformingMusicbase implements BaseInMemoryMusicbase {

	private final BaseInMemoryMusicbase delegee;

	public AdditionalActionsPerformingMusicbase(BaseInMemoryMusicbase delegee) {
		super();
		this.delegee = delegee;
	}

	///////////////////////////////////////////////////////////////////////////
	public void addBundle(Bundle bundle) {
		delegee.addBundle(bundle);
	}

	public void addPlaylist(Playlist playlist) {
		delegee.addPlaylist(playlist);
	}

	public void addTrack(Track track) {
		delegee.addTrack(track);
	}

	public Set<Bundle> bundles() {
		return delegee.bundles();
	}

	public Set<Playlist> playlists(Bundle bundle) {
		return delegee.playlists(bundle);
	}

	public Set<Track> tracks(Bundle bundle) {
		return delegee.tracks(bundle);
	}

	///////////////////////////////////////////////////////////////////////////
	public Bundle createNewBundle(String name) {
		return delegee.createNewBundle(name);
	}

	public void renameBundle(Bundle bundle, String newName) {
		delegee.renameBundle(bundle, newName);
	}

	public void removeBundle(Bundle bundle) {
		removeAllTracksOfBundle(bundle);
		removeAllPlaylistsOfBundle(bundle);

		delegee.removeBundle(bundle);
	}

	protected void removeAllPlaylistsOfBundle(Bundle bundle) {
		playlists(bundle).forEach((p) -> removePlaylist(p));
	}

	protected void removeAllTracksOfBundle(Bundle bundle) {
		tracks(bundle).forEach((t) -> removeTrack(t));
	}

	public void bundleUpdated(Bundle bundle) {
		delegee.bundleUpdated(bundle);
	}

/////////////////////////////////////////////////////////////////////////////////////

	public Playlist createNewPlaylist(Bundle bundle, String name) {
		return delegee.createNewPlaylist(bundle, name);
	}

	public void renamePlaylist(Playlist playlist, String newName) {
		delegee.renamePlaylist(playlist, newName);
	}

	public void movePlaylist(Playlist playlist, Bundle newBundle) {
		moveAllTracksOfPlaylistToNewBundle(playlist, newBundle);

		delegee.movePlaylist(playlist, newBundle);
	}

	protected void moveAllTracksOfPlaylistToNewBundle(Playlist playlist, Bundle newBundle) {
		new HashSet<>(playlist.getTracks().getTracks()).forEach((t) -> moveTrack(t, newBundle));
	}

	public void removePlaylist(Playlist playlist) {
		delegee.removePlaylist(playlist);
	}

	public void playlistUpdated(Playlist playlist) {
		delegee.playlistUpdated(playlist);
	}

/////////////////////////////////////////////////////////////////////////////////////	

	public Track createNewTrack(Bundle bundle, TrackData data, TrackFileCreationWay trackFileHow,
			File trackSourceFile) {
		return delegee.createNewTrack(bundle, data, trackFileHow, trackSourceFile);
	}

	public void renameTrack(Track track, String newTitle) {
		delegee.renameTrack(track, newTitle);

		updateAllPlaylistsContainingTrack(track);
		updateBundleContainingTrack(track);
	}

	protected void updateBundleContainingTrack(Track track) {
		Bundle bundle = track.getBundle();
		delegee.bundleUpdated(bundle);
	}

	protected void updateAllPlaylistsContainingTrack(Track track) {
		playlistsContaining(track).forEach((p) -> playlistUpdated(p));
	}

	public void moveTrack(Track track, Bundle newBundle) {
		removeTrackFromAllPlaylists(track);

		Bundle oldBundle = track.getBundle();
		delegee.moveTrack(track, newBundle);

		updateOriginalTrackBundle(track, oldBundle);
		updateNewTrackBundle(track, newBundle);
	}

	protected void updateOriginalTrackBundle(Track track, Bundle oldBundle) {
		delegee.bundleUpdated(oldBundle);
	}

	protected void updateNewTrackBundle(Track track, Bundle newBundle) {
		delegee.bundleUpdated(newBundle);
	}

	protected void removeTrackFromAllPlaylists(Track track) {
		playlistsContaining(track).forEach((p) -> {
			removeTrackFromPlaylist(track, p);
		});
	}

	public void removeTrack(Track track) {
		removeTrackFromAllPlaylists(track);

		delegee.removeTrack(track);
	}

	public void trackUpdated(Track track) {
		delegee.trackUpdated(track);
	}
	
	@Override
	public void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile) {
		delegee.specifyTrackFile(track, trackFileHow, trackSourceFile);
	}
	///////////////////////////////////////////////////////////////////////////

	private Set<Playlist> playlistsContaining(Track track) {
		Bundle bundle = track.getBundle();

		return playlists(bundle).stream() //
				.filter(p -> p.getTracks().getTracks().contains(track)) //
				.collect(Collectors.toSet());
	}

	private void removeTrackFromPlaylist(Track track, Playlist playlist) {
		PlaylistModifier modifier = new PlaylistModifier(playlist);
		modifier.removeAll(track);
	}
	///////////////////////////////////////////////////////////////////////////

}
