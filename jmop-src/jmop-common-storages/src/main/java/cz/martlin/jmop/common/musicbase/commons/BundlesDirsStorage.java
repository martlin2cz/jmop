package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;

public class BundlesDirsStorage implements BaseMusicbaseStorage {

	private final BaseFileSystemAccessor fs;
	private final FilesLocatorExtension locator;
	private final BaseMusicdataSaver saver;

	public BundlesDirsStorage(BaseFileSystemAccessor fs, BaseFilesLocator locator, BaseMusicdataSaver saver) {
		super();
		this.fs = fs;
		this.locator = new FilesLocatorExtension(locator);
		this.saver = saver;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void load(BaseInMemoryMusicbase inmemory) throws JMOPSourceException {
		Map<String, Bundle> bundles = loadBundles(inmemory);

		for (String bundleName : bundles.keySet()) {
			Bundle bundle = bundles.get(bundleName);

			Map<String, Track> tracks = loadTracks(inmemory, bundleName, bundle);

			loadPlaylists(inmemory, bundleName, bundle, tracks);
		}
	}

	private void loadPlaylists(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle,
			Map<String, Track> tracks) throws JMOPSourceException {
		
		File bundleDir = locator.bundleDir(bundleName);
		for (String playlistName : saver.loadPlaylistsNames(bundleDir, bundleName)) {
			File playlistFile = locator.playlistFile(bundleName, playlistName);
			
			Playlist playlistData = saver.loadPlaylistData(playlistFile, bundle, tracks, playlistName);

			inmemory.createPlaylist(playlistData);
		}
	}

	private Map<String, Track> loadTracks(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle)
			throws JMOPSourceException {

		Map<String, Track> tracks = new HashMap<>();
		
		File bundleDir = locator.bundleDir(bundleName);
		for (String trackTitle : saver.loadTracksTitles(bundleDir, bundleName)) {
			File trackFile = locator.trackFile(bundleName, trackTitle);
			Track trackData = saver.loadTrackData(trackFile, bundle, trackTitle);

			Track track = inmemory.createTrack(trackData);
			tracks.put(trackTitle, track);
		}

		return tracks;
	}

	private Map<String, Bundle> loadBundles(BaseInMemoryMusicbase inmemory) throws JMOPSourceException {
		Map<String, Bundle> bundles = new HashMap<>();

		for (String bundleName : saver.loadBundlesNames()) {
			File bundleDir = locator.bundleDir(bundleName);
			Bundle bundleData = saver.loadBundleData(bundleDir, bundleName);

			Bundle bundle = inmemory.createBundle(bundleData);
			bundles.put(bundleName, bundle);
		}

		return bundles;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void createBundle(Bundle bundle) throws JMOPSourceException {
		File bundleDir = locator.bundleDir(bundle);
		
		fs.createDirectory(bundleDir);
		saver.saveBundleData(bundleDir, bundle);
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPSourceException {
		File oldBundleDir = locator.bundleDir(oldName);
		File newBundleDir = locator.bundleDir(newName);
		
		fs.renameDirectory(oldBundleDir, newBundleDir);
		saver.saveBundleData(newBundleDir, bundle);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPSourceException {
		File bundleDir = locator.bundleDir(bundle);
		
		fs.deleteDirectory(bundleDir);
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle) throws JMOPSourceException {
		File bundleDir = locator.bundleDir(bundle);
		
		saver.saveBundleData(bundleDir, bundle);
	}

	@Override
	public void createPlaylist(Playlist playlist) throws JMOPSourceException {
		File playlistFile = locator.playlistFile(playlist);
		
		saver.savePlaylistData(playlistFile, playlist);
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPSourceException {
		Bundle bundle = playlist.getBundle();
		File oldPlaylistFile = locator.playlistFile(bundle, oldName);
		File newPlaylistFile = locator.playlistFile(bundle, newName);
		
		saver.savePlaylistData(newPlaylistFile, playlist);
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		File newPlaylistFile = locator.playlistFile(newBundle, playlist);
		File oldPlaylistFile = locator.playlistFile(oldBundle, playlist);
		
		saver.savePlaylistData(newPlaylistFile, playlist);
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPSourceException {
		File oldPlaylistFile = locator.playlistFile(playlist);
		
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) throws JMOPSourceException {
		File playlistFile = locator.playlistFile(playlist);
		
		saver.savePlaylistData(playlistFile, playlist);
	}

	@Override
	public void createTrack(Track track) throws JMOPSourceException {
		File trackFile = locator.trackFile(track);
		
		saver.saveTrackData(trackFile, track);
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		File oldTrackFile = locator.trackFile(bundle, oldTitle);
		File newTrackFile = locator.trackFile(bundle, newTitle);

		fs.moveFile(oldTrackFile, newTrackFile);
		saver.saveTrackData(newTrackFile, track);
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		File oldTrackFile = locator.trackFile(oldBundle, track);
		File newTrackFile = locator.trackFile(newBundle, track);
		
		fs.moveFile(oldTrackFile, newTrackFile);
		saver.saveTrackData(newTrackFile, track);
	}

	@Override
	public void removeTrack(Track track) throws JMOPSourceException {
		File oldTrackFile = locator.trackFile(track);
		fs.deleteFile(oldTrackFile);
	}

	@Override
	public void saveUpdatedTrack(Track track) throws JMOPSourceException {
		File trackFile = locator.trackFile(track);
		
		saver.saveTrackData(trackFile, track);
	}

}
