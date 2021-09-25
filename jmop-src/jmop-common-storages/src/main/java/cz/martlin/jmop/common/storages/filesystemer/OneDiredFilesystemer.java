package cz.martlin.jmop.common.storages.filesystemer;

import java.io.InputStream;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.AllInOneDirStorageComponent;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The filesystemer, which puts everything in single (root) folder.
 * 
 * @author martin
 *
 */
public class OneDiredFilesystemer implements BaseMusicbaseFilesystemer, AllInOneDirStorageComponent {

	public OneDiredFilesystemer() {
		super();
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
	public void createTrack(Track track, InputStream trackFileContents) throws JMOPPersistenceException {
		// okay, nothing to do
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

}
