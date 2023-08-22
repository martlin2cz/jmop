package cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.impls;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.storage.musicdataloader.CommonMusicdataLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.BaseTracksLoader;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An tracks loader, loading the tracks from the bundle data file (by
 * help of {@link BaseMusicdataFileManipulator}).
 * 
 * It's component of {@link CommonMusicdataLoader}.
 * 
 * @author martin
 *
 */
public class BundleMusicfileTracksLoader implements BaseTracksLoader {

	private final BaseBundleDataLocator locator;
	private final BaseMusicdataFileManipulator manipulator;

	public BundleMusicfileTracksLoader(BaseBundleDataLocator locator, BaseMusicdataFileManipulator manipulator) {
		super();
		this.locator = locator;
		this.manipulator = manipulator;
	}

	@Override
	public Set<Track> loadTracks(Bundle bundle) throws JMOPPersistenceException {
		File file = locator.bundleDataFile(bundle);
		return manipulator.loadBundleTracks(file, bundle);
	}

}
