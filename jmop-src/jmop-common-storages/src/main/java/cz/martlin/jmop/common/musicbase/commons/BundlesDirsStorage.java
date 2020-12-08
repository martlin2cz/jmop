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
	private final BaseFilesLocator locator;
	private final BaseMusicdataSaver saver;

	public BundlesDirsStorage(BaseFileSystemAccessor fs, BaseFilesLocator locator, BaseMusicdataSaver saver) {
		super();
		this.fs = fs;
		this.locator = locator;
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
		
		for (String playlistName : saver.loadPlaylistsNames(bundleName)) {
			Playlist playlistData = saver.loadPlaylistData(bundle, tracks, playlistName);

			inmemory.createPlaylist(playlistData);
		}
	}

	private Map<String, Track> loadTracks(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle)
			throws JMOPSourceException {

		Map<String, Track> tracks = new HashMap<>();

		for (String trackTitle : saver.loadTracksTitles(bundleName)) {
			Track trackData = saver.loadTrackData(bundle, trackTitle);

			Track track = inmemory.createTrack(trackData);
			tracks.put(trackTitle, track);
		}

		return tracks;
	}

	private Map<String, Bundle> loadBundles(BaseInMemoryMusicbase inmemory) throws JMOPSourceException {
		Map<String, Bundle> bundles = new HashMap<>();

		for (String bundleName : saver.loadBundlesNames()) {
			Bundle bundleData = saver.loadBundleData(bundleName);

			Bundle bundle = inmemory.createBundle(bundleData);

			bundles.put(bundleName, bundle);
		}

		return bundles;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void createBundle(Bundle bundle) throws JMOPSourceException {
		File bundleDir = locator.bundleDir(bundle.getName());
		fs.createDirectory(bundleDir);
		saver.saveBundleData(bundle);
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPSourceException {
		File oldBundleDir = locator.bundleDir(oldName);
		File newBundleDir = locator.bundleDir(newName);
		fs.renameDirectory(oldBundleDir, newBundleDir);
		saver.saveBundleData(bundle);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPSourceException {
		File bundleDir = locator.bundleDir(bundle.getName());
		fs.deleteDirectory(bundleDir);
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle) throws JMOPSourceException {
		saver.saveBundleData(bundle);
	}

	@Override
	public void createPlaylist(Playlist playlist) throws JMOPSourceException {
		saver.savePlaylistData(playlist);
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPSourceException {
		saver.savePlaylistData(playlist);

		Bundle bundle = playlist.getBundle();
		File oldPlaylistFile = locator.playlistFile(bundle.getName(), oldName);
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		saver.savePlaylistData(playlist);

		File oldPlaylistFile = locator.playlistFile(oldBundle.getName(), playlist.getName());
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPSourceException {
		Bundle bundle = playlist.getBundle();
		File oldPlaylistFile = locator.playlistFile(bundle.getName(), playlist.getName());
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) throws JMOPSourceException {
		saver.savePlaylistData(playlist);
	}

	@Override
	public void createTrack(Track track) throws JMOPSourceException {
		saver.saveTrackData(track);
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPSourceException {
		saver.saveTrackData(track);

		Bundle bundle = track.getBundle();
		File oldTrackFile = locator.trackFile(bundle.getName(), oldTitle);
		File newTrackFile = locator.trackFile(bundle.getName(), newTitle);
		fs.moveFile(oldTrackFile, newTrackFile);
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPSourceException {
		saver.saveTrackData(track);

		File oldTrackFile = locator.trackFile(oldBundle.getName(), track.getTitle());
		File newTrackFile = locator.trackFile(newBundle.getName(), track.getTitle());
		fs.moveFile(oldTrackFile, newTrackFile);
	}

	@Override
	public void removeTrack(Track track) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		File oldTrackFile = locator.trackFile(bundle.getName(), track.getTitle());
		fs.deleteFile(oldTrackFile);
	}

	@Override
	public void saveUpdatedTrack(Track track) throws JMOPSourceException {
		saver.saveTrackData(track);
	}

}
