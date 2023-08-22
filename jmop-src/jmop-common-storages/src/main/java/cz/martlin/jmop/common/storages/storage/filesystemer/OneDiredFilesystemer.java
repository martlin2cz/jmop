package cz.martlin.jmop.common.storages.storage.filesystemer;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.common.storages.components.AllInOneDirStorageComponent;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;
import cz.martlin.jmop.common.storages.storage.filesystemer.trackfile.TrackFileCreater;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The filesystemer, which puts everything in single (root) folder.
 * 
 * Since no filesystem operations are nescessary (no, creating dir or moving
 * files about), this class just handles the creation of the track file.
 * 
 * It's component of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public class OneDiredFilesystemer implements BaseMusicbaseFilesystemer, AllInOneDirStorageComponent {

	private final TrackFileCreater trackCreater;

	public OneDiredFilesystemer(TrackFileCreater trackCreater) {
		super();
		this.trackCreater = trackCreater;
	}

	@Override
	public void createBundle(Bundle bundle) throws JMOPPersistenceException {
		// okay, nothing to do
	}

	@Override
	public void renameBundle(String oldName, String newName) throws JMOPPersistenceException {
		// okay, nothing to do
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPPersistenceException {
		// okay, nothing to do
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPPersistenceException {
		// okay, nothing to do
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPPersistenceException {
		// okay, nothing to do
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPPersistenceException {
		// okay, nothing to do
	}

	@Override
	public void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile)
			throws JMOPPersistenceException {

		trackCreater.performTrackFileCreate(track, trackCreationWay, trackSourceFile);
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPPersistenceException {
		// okay, nothing to do
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPPersistenceException {
		// okay, nothing to do
	}

	@Override
	public void removeTrack(Track track) throws JMOPPersistenceException {
		// okay, nothing to do// TODO Auto-generated method stub
	}

	@Override
	public void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile)
			throws JMOPPersistenceException {

		trackCreater.performTrackFileCreate(track, trackFileHow, trackSourceFile);
	}

}
