package cz.martlin.jmop.common.storages.loader.tracks.impls;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.loader.tracks.BaseTracksLoader;
import cz.martlin.jmop.common.storages.locators.BaseBundleDataLocator;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An {@link BaseTracksLoader} loading the tracks from the bundle data file (by
 * help of {@link BaseMusicdataFileManipulator}).
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
