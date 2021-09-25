package cz.martlin.jmop.common.storages;

import java.io.File;

import cz.martlin.jmop.common.storages.loader.CommonMusicdataLoader;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.loader.bundles.BaseBundlesLoader;
import cz.martlin.jmop.common.storages.loader.bundles.ByIdentifierBundlesLoader;
import cz.martlin.jmop.common.storages.loader.playlists.BasePlaylistsLoader;
import cz.martlin.jmop.common.storages.loader.tracks.BaseTracksLoader;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirNamer;
//import cz.martlin.jmop.common.storages.locators.BaseFilesNamer;
//import cz.martlin.jmop.common.storages.locators.BundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.impls.AllTracksPlaylistBundleDataFileNamer;
import cz.martlin.jmop.common.storages.locators.impls.DefaultBundlesDirNamer;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.xspf.XSPFPlaylistFilesMusicdataManipulator;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The loader combining the bundles dir, all tracks playlist and XSPF.
 * 
 * @author martin
 * @deprecated do not use
 */
@Deprecated
public class BundlesDirATPwithXSPFloader extends CommonMusicdataLoader //
		implements BundlesDirStorageComponent, AllTracksPlaylistStorageComponent {


	private BundlesDirATPwithXSPFloader(BaseBundlesLoader bundlesLoader, BasePlaylistsLoader playlistsLoader,
			BaseTracksLoader tracksLoader) {
		super(bundlesLoader, playlistsLoader, tracksLoader);
	}

	public static BundlesDirATPwithXSPFloader create(boolean failsave, File root, BaseErrorReporter reporter,
			TrackFileFormat tracksFileFormat, String allTracksPlaylistName) {

		BaseMusicdataFileManipulator manipulator = prepareManipulator(failsave, reporter);


		BaseTracksLoader tracksLoader = prepareTracksLoader();
		BasePlaylistsLoader playlistLoader = preparePlaylistsLoader();
		BaseBundlesLoader bundlesLoader = prepareBundlesLoader();
		return new BundlesDirATPwithXSPFloader(bundlesLoader, playlistLoader, tracksLoader);
	}

	private static BaseTracksLoader prepareTracksLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	private static BasePlaylistsLoader preparePlaylistsLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	private static <IT> ByIdentifierBundlesLoader<IT> prepareBundlesLoader() {
		BaseBundlesByIdentifierLoader<IT> loader = null;
		return new ByIdentifierBundlesLoader<>(null, null);
	}

	private static BaseBundlesDirLocator prepareLocator(File root, TrackFileFormat tracksFileFormat,
			String allTracksPlaylistName, BaseMusicdataFileManipulator manipulator) {

		throw new UnsupportedOperationException("do not");
		
//		String playlistFileExtension = manipulator.fileExtension();
//		BaseFilesNamer namer = new AllTracksPlaylistBundleDataFileNamer(tracksFileFormat, allTracksPlaylistName, playlistFileExtension);
//
//		BaseBundlesDirNamer bundlesDirNamer = new DefaultBundlesDirNamer();
//		BundlesDirLocator locator = new BundlesDirLocator(root, namer, bundlesDirNamer);
//		return locator;
	}

	private static XSPFPlaylistFilesMusicdataManipulator prepareManipulator(boolean failsave,
			BaseErrorReporter reporter) {
		
		XSPFPlaylistFilesMusicdataManipulator manipulator;
		if (failsave) {
			manipulator = XSPFPlaylistFilesMusicdataManipulator.createFailsave(reporter);
		} else {
			manipulator = XSPFPlaylistFilesMusicdataManipulator.createWeak(reporter);
		}
		return manipulator;
	}

}
