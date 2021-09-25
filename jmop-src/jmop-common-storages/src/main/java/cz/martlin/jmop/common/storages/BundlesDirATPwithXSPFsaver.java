package cz.martlin.jmop.common.storages;

import java.io.File;

import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
//import cz.martlin.jmop.common.storages.locator.DefaultBundlesDirNamer;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
//import cz.martlin.jmop.common.storages.locators.AbstractLocator;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirNamer;
import cz.martlin.jmop.common.storages.locators.impls.AbstractCommonLocator;
//import cz.martlin.jmop.common.storages.locators.BaseFilesNamer;
//import cz.martlin.jmop.common.storages.locators.BundlesDirLocator;
//import cz.martlin.jmop.common.storages.locators.DefaultBundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.impls.AllTracksPlaylistBundleDataFileNamer;
import cz.martlin.jmop.common.storages.musicdatafile.BaseMusicdataFileManipulator;
import cz.martlin.jmop.common.storages.musicdatasaver.MusicdataSaverWithFiles;
import cz.martlin.jmop.common.storages.xspf.XSPFPlaylistFilesMusicdataManipulator;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The saver combining the bundles dir, all tracks playlist and XSPF.
 * 
 * @author martin
 * @deprecated do not
 */
@Deprecated
public class BundlesDirATPwithXSPFsaver extends MusicdataSaverWithFiles //
		implements BundlesDirStorageComponent, AllTracksPlaylistStorageComponent {

	public BundlesDirATPwithXSPFsaver(AbstractCommonLocator locator, BaseMusicdataFileManipulator manipulator,
			BaseInMemoryMusicbase inmemory) {
		super(locator, manipulator, inmemory);
	}
//
	
//
//	public static BundlesDirATPwithXSPFsaver create(boolean failsave, File root, BaseErrorReporter reporter,
//			TrackFileFormat tracksFileFormat, String allTracksPlaylistName) {
//
//		BaseMusicdataFileManipulator manipulator = prepareManipulator(failsave, reporter);
//
//		AbstractLocator locator = prepareLocator(root, tracksFileFormat, allTracksPlaylistName, manipulator);
//
//		return new BundlesDirATPwithXSPFsaver(locator, manipulator);
//	}
//
//	private static BundlesDirLocator prepareLocator(File root, TrackFileFormat tracksFileFormat,
//			String allTracksPlaylistName, BaseMusicdataFileManipulator manipulator) {
//		BaseBundlesDirLocator bdLocator = new DefaultBundlesDirLocator();
//		
//		String playlistFileExtension = manipulator.fileExtension();
//		BaseFilesNamer namer = new AllTracksPlaylistBundleDataFileNamer(tracksFileFormat, allTracksPlaylistName, playlistFileExtension);
//
//		BaseBundlesDirNamer bundlesDirNamer = new DefaultBundlesDirNamer();
//		BundlesDirLocator locator = new BundlesDirLocator(root, namer, bundlesDirNamer);
//		return locator;
//	}
//
//	private static XSPFPlaylistFilesMusicdataManipulator prepareManipulator(boolean failsave,
//			BaseErrorReporter reporter) {
//		
//		XSPFPlaylistFilesMusicdataManipulator manipulator;
//		if (failsave) {
//			manipulator = XSPFPlaylistFilesMusicdataManipulator.createFailsave(reporter);
//		} else {
//			manipulator = XSPFPlaylistFilesMusicdataManipulator.createWeak(reporter);
//		}
//		return manipulator;
//	}

}
