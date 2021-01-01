package cz.martlin.jmop.common.storages.simples;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class InMemoryStorage implements BaseMusicbaseStorage {

	private final BaseInMemoryMusicbase inmemory;

	public InMemoryStorage(BaseInMemoryMusicbase inmemory) {
		super();
		this.inmemory = inmemory;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void load(BaseInMemoryMusicbase inmemoryTo) throws JMOPMusicbaseException {
		try {
			inmemory.bundles().forEach(b -> {
				createBundle(inmemoryTo, b);
			});
		} catch (JMOPMusicbaseException | RuntimeException e) {
			throw new JMOPMusicbaseException("Cannot load", e);
		}
	}

	private void createBundle(BaseInMemoryMusicbase inmemoryTo, Bundle originalBundle) {
		try {
			Bundle newBundle = inmemoryTo.createNewBundle(originalBundle.getName());

			inmemory.playlists(originalBundle).forEach(p -> addPlaylist(inmemoryTo, newBundle, p));
			inmemory.tracks(originalBundle).forEach(t -> addTrack(inmemoryTo, newBundle, t));
		} catch (JMOPMusicbaseException e) {
			throw new RuntimeException("Cannot load bundle " + originalBundle, e);
		}
	}

	private void addPlaylist(BaseInMemoryMusicbase inmemoryTo, Bundle bundle, Playlist playlist) {
		try {
			inmemoryTo.createNewPlaylist(bundle, playlist.getName());
		} catch (JMOPMusicbaseException e) {
			throw new RuntimeException("Cannot load playlist " + playlist, e);
		}
	}

	private void addTrack(BaseInMemoryMusicbase inmemoryTo, Bundle bundle, Track track) {
		try {
			TrackData td = trackDataOfTrack(track);
			inmemoryTo.createNewTrack(bundle, td);
		} catch (JMOPMusicbaseException e) {
			throw new RuntimeException("Cannot load track " + track, e);
		}
	}

	private TrackData trackDataOfTrack(Track track) {
		TrackData td = new TrackData(track.getIdentifier(), track.getTitle(), track.getDescription(),
				track.getDuration());
		return td;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createBundle(Bundle bundle) throws JMOPMusicbaseException {
		inmemory.createNewBundle(bundle.getName());
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPMusicbaseException {
		inmemory.renameBundle(bundle, newName);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPMusicbaseException {
		inmemory.removeBundle(bundle);
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle) {
		// okay
	}

	@Override
	public void createPlaylist(Playlist playlist) throws JMOPMusicbaseException {
		inmemory.createNewPlaylist(playlist.getBundle(), playlist.getName());
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPMusicbaseException {
		inmemory.renamePlaylist(playlist, newName);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPMusicbaseException {
		inmemory.movePlaylist(playlist, newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPMusicbaseException {
		inmemory.removePlaylist(playlist);
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) {
		// okay
	}

	@Override
	public void createTrack(Track track) throws JMOPMusicbaseException {
		TrackData td = trackDataOfTrack(track);
		inmemory.createNewTrack(track.getBundle(), td);
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPMusicbaseException {
		inmemory.renameTrack(track, newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPMusicbaseException {
		inmemory.moveTrack(track, newBundle);
	}

	@Override
	public void removeTrack(Track track) throws JMOPMusicbaseException {
		inmemory.removeTrack(track);
	}

	@Override
	public void saveUpdatedTrack(Track track) {
		// okay
	}
	
	@Override
	public File trackFile(Track track) throws JMOPMusicbaseException {
		try {
			Path path = Files.createTempFile("track", track.getTitle());
			return path.toFile();
		} catch (IOException e) {
			throw new JMOPMusicbaseException("Cannot obtain track file", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "InMemoryStorage [" + inmemory + "]";
	}

}