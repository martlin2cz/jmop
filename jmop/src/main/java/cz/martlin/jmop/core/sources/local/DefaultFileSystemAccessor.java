package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.sources.SourceKind;

public class DefaultFileSystemAccessor implements AbstractFileSystemAccessor {

	private final File root;
	private final BaseFilesNamer namer;
	private final PlaylistLoader loader;

	public DefaultFileSystemAccessor(File root, BaseFilesNamer namer, PlaylistLoader loader) {
		super();
		this.root = root;
		this.namer = namer;
		this.loader = loader;
	}

	@Override
	public List<String> listBundles() {
		File[] bundlesDirs = root.listFiles((f) -> isBundleDir(f));

		return Arrays.stream(bundlesDirs) //
				.map((d) -> namer.dirToBundleName(d)) //
				.collect(Collectors.toList()); //
	}

	private boolean isBundleDir(File fileOrDir) {
		return fileOrDir.isDirectory() && namer.isBundleDirectory(fileOrDir);
	}

	@Override
	public Bundle loadBundle(String name) throws IOException {
		String playlistName = namer.nameOfFullPlaylist();
		Bundle tmpBundle = new Bundle(null, name, new Tracklist());
		PlaylistFileData data = loadPlaylistFile(tmpBundle, playlistName);
		
		SourceKind kind = data.getKind();
		Tracklist tracklist = data.getTracklist();
		return new Bundle(kind, name, tracklist);
	}

	@Override
	public void createBundle(Bundle bundle) throws IOException {
		createBundleDirectory(bundle);

		Tracklist tracklist = bundle.tracks();
		SourceKind kind = bundle.getKind();

		String playlistName = namer.nameOfFullPlaylist();
		savePlaylistData(bundle, playlistName, kind, tracklist);
	}

	private void createBundleDirectory(Bundle bundle) throws IOException {
		File directory = directoryOfBundle(bundle);

		Path path = directory.toPath();
		Files.createDirectory(path);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> listPlaylists(Bundle bundle) {
		File bundleDirectory = directoryOfBundle(bundle);
		File[] playlistsFiles = bundleDirectory //
				.listFiles((f) -> isPlaylistFile(f));

		return Arrays.stream(playlistsFiles) //
				.map((f) -> namer.fileToPlaylistName(f)) //
				.collect(Collectors.toList()); //
	}

	private boolean isPlaylistFile(File fileOrDir) {
		return fileOrDir.isFile() && namer.isPlaylistFile(fileOrDir);
	}


	@Override
	public Playlist getPlaylist(Bundle bundle, String name) throws IOException {
		PlaylistFileData data = loadPlaylistFile(bundle, name);
		Tracklist tracks = data.getTracklist();
		return new Playlist(bundle, name, tracks);
	}

	private PlaylistFileData loadPlaylistFile(Bundle bundle, String name) throws IOException {
		File file = fileOfPlaylist(bundle, name);
		PlaylistFileData data = loader.load(bundle, file);
		return data;
	}

	private File fileOfPlaylist(Bundle bundle, String name) {
		SourceKind source = bundle.getKind();
		String bundleName = bundle.getName();
		return namer.fileOfPlaylist(root, source, bundleName, name);
	}

	@Override
	public void savePlaylist(Bundle bundle, Playlist playlist) throws IOException {
		String name = playlist.getName();

		SourceKind kind = bundle.getKind();
		Tracklist tracklist = playlist.getTracks();

		savePlaylistData(bundle, name, kind, tracklist);
	}

	private void savePlaylistData(Bundle bundle, String name, SourceKind kind, Tracklist tracklist) throws IOException {
		PlaylistFileData data = new PlaylistFileData(name, kind, tracklist);
		File file = fileOfPlaylist(bundle, name);

		loader.save(data, file);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileFormat format) {
		return namer.fileOfTrack(root, bundle, track, format);
		// TODO test existence
	}
	/////////////////////////////////////////////////////////////////////////////////////

	private File directoryOfBundle(Bundle bundle) {
		SourceKind kind = bundle.getKind();
		String name = bundle.getName();
		File directory = namer.directoryOfBundle(root, kind, name);
		return directory;
	}

}
