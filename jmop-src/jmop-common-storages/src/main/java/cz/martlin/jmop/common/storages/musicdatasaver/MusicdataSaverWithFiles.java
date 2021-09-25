package cz.martlin.jmop.common.storages.musicdatasaver;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.locators.BasePlaylistLocator;
import cz.martlin.jmop.common.storages.locators.impls.AbstractCommonLocator;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

/**
 * Just an Musicdata saver, which enhances its calls to files or dirs obtained
 * by the locator.
 * 
 * @author martin
 *
 */
public class MusicdataSaverWithFiles implements BaseMusicdataSaver {

	private final BaseBundleDataLocator bundlesLocator;
	private final BasePlaylistLocator playlistsLocator;
	private final TracksLocator tracksLocator;
	private final BaseMusicdataFileManipulator manipulator;
	private final BaseInMemoryMusicbase inmemory;

	public MusicdataSaverWithFiles(AbstractCommonLocator locator, BaseMusicdataFileManipulator manipulator, BaseInMemoryMusicbase inmemory) {
		super();
		this.bundlesLocator = locator;
		this.playlistsLocator = locator;
		this.tracksLocator = locator;
		this.manipulator = manipulator;
		this.inmemory = inmemory;
	}

	public MusicdataSaverWithFiles(BaseBundleDataLocator bundlesLocator, BasePlaylistLocator playlistsLocator,
			TracksLocator tracksLocator, BaseMusicdataFileManipulator manipulator, BaseInMemoryMusicbase inmemory) {
		super();
		this.bundlesLocator = bundlesLocator;
		this.playlistsLocator = playlistsLocator;
		this.tracksLocator = tracksLocator;
		this.manipulator = manipulator;
		this.inmemory = inmemory;
	}
	
	public BaseMusicdataFileManipulator getManipulator() {
		return manipulator;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void saveBundleData(Bundle bundle, SaveReason reason) {
		File file = bundlesLocator.bundleDataFile(bundle);
		
		Set<Track> tracks = inmemory.tracks(bundle);
		try {
			manipulator.saveBundleData(bundle, tracks, file, tracksLocator);
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not save bundle " + bundle.getName(), e);
		}
	}

	@Override
	public void savePlaylistData(Playlist playlist, SaveReason reason) {
		File file = playlistsLocator.playlistFile(playlist);
		try {
			manipulator.savePlaylistData(playlist, file, tracksLocator);
		} catch (JMOPPersistenceException e) {
			throw new JMOPRuntimeException("Could not save playlist " + playlist.getName(), e);
		}
	}

	@Override
	public void saveTrackData(Track track, SaveReason reason) {
		Bundle bundle = track.getBundle();
		saveBundleData(bundle, SaveReason.UPDATED);
	}

}
