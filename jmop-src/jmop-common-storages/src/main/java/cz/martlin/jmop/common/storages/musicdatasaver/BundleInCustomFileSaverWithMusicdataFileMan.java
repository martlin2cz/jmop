package cz.martlin.jmop.common.storages.musicdatasaver;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;

/**
 * The saver of musicdata, which uses the {@link BaseMusicdataFileManipulator}.
 * 
 * The bundle data gets saved into the file of specified name.
 * 
 * @author martin
 * @deprecated do not
 */
@Deprecated
public class BundleInCustomFileSaverWithMusicdataFileMan /* extends AbstractSaverWithMusicdataFileMan*/ {

	private final BaseInMemoryMusicbase musicbase;
	private final String bundleFileName;

	public BundleInCustomFileSaverWithMusicdataFileMan(BaseBundlesDirLocator locator,
			BaseMusicdataFileManipulator manipulator, BaseInMemoryMusicbase musicbase, String bundleFileName) {
//		super(locator, manipulator);
		this.musicbase = musicbase;
		this.bundleFileName = bundleFileName;
	}

/////////////////////////////////////////////////////////////////////////////////////

//	@Override
//	protected Set<Track> obtainBundleTracks(Bundle bundle) {
//		return musicbase.tracks(bundle);
//	}
//
//	protected File obtainBundleFile(File bundleDir, Bundle bundle) {
//		String fileName = bundleFileName + "." + manipulator.fileExtension();
//		return new File(bundleDir, fileName);
//	}

/////////////////////////////////////////////////////////////////////////////////////

}
