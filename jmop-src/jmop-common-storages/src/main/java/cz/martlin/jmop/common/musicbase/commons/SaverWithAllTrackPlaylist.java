package cz.martlin.jmop.common.musicbase.commons;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;

public class SaverWithAllTrackPlaylist implements BaseMusicdataSaver {

	private final String allTracksPlaylistName;
	private final BaseInMemoryMusicbase musicbase;
	private final BaseExtendedPlaylistSaver saver;
	private File root;
	private BaseFileSystemAccessor fs;
	private FilesLocatorExtension locator;

	public SaverWithAllTrackPlaylist(String allTracksPlaylistName, BaseInMemoryMusicbase musicbase,
			BaseExtendedPlaylistSaver saver) {
		super();
		this.allTracksPlaylistName = allTracksPlaylistName;
		this.musicbase = musicbase;
		this.saver = saver;
	}

///////////////////////////////////////////////////////////////////////////////

	@Override
	public void saveBundleData(File bundleDir, Bundle bundle) throws JMOPSourceException {
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);
		File allTracksPlaylistFile = locator.playlistFile(allTracksPlaylist);
		save(allTracksPlaylist, allTracksPlaylistFile);
	}

	@Override
	public void savePlaylistData(File playlistFile, Playlist playlist) throws JMOPSourceException {
		save(playlist, playlistFile);
	}

	@Override
	public void saveTrackData(File trackFile, Track track) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);
		File allTracksPlaylistFile = locator.playlistFile(allTracksPlaylist);
		save(allTracksPlaylist, allTracksPlaylistFile);
	}

	@Override
	public List<String> loadBundlesNames() throws JMOPSourceException {
		return fs.listDirectories(root) //
				.filter(d -> hasAllPlaylistFile(d)) //
				.map(d -> locator.bundleName(d)) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Bundle loadBundleData(File bundleDir, String bundleName) throws JMOPSourceException {
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleDir);
		return saver.loadBundleDataFromAllTracksPlaylist(allTracksPlaylistFile);
	}

	@Override
	public List<String> loadPlaylistsNames(File bundleDir, String bundleName) throws JMOPSourceException {
		return fs.listFiles(bundleDir) //
				.filter(f -> isPlaylistFile(f)) //
				.map(f -> locator.playlistName(f)) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Playlist loadPlaylistData(File playlistFile, Bundle bundle, Map<String, Track> tracks, String playlistName)
			throws JMOPSourceException {
		
		return saver.loadPlaylistDataFromPlaylistFile(bundle, playlistFile);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean isPlaylistFile(File file) {
		return file.exists(); // TODO check its type (extension) as well
	}

	private File allTracksPlaylistFile(File bundleDir) {
		String bundleName = locator.bundleName(bundleDir);
		return locator.playlistFile(bundleName, allTracksPlaylistName);
	}

	private boolean hasAllPlaylistFile(File bundleDir) {
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleDir);
		return allTracksPlaylistFile.exists();
	}

	private boolean isAllTracksPlaylist(Playlist playlist) {
		return playlist.getName().equals(allTracksPlaylistName);
	}

	private Playlist obtainAllTracksPlaylist(Bundle bundle) throws JMOPSourceException {
		return musicbase.playlists(bundle).stream() //
				.filter(p -> isAllTracksPlaylist(p)) //
				.findAny().get(); //
	}

	private void save(Playlist playlist, File playlistFile) throws JMOPSourceException {
		if (isAllTracksPlaylist(playlist)) {
			saver.savePlaylistWithBundle(playlist, playlistFile);
		} else {
			saver.saveOnlyPlaylist(playlist, playlistFile);
		}
	}

}
