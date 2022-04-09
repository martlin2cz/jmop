package cz.martlin.jmop.common.storages.dflt;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

/**
 * Just the storage wrapper, which keeps an all tracks playlist in each bundle,
 * and automatically updates that playlist when any track gets
 * created/removed/renamed/moved.
 * 
 * @author martin
 *
 */
public class AllTracksPlaylistStorage implements BaseMusicbaseStorage {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseMusicbaseStorage delegee;
	private final BaseInMemoryMusicbase inmemory;
	private final String allTracksPlaylistName;

	public AllTracksPlaylistStorage(BaseMusicbaseStorage delegee, BaseInMemoryMusicbase inmemory,
			String allTracksPlaylistName) {
		super();
		this.delegee = delegee;
		this.inmemory = inmemory;
		this.allTracksPlaylistName = allTracksPlaylistName;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void load(BaseInMemoryMusicbase inmemory) {
		delegee.load(inmemory);
	}

	@Override
	public void terminate(BaseInMemoryMusicbase inmemory) {
		delegee.terminate(inmemory);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createBundle(Bundle bundle) {
		delegee.createBundle(bundle);
		createAllTracksPlaylist(bundle);
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPRuntimeException {
		delegee.renameBundle(bundle, oldName, newName);
		updateAllTracksPlaylist(bundle);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPRuntimeException {
		removeAllTracksPlaylist(bundle);
		delegee.removeBundle(bundle);
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle) throws JMOPRuntimeException {
		delegee.saveUpdatedBundle(bundle);
		updateAllTracksPlaylist(bundle);
	}

	@Override
	public void createPlaylist(Playlist playlist) {
		delegee.createPlaylist(playlist);
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) {
		if (oldName.equals(allTracksPlaylistName)) {
			LOG.warn("Atemp to rename the all track playlist was made, skipping");
			return; //TODO make a copy
		}
		
		delegee.renamePlaylist(playlist, oldName, newName);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) {
		if (playlist.getName().equals(allTracksPlaylistName)) {
			LOG.warn("Atemp to move the all track playlist was made, skipping");
			return; //TODO make a copy
		}
		
		delegee.movePlaylist(playlist, oldBundle, newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) {
		if (playlist.getName().equals(allTracksPlaylistName)) {
			LOG.warn("Atemp to remove the all track playlist was made, skipping");
			return;
		}
		
		delegee.removePlaylist(playlist);
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) {
		delegee.saveUpdatedPlaylist(playlist);
	}

	@Override
	public void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile)
			throws JMOPRuntimeException {
		
		delegee.createTrack(track, trackCreationWay, trackSourceFile);
		addTrackToAllTracksPlaylist(track);
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPRuntimeException {
		delegee.renameTrack(track, oldTitle, newTitle);
		updateAllTracksPlaylistOfTrack(track);
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPRuntimeException {
		delegee.moveTrack(track, oldBundle, newBundle);
		removeTrackFromAllTracksPlaylist(oldBundle, track);
		addTrackToAllTracksPlaylist(track);
	}

	@Override
	public void removeTrack(Track track) throws JMOPRuntimeException {
		delegee.removeTrack(track);
		removeTrackFromAllTracksPlaylist(track);
	}

	@Override
	public void saveUpdatedTrack(Track track) throws JMOPRuntimeException {
		delegee.saveUpdatedTrack(track);
		updateAllTracksPlaylistOfTrack(track);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void createAllTracksPlaylist(Bundle bundle) {
		LOG.info("Creating ATP for bundle {}", bundle.getName());

		Playlist playlist = new Playlist(bundle, allTracksPlaylistName, Metadata.createNew());
		inmemory.addPlaylist(playlist);
		delegee.createPlaylist(playlist);
	}

	private void updateAllTracksPlaylist(Bundle bundle) throws JMOPRuntimeException {
		LOG.info("Saving updated ATP of bundle {}", bundle.getName());

		Playlist playlist = allTracksPlaylistFor(bundle);
		inmemory.playlistUpdated(playlist);
		delegee.saveUpdatedPlaylist(playlist);
	}

	private void removeAllTracksPlaylist(Bundle bundle) throws JMOPRuntimeException {
		LOG.info("Removing ATP of bundle {}", bundle.getName());

		Playlist playlist = allTracksPlaylistFor(bundle);
		inmemory.removePlaylist(playlist);
		delegee.removePlaylist(playlist);
	}

	private void addTrackToAllTracksPlaylist(Track track) throws JMOPRuntimeException {
		LOG.info("Adding track {} to its ATP", track.getTitle());

		Bundle bundle = track.getBundle();
		Playlist playlist = allTracksPlaylistFor(bundle);
		playlist.addTrack(track);

		inmemory.playlistUpdated(playlist);
		delegee.saveUpdatedPlaylist(playlist);
	}


	private void removeTrackFromAllTracksPlaylist(Bundle abundle, Track track) {
		LOG.info("Removing track {} from {} bundle's ATP", abundle.getName(), track.getTitle());
		
		Playlist playlist = allTracksPlaylistFor(abundle);
		playlist.removeTrack(track);

		inmemory.playlistUpdated(playlist);
		delegee.saveUpdatedPlaylist(playlist);
	}

	private void removeTrackFromAllTracksPlaylist(Track track) throws JMOPRuntimeException {
		LOG.info("Removing track {} from its ATP", track.getTitle());

		Bundle bundle = track.getBundle();
		Playlist playlist = allTracksPlaylistFor(bundle);
		playlist.removeTrack(track);

		inmemory.playlistUpdated(playlist);
		delegee.saveUpdatedPlaylist(playlist);
	}

	private void updateAllTracksPlaylistOfTrack(Track track) throws JMOPRuntimeException {
		LOG.info("Updating ATP of track {}", track.getTitle());

		Bundle bundle = track.getBundle();
		Playlist playlist = allTracksPlaylistFor(bundle);

		inmemory.playlistUpdated(playlist);
		delegee.saveUpdatedPlaylist(playlist);
	}

/////////////////////////////////////////////////////////////////////////////////////

	private Playlist allTracksPlaylistFor(Bundle bundle) throws JMOPRuntimeException {
		return inmemory.playlists(bundle).stream() //
				.filter(p -> allTracksPlaylistName.equals(p.getName())) //
				.findAny().orElseThrow(() -> new JMOPRuntimeException(
						"The bundle " + bundle.getName() + " does not have an all tracks playlist."));
	}

}
