package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class LoaderWithAllTrackPlaylist implements BaseMusicdataLoader {

	private final String allTracksPlaylistName;
	private final BaseFileSystemAccessor fs;
	private final BaseFilesLocator locator;
	private final BaseExtendedPlaylistManipulator saver;
	

	public LoaderWithAllTrackPlaylist(String allTracksPlaylistName, BaseFileSystemAccessor fs,
			BaseFilesLocator locator, BaseExtendedPlaylistManipulator saver) {
		super();
		this.allTracksPlaylistName = allTracksPlaylistName;
		this.fs = fs;
		this.locator = locator;
		this.saver = saver;
	}

	@Override
	public List<String> loadBundlesNames()  {
		try {
			return fs.listDirectories(locator.getRootDirectory()) //
					.filter(d -> hasAllPlaylistFile(d)) //
					.map(d -> locator.bundleName(d)) //
					.collect(Collectors.toList());
			
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			//FIXME make it throwable
			throw new JMOPRuntimeException("", e);
		}
	}

	@Override
	public Bundle loadBundle(File bundleDir, String bundleName)  {
		try {
			File allTracksPlaylistFile = allTracksPlaylistFile(bundleName);
			return saver.loadOnlyBundle(allTracksPlaylistFile);
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			//FIXME make it throwable
			throw new JMOPRuntimeException("", e);
		}
	}

	@Override
	public List<String> loadPlaylistsNames(File bundleDir, Bundle bundle, String bundleName)  {
		try {
			return fs.listFiles(bundleDir) //
					.filter(f -> isPlaylistFile(f)) //
					.map(f -> locator.playlistName(f)) //
					.collect(Collectors.toList()); //
			
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			//FIXME make it throwable
			throw new JMOPRuntimeException("", e);
		}
	}

	@Override
	public Playlist loadPlaylist(File playlistFile, Bundle bundle, Map<String, Track> tracks, String playlistName)
			 {
		try {
			return saver.loadOnlyPlaylist(bundle, tracks, playlistFile);
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			//FIXME make it throwable
			throw new JMOPRuntimeException("", e);
		}
	}

	@Override
	public List<String> loadTracksTitles(File bundleDir, Bundle bundle, String bundleName)  {
		try {
			File allTracksPlaylistFile = allTracksPlaylistFile(bundleName);
			return saver.loadOnlyPlaylist(bundle, null, allTracksPlaylistFile)
					//TODO little tricky
					.getTracks() //
					.getTracks() //
					.stream() //
					.map(Track::getTitle) //
					.collect(Collectors.toList()); //
			
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			//FIXME make it throwable
			throw new JMOPRuntimeException("", e);
		}
	}

	@Override
	public Track loadTrack(File trackFile, Bundle bundle, String trackTitle)  {
		try {
			String bundleName = bundle.getName();
			File allTracksPlaylistFile = allTracksPlaylistFile(bundleName);
			return saver.loadOnlyPlaylist(bundle, null, allTracksPlaylistFile) //
					//TODO little tricky
					.getTracks() //
					.getTracks() //
					.stream() //
					.filter(t -> t.getTitle().equals(trackTitle)) //
					.findAny() //
					.get(); //
			
		} catch (JMOPRuntimeException | JMOPPersistenceException e) {
			//FIXME make it throwable
			throw new JMOPRuntimeException("", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean isPlaylistFile(File file) {
		String extension = saver.fileExtension();
		return file.exists() && file.getName().endsWith(extension); //TODO still not the best solution in the world
	}

//	private boolean isTrackFile(File file) {
//		return file.exists(); // TODO check its type (= extension)
//	}

	private boolean hasAllPlaylistFile(File bundleDir) {
		String bundleName = locator.bundleName(bundleDir);
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleName);
		return allTracksPlaylistFile.exists();
	}

	private File allTracksPlaylistFile(String bundleName) {
		return locator.playlistFile(bundleName, allTracksPlaylistName);
	}

}
