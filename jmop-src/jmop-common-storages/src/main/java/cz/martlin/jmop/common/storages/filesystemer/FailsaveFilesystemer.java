package cz.martlin.jmop.common.storages.filesystemer;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

public class FailsaveFilesystemer implements BaseMusicbaseFilesystemer {

	private final BaseMusicbaseFilesystemer delegee;
	private final BaseErrorReporter reporter;

	public FailsaveFilesystemer(BaseMusicbaseFilesystemer delegee, BaseErrorReporter reporter) {
		super();
		this.delegee = delegee;
		this.reporter = reporter;
	}

	@Override
	public void createBundle(Bundle bundle) {
		try {
			delegee.createBundle(bundle);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not create bundle in the filesystem", e);
		}
	}

	@Override
	public void renameBundle(String oldName, String newName) {
		try {
			delegee.renameBundle(oldName, newName);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not rename bundle in the filesystem", e);
		}
	}

	@Override
	public void removeBundle(Bundle bundle) {
		try {
			delegee.removeBundle(bundle);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not remove bundle in the filesystem", e);
		}
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) {
		try {
			delegee.renamePlaylist(playlist, oldName, newName);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not rename playlist in the filesystem", e);
		}
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) {
		try {
			delegee.movePlaylist(playlist, oldBundle, newBundle);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not move playlist in the filesystem", e);
		}
	}

	@Override
	public void removePlaylist(Playlist playlist) {
		try {
			delegee.removePlaylist(playlist);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not remove playlist in the filesystem", e);
		}
	}

	@Override
	public void createTrack(Track track, TrackFileCreationWay trackCreationWay, File trackSourceFile) {
		try {
			delegee.createTrack(track, trackCreationWay, trackSourceFile);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not create track in the filesystem", e);
		}
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) {
		try {
			delegee.renameTrack(track, oldTitle, newTitle);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not rename track in the filesystem", e);
		}
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) {
		try {
			delegee.moveTrack(track, oldBundle, newBundle);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not move track in the filesystem", e);
		}
	}

	@Override
	public void removeTrack(Track track) {
		try {
			delegee.removeTrack(track);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not remove track in the filesystem", e);
		}
	}
	
	@Override
	public void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile)
			throws JMOPPersistenceException {
		try {
			delegee.specifyTrackFile(track, trackFileHow, trackSourceFile);
		} catch (JMOPPersistenceException e) {
			reporter.report("Could not specify the track file", e);
		}
	}

}
