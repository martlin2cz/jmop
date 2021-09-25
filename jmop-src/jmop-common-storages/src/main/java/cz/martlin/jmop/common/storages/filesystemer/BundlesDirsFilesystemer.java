package cz.martlin.jmop.common.storages.filesystemer;

import java.io.File;
import java.io.InputStream;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.BundlesDirStorageComponent;
import cz.martlin.jmop.common.storages.fs.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.locators.BasePlaylistLocator;
import cz.martlin.jmop.common.storages.locators.BaseTrackFileLocator;
import cz.martlin.jmop.common.storages.locators.impls.AbstractCommonLocator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * The filesystemer, which assumes each bundle has its own directory.
 * 
 * Important: The locators may correspond to that.
 * 
 * @author martin
 *
 */
public class BundlesDirsFilesystemer implements BaseMusicbaseFilesystemer, BundlesDirStorageComponent {

	private final BaseFileSystemAccessor fs;
	private final BaseBundlesDirLocator bundlesDirLocator;
	private final BasePlaylistLocator playlistsLocator;
	private final BaseTrackFileLocator tracksLocator;

	public BundlesDirsFilesystemer(BaseFileSystemAccessor fs, BaseBundlesDirLocator bundlesDirLocator,
			AbstractCommonLocator locator) {
		super();
		this.fs = fs;
		this.bundlesDirLocator = bundlesDirLocator;
		this.playlistsLocator = locator;
		this.tracksLocator = locator;
	}

	public BundlesDirsFilesystemer(BaseFileSystemAccessor fs, BaseBundlesDirLocator bundlesDirLocator,
			BasePlaylistLocator playlistsLocator, BaseTrackFileLocator tracksLocator) {
		super();
		this.fs = fs;
		this.bundlesDirLocator = bundlesDirLocator;
		this.playlistsLocator = playlistsLocator;
		this.tracksLocator = tracksLocator;
	}

	@Override
	public void createBundle(Bundle bundle) throws JMOPPersistenceException {
		File bundleDir = bundlesDirLocator.bundleDir(bundle);
		fs.createDirectory(bundleDir);
	}

	@Override
	public void renameBundle(String oldName, String newName) throws JMOPPersistenceException {
		File oldBundleDir = bundlesDirLocator.bundleDir(oldName);
		File newBundleDir = bundlesDirLocator.bundleDir(newName);

		fs.renameDirectory(oldBundleDir, newBundleDir);
	}

	@Override
	public void removeBundle(Bundle bundle) throws JMOPPersistenceException {
		File bundleDir = bundlesDirLocator.bundleDir(bundle);

		fs.deleteDirectory(bundleDir);
	}

	@Override
	public void renamePlaylist(Playlist playlist, String oldName, String newName) throws JMOPPersistenceException {
		Bundle bundle = playlist.getBundle();
		File oldPlaylistFile = playlistsLocator.playlistFile(bundle, oldName);
		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle oldBundle, Bundle newBundle) throws JMOPPersistenceException {
		File oldPlaylistFile = playlistsLocator.playlistFile(oldBundle, playlist);

		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void removePlaylist(Playlist playlist) throws JMOPPersistenceException {
		File oldPlaylistFile = playlistsLocator.playlistFile(playlist);

		fs.deleteFile(oldPlaylistFile);
	}

	@Override
	public void createTrack(Track track, InputStream trackFileContents) throws JMOPPersistenceException {
		File trackFile = tracksLocator.trackFile(track);
		if (trackFileContents != null) {
			fs.writeFile(trackFile, trackFileContents);
		}
	}

	@Override
	public void renameTrack(Track track, String oldTitle, String newTitle) throws JMOPPersistenceException {
		Bundle bundle = track.getBundle();
		File oldTrackFile = tracksLocator.trackFile(bundle, oldTitle);
		File newTrackFile = tracksLocator.trackFile(bundle, newTitle);

		if (oldTrackFile.exists()) {
			fs.moveFile(oldTrackFile, newTrackFile);
		}
	}

	@Override
	public void moveTrack(Track track, Bundle oldBundle, Bundle newBundle) throws JMOPPersistenceException {
		File oldTrackFile = tracksLocator.trackFile(oldBundle, track);
		File newTrackFile = tracksLocator.trackFile(newBundle, track);

		if (oldTrackFile.exists()) {
			fs.moveFile(oldTrackFile, newTrackFile);
		}
	}

	@Override
	public void removeTrack(Track track) throws JMOPPersistenceException {
		File oldTrackFile = tracksLocator.trackFile(track);

		if (oldTrackFile.exists()) {
			fs.deleteFile(oldTrackFile);
		}
	}

}