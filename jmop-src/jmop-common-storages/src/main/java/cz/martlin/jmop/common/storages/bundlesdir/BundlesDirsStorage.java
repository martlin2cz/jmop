package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.storages.bundlesdir.BaseMusicdataSaver.SaveReason;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.common.storages.utils.FilesLocatorExtension;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

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
	public void load(BaseInMemoryMusicbase inmemory) throws JMOPMusicbaseException {
		Map<String, Bundle> bundles = loadBundles(inmemory);

		for (String bundleName : bundles.keySet()) {
			Bundle bundle = bundles.get(bundleName);

			Map<String, Track> tracks = loadTracks(inmemory, bundleName, bundle);

			loadPlaylists(inmemory, bundleName, bundle, tracks);
		}
	}

	private void loadPlaylists(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle,
			Map<String, Track> tracks) throws JMOPMusicbaseException {

		File bundleDir = locator.bundleDir(bundleName);
		for (String playlistName : loader.loadPlaylistsNames(bundleDir, bundle, bundleName)) {
			File playlistFile = locator.playlistFile(bundleName, playlistName);

			Playlist playlist = loader.loadPlaylist(playlistFile, bundle, tracks, playlistName);

			inmemory.addPlaylist(playlist);
		}
	}

	private Map<String, Track> loadTracks(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle)
			throws JMOPMusicbaseException {

		Map<String, Track> tracks = new HashMap<>();

		File bundleDir = locator.bundleDir(bundleName);
		for (String trackTitle : loader.loadTracksTitles(bundleDir, bundle, bundleName)) {
			File trackFile = locator.trackFile(bundleName, trackTitle);
			Track track = loader.loadTrack(trackFile, bundle, trackTitle);

			inmemory.addTrack(track);
			tracks.put(trackTitle, track);
		}

		return tracks;
	}

	private Map<String, Bundle> loadBundles(BaseInMemoryMusicbase inmemory) throws JMOPMusicbaseException {
		Map<String, Bundle> bundles = new HashMap<>();

		for (String bundleName : loader.loadBundlesNames()) {
			File bundleDir = locator.bundleDir(bundleName);
			Bundle bundle = loader.loadBundle(bundleDir, bundleName);

			inmemory.addBundle(bundle);
			bundles.put(bundleName, bundle);
		}

		return bundles;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void createBundle(Bundle bundle) throws JMOPMusicbaseException {
		File bundleDir = locator.bundleDir(bundle);

		fs.createDirectory(bundleDir);
		saver.saveBundleData(bundleDir, bundle, SaveReason.CREATED);
	}

	@Override
	public void renameBundle(Bundle bundle, String oldName, String newName) throws JMOPMusicbaseException {
		File oldBundleDir = locator.bundleDir(oldName);
		File newBundleDir = locator.bundleDir(newName);

		fs.renameDirectory(oldBundleDir, newBundleDir);
		saver.saveBundleData(newBundleDir, bundle, SaveReason.RENAMED);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPMusicbaseException {
		File bundleDir = locator.bundleDir(bundle);

		fs.deleteDirectory(bundleDir);
	}

	@Override
	public void saveUpdatedBundle(Bundle bundle) throws JMOPMusicbaseException {
		File bundleDir = locator.bundleDir(bundle);

		saver.saveBundleData(bundleDir, bundle, SaveReason.UPDATED);
	}

	@Override
	public void createPlaylist(Playlist playlist) throws JMOPMusicbaseException {
		File playlistFile = locator.playlistFile(playlist);

		saver.savePlaylistData(playlistFile, playlist, SaveReason.CREATED);
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPMusicbaseException {
		Bundle bundle = playlist.getBundle();
		File oldPlaylistFile = locator.playlistFile(bundle, oldName);
		File newPlaylistFile = locator.playlistFile(bundle, newName);

		saver.savePlaylistData(newPlaylistFile, playlist, SaveReason.RENAMED);
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPMusicbaseException {
		File newPlaylistFile = locator.playlistFile(newBundle, playlist);
		File oldPlaylistFile = locator.playlistFile(oldBundle, playlist);

		saver.savePlaylistData(newPlaylistFile, playlist, SaveReason.MOVED);
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPMusicbaseException {
		File oldPlaylistFile = locator.playlistFile(playlist);

		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) throws JMOPMusicbaseException {
		File playlistFile = locator.playlistFile(playlist);

		saver.savePlaylistData(playlistFile, playlist, SaveReason.UPDATED);
	}

	@Override
	public void createTrack(Track track, InputStream trackFileContents) throws JMOPMusicbaseException {
		File trackFile = locator.trackFile(track);

		saver.saveTrackData(trackFile, track, SaveReason.CREATED);
		fs.writeFile(trackFile, trackFileContents);
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPMusicbaseException {
		Bundle bundle = track.getBundle();
		File oldTrackFile = locator.trackFile(bundle, oldTitle);
		File newTrackFile = locator.trackFile(bundle, newTitle);

		if (oldTrackFile.exists()) {
			fs.moveFile(oldTrackFile, newTrackFile);
		}
		saver.saveTrackData(newTrackFile, track, SaveReason.RENAMED);
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPMusicbaseException {
		File oldTrackFile = locator.trackFile(oldBundle, track);
		File newTrackFile = locator.trackFile(newBundle, track);

		if (oldTrackFile.exists()) {
			fs.moveFile(oldTrackFile, newTrackFile);
		}
		saver.saveTrackData(newTrackFile, track, SaveReason.MOVED);
	}

	@Override
	public void removeTrack(Track track) throws JMOPMusicbaseException {
		File oldTrackFile = locator.trackFile(track);
		
		if (oldTrackFile.exists()) {
			fs.deleteFile(oldTrackFile);
		}
	}

	@Override
	public void saveUpdatedTrack(Track track) throws JMOPMusicbaseException {
		File trackFile = locator.trackFile(track);

		saver.saveTrackData(trackFile, track, SaveReason.UPDATED);
	}
	
	@Override
	public File trackFile(Track track) {
		File trackFile = locator.trackFile(track);
		return trackFile;
	}

}
