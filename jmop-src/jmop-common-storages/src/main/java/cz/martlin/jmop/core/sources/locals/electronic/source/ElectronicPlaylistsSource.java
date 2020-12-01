package cz.martlin.jmop.core.sources.locals.electronic.source;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BasePlaylistsLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.locals.base.BasePlaylistFilesLoaderStorer;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFilesLocator;

public class ElectronicPlaylistsSource implements BasePlaylistsLocalSource {

	protected static final TrackFileLocation LOCATION_OF_PLAYLIST_FILES = TrackFileLocation.SAVE;
	private static final String DOT = ".";

	private final BaseFilesLocator locator;
	private final BaseFileSystemAccessor filesystem;
	private final BasePlaylistFilesLoaderStorer pfls;

	public ElectronicPlaylistsSource(BaseFilesLocator locator, BaseFileSystemAccessor filesystem,
			BasePlaylistFilesLoaderStorer pfls) {
		super();
		this.locator = locator;
		this.filesystem = filesystem;
		this.pfls = pfls;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public List<Playlist> loadPlaylists(Bundle bundle) throws JMOPSourceException {
		String bundleName = bundle.getName();
		TrackFileLocation location = LOCATION_OF_PLAYLIST_FILES;
		File directory = locator.getDirectoryOfBundle(bundleName, location);
		Predicate<File> matcher = (f) -> isPlaylistFile(f);

		Set<File> files = filesystem.listFilesMatching(directory, matcher);
		List<Playlist> playlists = files.stream() //
				.map((n) -> loadPlaylistOrHandleError(bundle, n)) //
				.filter((p) -> p != null) //
				.collect(Collectors.toList()); //

		playlists.sort((p1, p2) -> p1.compareTo(p2));

		return playlists;
	}

	private Playlist loadPlaylistOrHandleError(Bundle bundle, File file) {
		try {
			return loadPlaylist(bundle, file);
		} catch (JMOPSourceException e) {
			// TODO handler error
			e.printStackTrace();
			return null;
		}
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void createPlaylist(Playlist playlist) throws JMOPSourceException {
		savePlaylist(playlist);
	}

	@Override
	public void deletePlaylist(Playlist playlist) throws JMOPSourceException {
		File file = fileOfPlaylist(playlist);
		filesystem.deleteFile(file);
	}

	@Override
	public void saveUpdatedPlaylist(Playlist playlist) throws JMOPSourceException {
		savePlaylist(playlist);
	}
///////////////////////////////////////////////////////////////////////////

	private boolean isPlaylistFile(File file) {
		String name = file.getName();
		String extension = pfls.extensionOfFile();

		return name.endsWith(DOT + extension);
	}

	private Playlist loadPlaylist(Bundle bundle, File file) throws JMOPSourceException {
		return pfls.loadPlaylist(bundle, file);
	}

	private void savePlaylist(Playlist playlist) throws JMOPSourceException {
		Bundle bundle = playlist.getBundle();
		File file = fileOfPlaylist(playlist);
		pfls.savePlaylist(bundle, playlist, file);
	}

///////////////////////////////////////////////////////////////////////////

	private File fileOfPlaylist(Playlist playlist) {
		Bundle bundle = playlist.getBundle();
		TrackFileLocation location = LOCATION_OF_PLAYLIST_FILES;
		File directory = directoryOfBundle(bundle, location);
		String bundleDirName = directory.getName();

		String playlistName = playlist.getName();

		return locator.getFileOfPlaylist(bundleDirName, playlistName);
	}

	private File directoryOfBundle(Bundle bundle, TrackFileLocation location) {
		String bundleName = bundle.getName();
		return locator.getDirectoryOfBundle(bundleName, location);
	}
}
