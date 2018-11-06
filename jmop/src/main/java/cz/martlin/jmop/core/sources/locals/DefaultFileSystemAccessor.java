package cz.martlin.jmop.core.sources.locals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

/**
 * The default implementation of {@link AbstractFileSystemAccessor}. Simply
 * works with bundle dirs and playlist and track files as expected. The paths
 * and names of such files and dirs are specified by {@link BaseFilesNamer}, the
 * way how to load contents of playlist files are then implemented by
 * {@link AbstractPlaylistLoader} instance.
 * 
 * @author martin
 *
 */
public class DefaultFileSystemAccessor implements AbstractFileSystemAccessor {

	private final File root;
	private final BaseFilesNamer namer;
	private final AbstractPlaylistLoader loader;

	public DefaultFileSystemAccessor(File root, BaseFilesNamer namer, AbstractPlaylistLoader loader)
			throws IOException {
		super();
		this.root = root;
		this.namer = namer;
		this.loader = loader;

	}

	@Override
	public List<String> listBundlesDirectoriesNames() throws IOException {
		return Arrays.stream(root.listFiles()) //
				.filter((f) -> f.isDirectory()) //
				.map((f) -> f.getName()) //
				.collect(Collectors.toList());
	}

	@Override
	public String bundleDirectoryName(String bundleName) throws IOException {
		return namer.directoryNameOfBundle(bundleName);
	}

	@Override
	public void createBundleDirectory(String bundleName) throws IOException {
		File bundleDir = namer.bundleDirOfBundleName(root, bundleName);
		Files.createDirectories(bundleDir.toPath());

	}

	@Override
	public List<String> listPlaylistsFiles(String bundleDirName) throws IOException {
		File bundleDir = namer.bundleDirOfBundleDirName(root, bundleDirName);
		return Arrays.stream(bundleDir.listFiles()) //
				.filter((f) -> isPlaylistFile(f)) //
				.map((f) -> f.getName()) //
				.collect(Collectors.toList());
	}

	/**
	 * Returns true if given "file" (in fact could be even a directory) is a
	 * playlist file (ends with the playlist file extension).
	 * 
	 * @param file
	 * @return
	 */
	private boolean isPlaylistFile(File file) {
		if (!file.isFile()) {
			return false;
		}

		String name = file.getName();
		String extension = loader.getFileExtension();
		String suffix = DefaultFilesNamer.DOT + extension;

		return name.endsWith(suffix);
	}

	@Override
	public boolean existsPlaylist(String bundleDirName, String playlistName) throws IOException {
		String extension = loader.getFileExtension();
		File playlistFile = namer.playlistFileOfPlaylist(root, bundleDirName, playlistName, extension);
		return playlistFile.exists();
	}

	@Override
	public PlaylistFileData getPlaylistMetadataOfName(String bundleDirName, String playlistName) throws IOException {
		String extension = loader.getFileExtension();
		File playlistFile = namer.playlistFileOfPlaylist(root, bundleDirName, playlistName, extension);
		return loader.load(null, playlistFile, true);
	}

	@Override
	public PlaylistFileData getPlaylistMetadataOfFile(String bundleDirName, String playlistFileName)
			throws IOException {
		File playlistFile = namer.playlistFileOfFile(root, bundleDirName, playlistFileName);
		return loader.load(null, playlistFile, true);
	}

	@Override
	public PlaylistFileData getPlaylistOfName(Bundle bundle, String bundleDirName, String playlistName)
			throws IOException {
		String extension = loader.getFileExtension();
		File playlistFile = namer.playlistFileOfPlaylist(root, bundleDirName, playlistName, extension);
		return loader.load(bundle, playlistFile, false);
	}

	@Override
	public void savePlaylist(String bundleDirName, Playlist playlist) throws IOException {
		String playlistName = playlist.getName();
		String extension = loader.getFileExtension();
		File playlistFile = namer.playlistFileOfPlaylist(root, bundleDirName, playlistName, extension);
		PlaylistFileData data = playlistToData(playlist);
		loader.save(data, playlistFile);
	}

	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format)
			throws IOException {
		return fileOfTrack(bundle, track, location, format);
	}

	@Override
	public boolean existsTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format)
			throws IOException {
		File trackFile = fileOfTrack(bundle, track, location, format);
		return trackFile.exists();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts given playlist to playlist file data.
	 * @param playlist
	 * @return
	 */
	private PlaylistFileData playlistToData(Playlist playlist) {
		Bundle bundle = playlist.getBundle();
		String bundleName = bundle.getName();
		SourceKind kind = bundle.getKind();
		String playlistName = playlist.getName();
		Tracklist tracklist = playlist.getTracks();
		boolean locked = playlist.isLocked();
		int currentTrack = playlist.getCurrentTrackIndex();

		return new PlaylistFileData(bundleName, playlistName, kind, tracklist, currentTrack, locked);
	}

	/**
	 * Returns file for track at given location and of given format. Creates directories, if do not exist.
	 * @param bundle
	 * @param track
	 * @param location
	 * @param format
	 * @return
	 * @throws IOException
	 */
	private File fileOfTrack(Bundle bundle, Track track, TrackFileLocation location, TrackFileFormat format)
			throws IOException {
		File bundleDir = locatedBundleDir(bundle, location);
		if (!bundleDir.exists()) {
			Files.createDirectories(bundleDir.toPath());
		}
		String trackFileName = namer.fileNameOfTrack(track, format);
		return new File(bundleDir, trackFileName);
	}

	/**
	 * Returns path of the bundle directory based on the location.
	 * @param bundle
	 * @param location
	 * @return
	 */
	private File locatedBundleDir(Bundle bundle, TrackFileLocation location) {
		String bundleName = bundle.getName();
		switch (location) {
		case CACHE:
			return namer.cacheBundleDir(root, bundleName);
		case SAVE:
			return namer.bundleDirOfBundleName(root, bundleName);
		case TEMP:
			return namer.tempBundleDir(bundleName);
		default:
			throw new IllegalArgumentException("Unknown location " + location);
		}
	}

}
