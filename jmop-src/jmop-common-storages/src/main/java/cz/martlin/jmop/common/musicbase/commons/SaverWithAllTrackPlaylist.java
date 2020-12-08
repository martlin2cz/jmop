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
	private BaseFilesLocator locator;
	
	
	
	public SaverWithAllTrackPlaylist(String allTracksPlaylistName, BaseInMemoryMusicbase musicbase,
			BaseExtendedPlaylistSaver saver) {
		super();
		this.allTracksPlaylistName = allTracksPlaylistName;
		this.musicbase = musicbase;
		this.saver = saver;
	}


	@Override
	public void saveBundleData(Bundle bundle) throws JMOPSourceException {
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);
		saver.savePlaylist(allTracksPlaylist);
	}


	@Override
	public void savePlaylistData(Playlist playlist) throws JMOPSourceException {
		saver.savePlaylist(playlist);
	}

	@Override
	public void saveTrackData(Track track) throws JMOPSourceException {
		Bundle bundle = track.getBundle();
		Playlist allTracksPlaylist = obtainAllTracksPlaylist(bundle);
		saver.savePlaylist(allTracksPlaylist);
	}

	@Override
	public List<String> loadBundlesNames() throws JMOPSourceException {
		return fs.listDirectoriesMatching(root, d -> hasAllPlaylistFile(d)) //
				.stream() //
				.map(d -> allTracksPlaylistFile(d)) //
				.map(f -> saver.loadBundleNameFromAllTracksPlaylist(f))
				.collect(Collectors.toList());
	}

	private File allTracksPlaylistFile(File bundleDir) {
		//FIXME to obtain all tracks playlist,
		// we need to know the bundle name
		// which we can get only by the all tracks playlist #fail
		String bundleName = FAIL;  
		return locator.playlistFile(bundleName , allTracksPlaylistName);
	}


	private boolean hasAllPlaylistFile(File bundleDir) {
		return allTracksPlaylistFile(bundleDir).exists();
	}


	@Override
	public Bundle loadBundleData(String bundleName) throws JMOPSourceException {
		File bundleDir = locator.bundleDir(bundleName);
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleDir);
		return saver.loadBundleDataFromAllTracksPlaylist(allTracksPlaylistFile);
	}

	@Override
	public List<String> loadPlaylistsNames(String bundleName) throws JMOPSourceException {
		File bundleDir = locator.bundleDir(bundleName);
		return fs.listFilesMatching(bundleDir, f -> isPlaylistFile(f)) //
				.stream() //
				.map(f -> saver.loadPlaylistNameFromPlaylistFile(f)) //
				.collect(Collectors.toList());
	}

	private boolean isPlaylistFile(File file) {
		return file.exists(); //TODO check its type as well
	}


	@Override
	public Playlist loadPlaylistData(Bundle bundle, Map<String, Track> tracks, String playlistName)
			throws JMOPSourceException {
		String bundleName = bundle.getName();
		File playlistFile = locator.playlistFile(bundleName , playlistName);
		return saver.loadPlaylistDataFromPlaylistFile(playlistFile);
	}

	@Override
	public List<String> loadTracksTitles(String bundleName) throws JMOPSourceException {
		File bundleDir = locator.bundleDir(bundleName);
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleDir);
		return saver.loadTrackTitlesFromAllTracksPlaylist(allTracksPlaylistFile);
	}

	@Override
	public Track loadTrackData(Bundle bundle, String trackTitle) throws JMOPSourceException {
		String bundleName = bundle.getName();
		File bundleDir = locator.bundleDir(bundleName);
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleDir);
		return saver.loadTrackDataFromAllTracksPlaylist(allTracksPlaylistFile, trackTitle);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	

	private Playlist obtainAllTracksPlaylist(Bundle bundle) throws JMOPSourceException {
		return musicbase.playlists(bundle).stream() //
				.filter(p -> p.getName().equals(allTracksPlaylistName)) //
				.findAny().get(); //
	}

}
