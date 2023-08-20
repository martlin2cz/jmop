package cz.martlin.jmop.common.musicbase.persistent;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;

public class PersistentMusicbase implements BaseMusicbase {

	private final BaseInMemoryMusicbase inmemory;
	private final BaseMusicbaseStorage storage;

	public PersistentMusicbase(BaseInMemoryMusicbase inmemory, BaseMusicbaseStorage storage) {
		super();
		this.inmemory = inmemory;
		this.storage = storage;
	}

	/////////////////////////////////////////////////////////////////
	@Override
	public void load() {
		storage.load(inmemory);
	}

	@Override
	public void terminate() {
		storage.terminate(inmemory);
	}

	/////////////////////////////////////////////////////////////////
	@Override
	public Set<Bundle> bundles() {
		return inmemory.bundles();
	}

	@Override
	public Set<Playlist> playlists(Bundle bundle) {
		return inmemory.playlists(bundle);
	}

	@Override
	public Set<Track> tracks(Bundle bundle) {
		return inmemory.tracks(bundle);
	}

	/////////////////////////////////////////////////////////////////
	@Override
	public void addBundle(Bundle bundle) {
		inmemory.addBundle(bundle);
	}

	@Override
	public Bundle createNewBundle(String name) {
		Bundle bundle = inmemory.createNewBundle(name);
		storage.createBundle(bundle);
		return bundle;
	}

	@Override
	public void renameBundle(Bundle bundle, String newName) {
		String oldName = bundle.getName();
		inmemory.renameBundle(bundle, newName);
		storage.renameBundle(bundle, oldName, newName);
	}

	@Override
	public void removeBundle(Bundle bundle) {
		storage.removeBundle(bundle);
		inmemory.removeBundle(bundle);
	}

	@Override
	public void bundleUpdated(Bundle bundle) {
		inmemory.bundleUpdated(bundle);
		storage.saveUpdatedBundle(bundle);
	}

	/////////////////////////////////////////////////////////////////
	@Override
	public void addPlaylist(Playlist playlist) {
		inmemory.addPlaylist(playlist);
	}

	@Override
	public Playlist createNewPlaylist(Bundle bundle, String name) {
		Playlist playlist = inmemory.createNewPlaylist(bundle, name);
		storage.createPlaylist(playlist);
		return playlist;
	}

	@Override
	public void renamePlaylist(Playlist playlist, String newName) {
		String oldName = playlist.getName();
		inmemory.renamePlaylist(playlist, newName);
		storage.renamePlaylist(playlist, oldName, newName);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle newBundle) {
		Bundle oldBundle = playlist.getBundle();
		inmemory.movePlaylist(playlist, newBundle);
		storage.movePlaylist(playlist, oldBundle, newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) {
		storage.removePlaylist(playlist);
		inmemory.removePlaylist(playlist);
	}

	@Override
	public void playlistUpdated(Playlist playlist) {
		inmemory.playlistUpdated(playlist);
		storage.saveUpdatedPlaylist(playlist);
	}

	/////////////////////////////////////////////////////////////////
	@Override
	public void addTrack(Track track) {
		inmemory.addTrack(track);
		storage.createTrack(track, null, null);
	}

	@Override
	public Track createNewTrack(Bundle bundle, TrackData data, TrackFileCreationWay trackCreationWay, File trackSourceFile) {
		Track track = inmemory.createNewTrack(bundle, data, trackCreationWay, trackSourceFile);
		storage.createTrack(track, trackCreationWay, trackSourceFile);
		return track;
	}
	
	@Override
	public void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile) {
		storage.specifyTrackFile(track, trackFileHow, trackSourceFile);
	}

	@Override
	public void renameTrack(Track track, String newTitle) {
		String oldTitle = track.getTitle();
		inmemory.renameTrack(track, newTitle);
		storage.renameTrack(track, oldTitle, newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle newBundle) {
		Bundle oldBundle = track.getBundle();
		inmemory.moveTrack(track, newBundle);
		storage.moveTrack(track, oldBundle, newBundle);
	}

	@Override
	public void removeTrack(Track track) {
		storage.removeTrack(track);
		inmemory.removeTrack(track);
	}

	@Override
	public void trackUpdated(Track track) {
		inmemory.trackUpdated(track);
		storage.saveUpdatedTrack(track);
	}

	/////////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		return "PersistentMusicbase [inmemory=" + inmemory + ", storage=" + storage + "]";
	}

}
