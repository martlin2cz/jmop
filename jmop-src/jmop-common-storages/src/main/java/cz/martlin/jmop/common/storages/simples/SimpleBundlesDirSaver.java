package cz.martlin.jmop.common.storages.simples;

import java.io.File;

import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
//import cz.martlin.jmop.common.storages.locator.DefaultBundlesDirNamer;
//import cz.martlin.jmop.common.storages.locators.BundlesDirLocator;
//import cz.martlin.jmop.common.storages.locators.SimpleBundlesDirFilesNamer;
import cz.martlin.jmop.common.storages.musicdatasaver.MusicdataSaverWithFiles;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * An saver of bundles dir storage, which just uses list of txt files as the
 * playlist files.
 * 
 * @author martin
 * @deprecated do not
 */
public class SimpleBundlesDirSaver extends MusicdataSaverWithFiles implements SimpleStorageComponent {

	public SimpleBundlesDirSaver(File root, TrackFileFormat tracksFormat, BaseFileSystemAccessor fs) {
//		super(//
//				new BundlesDirLocator(root, new SimpleBundlesDirFilesNamer(tracksFormat), new DefaultBundlesDirNamer()), //
//				new SimpleMusicdataFileManipulator(fs));
		super(null, null, null);
		
		throw new UnsupportedOperationException("do not");
	}
//
//	@Override
//	public void saveBundleDataToFile(File bundleDir, Bundle bundle, SaveReason reason) {
//		// ignore, since we don't store metadata
//	}
//
//	@Override
//	public void savePlaylistDataToFile(File playlistFile, Playlist playlist, SaveReason reason) {
//		Tracklist tracklist = playlist.getTracks();
//		saveTracklist(tracklist, playlistFile);
//	}
//
//	@Override
//	public void saveTrackDataToFile(File trackFile, Track track, SaveReason reason) {
//		// ignore, since we don't store metadata
//	}
//
//	/////////////////////////////////////////////////////////////////////////////////////
//
//	private void saveTracklist(Tracklist tracklist, File playlistFile) {
//		try {
//			fs.saveLines(playlistFile, //
//					tracklist.getTracks().stream() //
//							.map(t -> (t.getIdentifier() + "mp3")) //
//							.collect(Collectors.toList()) //
//			);
//		} catch (JMOPPersistenceException e) {
//			throw new JMOPRuntimeException("Could not save the tracklist", e);
//		}
//	}
}
