package cz.martlin.jmop.common.storages.bundlesdir;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.utils.BaseFilesLocator;
import cz.martlin.jmop.core.misc.JMOPSourceException;

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
	public List<String> loadBundlesNames() throws JMOPSourceException {
		return fs.listDirectories(locator.getRootDirectory()) //
				.filter(d -> hasAllPlaylistFile(d)) //
				.map(d -> locator.bundleName(d)) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Bundle loadBundleData(File bundleDir, String bundleName) throws JMOPSourceException {
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleName);
		return saver.loadOnlyBundle(allTracksPlaylistFile);
	}

	@Override
	public List<String> loadPlaylistsNames(File bundleDir, Bundle bundle, String bundleName) throws JMOPSourceException {
		return fs.listFiles(bundleDir) //
				.filter(f -> isPlaylistFile(f)) //
				.map(f -> locator.playlistName(f)) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Playlist loadPlaylistData(File playlistFile, Bundle bundle, Map<String, Track> tracks, String playlistName)
			throws JMOPSourceException {

		return saver.loadOnlyPlaylist(bundle, playlistFile);
	}

	@Override
	public List<String> loadTracksTitles(File bundleDir, Bundle bundle, String bundleName) throws JMOPSourceException {
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleName);
		return saver.loadOnlyPlaylist(bundle, allTracksPlaylistFile)
				//TODO little tricky
				.getTracks() //
				.getTracks() //
				.stream() //
				.map(Track::getTitle) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Track loadTrackData(File trackFile, Bundle bundle, String trackTitle) throws JMOPSourceException {
		String bundleName = bundle.getName();
		File allTracksPlaylistFile = allTracksPlaylistFile(bundleName);
		return saver.loadOnlyPlaylist(bundle, allTracksPlaylistFile) //
				//TODO little tricky
				.getTracks() //
				.getTracks() //
				.stream() //
				.filter(t -> t.getTitle() == trackTitle) //
				.findAny() //
				.get(); //
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private boolean isPlaylistFile(File file) {
		return file.exists(); // TODO check its type (extension) as well
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
