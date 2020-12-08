package cz.martlin.jmop.common.musicbase.persistent;

import java.util.List;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
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
	public List<String> bundlesNames() throws JMOPSourceException {
		return inmemory.bundlesNames();
	}

	@Override
	public Bundle createBundle(String name) throws JMOPSourceException {
		Bundle bundle = inmemory.createBundle(name);
		storage.createBundle(bundle);
		return bundle;
	}

	@Override
	public Bundle getBundle(String name) throws JMOPSourceException {
		return inmemory.getBundle(name);
	}

	@Override
	public List<String> playlistsNames(Bundle bundle) throws JMOPSourceException {
		return inmemory.playlistsNames(bundle);
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
	public Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		return inmemory.getPlaylist(bundle, name);
	}

	@Override
	public void updateMetadata(Bundle bundle, Metadata newMetadata) throws JMOPSourceException {
		inmemory.updateMetadata(bundle, newMetadata);
		storage.saveUpdatedBundle(bundle);
	}

	@Override
	public List<String> listTracksIDs(Bundle bundle) throws JMOPSourceException {
		return inmemory.listTracksIDs(bundle);
	}

	@Override
	public Playlist createPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		Playlist playlist = inmemory.createPlaylist(bundle, name);
		storage.createPlaylist(playlist);
		return playlist;
	}

	@Override
	public Track getTrack(Bundle bundle, String id) throws JMOPSourceException {
		return inmemory.getTrack(bundle, id);
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
	public void updateMetadata(Playlist playlist, Metadata newMetadata) throws JMOPSourceException {
		inmemory.updateMetadata(playlist, newMetadata);
		storage.saveUpdatedPlaylist(playlist);
	}

	@Override
	public void saveModifiedTracklist(Playlist playlist) throws JMOPSourceException {
		inmemory.saveModifiedTracklist(playlist);
		storage.saveUpdatedPlaylist(playlist);
	}

	@Override
	public Track createTrack(Bundle bundle, TrackData data) throws JMOPSourceException {
		Track track = inmemory.createTrack(bundle, data);
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
	public void updateMetadata(Track track, Metadata newMetadata) throws JMOPSourceException {
		inmemory.updateMetadata(track, newMetadata);
		storage.saveUpdatedTrack(track);
	}

}
