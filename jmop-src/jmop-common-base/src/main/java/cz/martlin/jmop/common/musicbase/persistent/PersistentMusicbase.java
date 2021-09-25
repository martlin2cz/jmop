package cz.martlin.jmop.common.musicbase.persistent;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class PersistentMusicbase implements BaseMusicbase {

	private final BaseInMemoryMusicbase inmemory;
	private final BaseMusicbaseStorage storage;

	public PersistentMusicbase(BaseInMemoryMusicbase inmemory, BaseMusicbaseStorage storage) {
		super();
		this.inmemory = inmemory;
		this.storage = storage;
	}

	@Override
	public void load() throws JMOPMusicbaseException {
		storage.load(inmemory);
	}

	@Override
	public Set<Bundle> bundles() throws JMOPMusicbaseException {
		return inmemory.bundles();
	}

	@Override
	public Set<Playlist> playlists(Bundle bundle) throws JMOPMusicbaseException {
		return inmemory.playlists(bundle);
	}

	@Override
	public Set<Track> tracks(Bundle bundle) throws JMOPMusicbaseException {
		return inmemory.tracks(bundle);
	}

	@Override
	public void addBundle(Bundle bundle) throws JMOPMusicbaseException {
		inmemory.addBundle(bundle);
	}
	
	@Override
	public Bundle createNewBundle(String name) throws JMOPMusicbaseException {
		Bundle bundle = inmemory.createNewBundle(name);
		storage.createBundle(bundle);
		return bundle;
	}

	@Override
	public void renameBundle(Bundle bundle, String newName) throws JMOPMusicbaseException {
		String oldName = bundle.getName();
		inmemory.renameBundle(bundle, newName);
		storage.renameBundle(bundle, oldName, newName);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPMusicbaseException {
		inmemory.removeBundle(bundle);
		storage.removeBundle(bundle);
	}

	@Override
	public void bundleUpdated(Bundle bundle) throws JMOPMusicbaseException {
		inmemory.bundleUpdated(bundle);
		storage.saveUpdatedBundle(bundle);
	}

	@Override
	public void addPlaylist(Playlist playlist) throws JMOPMusicbaseException {
		inmemory.addPlaylist(playlist);
	}
	
	@Override
	public Playlist createNewPlaylist(Bundle bundle, String name) throws JMOPMusicbaseException {
		Playlist playlist = inmemory.createNewPlaylist(bundle, name);
		storage.createPlaylist(playlist);
		return playlist;
	}

	@Override
	public void renamePlaylist(Playlist playlist, String newName) throws JMOPMusicbaseException {
		String oldName = playlist.getName();
		inmemory.renamePlaylist(playlist, newName);
		storage.renamePlaylist(playlist, oldName, newName);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPMusicbaseException {
		Bundle oldBundle = playlist.getBundle();
		inmemory.movePlaylist(playlist, newBundle);
		storage.movePlaylist(playlist, oldBundle, newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPMusicbaseException {
		inmemory.removePlaylist(playlist);
		storage.removePlaylist(playlist);
	}

	@Override
	public void playlistUpdated(Playlist playlist) throws JMOPMusicbaseException {
		inmemory.playlistUpdated(playlist);
		storage.saveUpdatedPlaylist(playlist);
	}

	@Override
	public void addTrack(Track track) throws JMOPMusicbaseException {
		inmemory.addTrack(track);
	}
	
	@Override
	public Track createNewTrack(Bundle bundle, TrackData data) throws JMOPMusicbaseException {
		Track track = inmemory.createNewTrack(bundle, data);
		storage.createTrack(track);
		return track;
	}

	@Override
	public void renameTrack(Track track, String newTitle) throws JMOPMusicbaseException {
		String oldTitle = track.getTitle();
		inmemory.renameTrack(track, newTitle);
		storage.renameTrack(track, oldTitle, newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle newBundle) throws JMOPMusicbaseException {
		Bundle oldBundle = track.getBundle();
		inmemory.moveTrack(track, newBundle);
		storage.moveTrack(track, oldBundle, newBundle);
	}

	@Override
	public void removeTrack(Track track) throws JMOPMusicbaseException {
		inmemory.removeTrack(track);
		storage.removeTrack(track);
	}

	@Override
	public void trackUpdated(Track track) throws JMOPMusicbaseException {
		inmemory.trackUpdated(track);
		storage.saveUpdatedTrack(track);
	}
	
	@Override
	public File trackFile(Track track) throws JMOPMusicbaseException {
		return storage.trackFile(track);
	}

}
