package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;
import java.io.InputStream;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.storages.bundlesdir.BaseMusicdataSaver.SaveReason;
import cz.martlin.jmop.common.storages.load.BaseMusicdataLoader;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.common.storages.utils.FilesLocatorExtension;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

public class BundlesDirsStorage implements BaseMusicbaseStorage {

	private final BaseFileSystemAccessor fs;
	private final FilesLocatorExtension locator;
	private final BaseMusicdataSaver saver;
	private final BaseMusicdataLoader loader;

	public BundlesDirsStorage(BaseFileSystemAccessor fs, BaseFilesLocator locator, //
			BaseMusicdataSaver saver, BaseMusicdataLoader loader) {
		super();
		this.fs = fs;
		this.locator = new FilesLocatorExtension(locator);
		this.saver = saver;
		this.loader = loader;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void load(BaseInMemoryMusicbase inmemory)  {
		loader.load(inmemory);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void createBundle(Bundle bundle)  {
		try {
			File bundleDir = locator.bundleDir(bundle);
			fs.createDirectory(bundleDir);
			saver.saveBundleData(bundleDir, bundle, SaveReason.CREATED);
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not create bundle", e); 
		}
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName)  {
		try {
			File oldBundleDir = locator.bundleDir(oldName);
			File newBundleDir = locator.bundleDir(newName);
	
			fs.renameDirectory(oldBundleDir, newBundleDir);
			saver.saveBundleData(newBundleDir, bundle, SaveReason.RENAMED);
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not rename bundle", e); 
		}
	}

	@Override
	public void removeBundle(Bundle bundle)  {
		try {
			File bundleDir = locator.bundleDir(bundle);
	
			fs.deleteDirectory(bundleDir);
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not remove bundle", e); 
		}
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle)  {
		try {
			File bundleDir = locator.bundleDir(bundle);
	
			saver.saveBundleData(bundleDir, bundle, SaveReason.UPDATED);
			
		} catch (JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not save the bundle", e); 
		}
	}

	@Override
	public void createPlaylist(Playlist playlist)  {
		try {
			File playlistFile = locator.playlistFile(playlist);
	
			saver.savePlaylistData(playlistFile, playlist, SaveReason.CREATED);
			
		} catch (JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not create the playlist", e); 
		}
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName)  {
		try {
			Bundle bundle = playlist.getBundle();
			File oldPlaylistFile = locator.playlistFile(bundle, oldName);
			File newPlaylistFile = locator.playlistFile(bundle, newName);
	
			saver.savePlaylistData(newPlaylistFile, playlist, SaveReason.RENAMED);
			fs.deleteFile(oldPlaylistFile);
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not rename playlist", e); 
		}
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle)  {
		try {
			File newPlaylistFile = locator.playlistFile(newBundle, playlist);
			File oldPlaylistFile = locator.playlistFile(oldBundle, playlist);
	
			saver.savePlaylistData(newPlaylistFile, playlist, SaveReason.MOVED);
			fs.deleteFile(oldPlaylistFile);
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not create bundle", e); 
		}
	}

	@Override
	public void removePlaylist(Playlist playlist)  {
		try {
			File oldPlaylistFile = locator.playlistFile(playlist);
	
			fs.deleteFile(oldPlaylistFile);
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not remove playlist", e); 
		}
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist)  {
		try {
			File playlistFile = locator.playlistFile(playlist);
	
			saver.savePlaylistData(playlistFile, playlist, SaveReason.UPDATED);
			
		} catch (JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not save the playlist", e); 
		}
	}

	@Override
	public void createTrack(Track track, InputStream trackFileContents)  {
		try {
			File trackFile = locator.trackFile(track);
	
			saver.saveTrackData(trackFile, track, SaveReason.CREATED);
			if (trackFileContents != null) { 
				fs.writeFile(trackFile, trackFileContents);
			}
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not create track", e); 
		}
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle)  {
		try {
			Bundle bundle = track.getBundle();
			File oldTrackFile = locator.trackFile(bundle, oldTitle);
			File newTrackFile = locator.trackFile(bundle, newTitle);
	
			if (oldTrackFile.exists()) {
				fs.moveFile(oldTrackFile, newTrackFile);
			}
			saver.saveTrackData(newTrackFile, track, SaveReason.RENAMED);
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not rename track", e); 
		}
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle)  {
		try {
			File oldTrackFile = locator.trackFile(oldBundle, track);
			File newTrackFile = locator.trackFile(newBundle, track);
	
			if (oldTrackFile.exists()) {
				fs.moveFile(oldTrackFile, newTrackFile);
			}
			saver.saveTrackData(newTrackFile, track, SaveReason.MOVED);
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not move track", e); 
		}
	}

	@Override
	public void removeTrack(Track track)  {
		try {
			File oldTrackFile = locator.trackFile(track);
			
			if (oldTrackFile.exists()) {
				fs.deleteFile(oldTrackFile);
			}
			
		} catch (JMOPPersistenceException | JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not remove track", e); 
		}
	}

	@Override
	public void saveUpdatedTrack(Track track)  {
		try {
			File trackFile = locator.trackFile(track);
	
			saver.saveTrackData(trackFile, track, SaveReason.UPDATED);
		
		} catch (JMOPRuntimeException e) {
			throw new JMOPRuntimeException("Could not save the track", e); 
		}
	}
	
	@Override
	public File trackFile(Track track) {
		File trackFile = locator.trackFile(track);
		return trackFile;
	}

}
