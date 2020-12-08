package cz.martlin.jmop.common.musicbase.persistent;

import java.util.Set;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class PersistentMusicbase implements BaseMusicbase {

	private final BaseInMemoryMusicbase inmemory;
	private final BaseMusicbaseStorage storage;

	public PersistentMusicbase(BaseInMemoryMusicbase inmemory, BaseMusicbaseStorage storage) {
		super();
		this.inmemory = inmemory;
		this.storage = storage;
	}

	@Override
	public void load() throws JMOPSourceException {
		storage.load(inmemory);
	}

	@Override
	public Set<Bundle> bundles() throws JMOPSourceException {
		return inmemory.bundles();
	}

	@Override
	public Set<Playlist> playlists(Bundle bundle) throws JMOPSourceException {
		return inmemory.playlists(bundle);
	}

	@Override
	public Set<Track> tracks(Bundle bundle) throws JMOPSourceException {
		return inmemory.tracks(bundle);
	}

	@Override
	public Bundle createBundle(Bundle bundleData) throws JMOPSourceException {
		Bundle bundle = inmemory.createBundle(bundleData);
		return bundle;
	}
	
	@Override
	public Bundle createNewBundle(String name) throws JMOPSourceException {
		Bundle bundle = inmemory.createNewBundle(name);
		storage.createBundle(bundle);
		return bundle;
	}

	@Override
	public void renameBundle(Bundle bundle, String newName) throws JMOPSourceException {
		String oldName = bundle.getName();
		inmemory.renameBundle(bundle, newName);
		storage.renameBundle(bundle, oldName, newName);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPSourceException {
		inmemory.removeBundle(bundle);
		storage.removeBundle(bundle);
	}

	@Override
	public void bundleUpdated(Bundle bundle) throws JMOPSourceException {
		inmemory.bundleUpdated(bundle);
		storage.saveUpdatedBundle(bundle);
	}

	@Override
	public Playlist createPlaylist(Playlist playlistData) throws JMOPSourceException {
		Playlist playlist = inmemory.createPlaylist(playlistData);
		return playlist;
	}
	
	@Override
	public Playlist createNewPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		Playlist playlist = inmemory.createNewPlaylist(bundle, name);
		storage.createPlaylist(playlist);
		return playlist;
	}

	@Override
	public void renamePlaylist(Playlist playlist, String newName) throws JMOPSourceException {
		String oldName = playlist.getName();
		inmemory.renamePlaylist(playlist, newName);
		storage.renamePlaylist(playlist, oldName, newName);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPSourceException {
		Bundle oldBundle = playlist.getBundle();
		inmemory.movePlaylist(playlist, newBundle);
		storage.movePlaylist(playlist, oldBundle, newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPSourceException {
		inmemory.removePlaylist(playlist);
		storage.removePlaylist(playlist);
	}

	@Override
	public void playlistUpdated(Playlist playlist) throws JMOPSourceException {
		inmemory.playlistUpdated(playlist);
		storage.saveUpdatedPlaylist(playlist);
	}

	@Override
	public Track createTrack(Track trackData) throws JMOPSourceException {
		Track track = inmemory.createTrack(trackData);
		return track;
	}
	
	@Override
	public Track createNewTrack(Bundle bundle, TrackData data) throws JMOPSourceException {
		Track track = inmemory.createNewTrack(bundle, data);
		storage.createTrack(track);
		return track;
	}

	@Override
	public void renameTrack(Track track, String newTitle) throws JMOPSourceException {
		String oldTitle = track.getTitle();
		inmemory.renameTrack(track, newTitle);
		storage.renameTrack(track, oldTitle, newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle newBundle) throws JMOPSourceException {
		Bundle oldBundle = track.getBundle();
		inmemory.moveTrack(track, newBundle);
		storage.moveTrack(track, oldBundle, newBundle);
	}

	@Override
	public void removeTrack(Track track) throws JMOPSourceException {
		inmemory.removeTrack(track);
		storage.removeTrack(track);
	}

	@Override
	public void trackUpdated(Track track) throws JMOPSourceException {
		inmemory.trackUpdated(track);
		storage.saveUpdatedTrack(track);
	}

}
