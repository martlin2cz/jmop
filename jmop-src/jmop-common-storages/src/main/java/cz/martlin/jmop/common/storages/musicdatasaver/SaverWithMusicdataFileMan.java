package cz.martlin.jmop.common.storages.musicdatasaver;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.impls.AbstractCommonLocator;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
//import cz.martlin.jmop.common.storages.utils.FilesLocatorExtension;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

/**
 * Abstract saver, which works with the musicdata file manipulator. Saves of
 * bundles and playlists are delegated to that object.
 * 
 * Subclasses may specify some more technical bits.
 * 
 * @author martin
 * @deprecated all done by the superclass
 */
@Deprecated
public class SaverWithMusicdataFileMan extends MusicdataSaverWithFiles {

	private final TracksLocator tracksSource;
	private final BaseMusicdataFileManipulator manipulator;
	private final BaseBundleDataLocator locator;

	public SaverWithMusicdataFileMan(BaseBundlesDirLocator locator, BaseMusicdataFileManipulator manipulator, BaseBundleDataLocator bundleDataLocator) {
		super((AbstractCommonLocator) locator, manipulator, null);
//		super(bundleDataLocator, null, null, manipulator);
		throw new UnsupportedOperationException("do not");
//		this.manipulator = manipulator;
//		this.tracksSource = new FilesLocatorExtension(locator);
//		this.locator = bundleDataLocator;
	}

///////////////////////////////////////////////////////////////////////////////

//	@Override
//	public void saveBundleDataToFile(File bundleDir, Bundle bundle, SaveReason reason) {
//		File bundleFile = locator.bundleDataFile(bundle);
//		try {
//			save(bundle, bundleFile);
//		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
//			throw new JMOPRuntimeException("Could not save bundle", e);
//		}
//	}
//
//	@Override
//	public void savePlaylistDataToFile(File playlistFile, Playlist playlist, SaveReason reason) {
//		try {
//			save(playlist, playlistFile);
//		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
//			throw new JMOPRuntimeException("Could not save playlist", e);
//		}
//	}
//
//	@Override
//	public void saveTrackDataToFile(File trackFile, Track track, SaveReason reason) {
//		// we do nothing here, all the work is done by the saveBundleData
//	}
//
//	/////////////////////////////////////////////////////////////////////////////////////
//
//	private void save(Bundle bundle, File bundleFile) throws JMOPPersistenceException {
//		Set<Track> tracks = obtainBundleTracks(bundle);
//		manipulator.saveBundleData(bundle, tracks, bundleFile, tracksSource);
//	}
//
//	private void save(Playlist playlist, File playlistFile) throws JMOPPersistenceException {
//		manipulator.savePlaylistData(playlist, playlistFile, tracksSource);
//	}
//
//	/////////////////////////////////////////////////////////////////////////////////////

}
