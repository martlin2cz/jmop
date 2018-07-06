package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.player.Playlist;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

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
		Playlist full = getFullPlaylist(name); // TODO
		SourceKind kind = full.getSource();
		return new Bundle(kind, name, full);
	}

	@Override
	public void createBundle(Bundle bundle) throws IOException {
		createBundleDirectory(bundle);
		Playlist full = bundle.getFullPlaylist();
		savePlaylist(bundle, full);

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

	private Playlist getFullPlaylist(String bundleName) throws IOException {
		File file = namer.fileOfFullPlaylist(root, null, bundleName);
		// FIXME implementeme: return loader.load(file);
		return new Playlist(file.getName(), SourceKind.YOUTUBE);
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) throws IOException {
		File file = fileOfPlaylist(bundle, name);
		// FIXME implementeme: return loader.load(file);
		return new Playlist(name, SourceKind.YOUTUBE);
	}

	private File fileOfPlaylist(Bundle bundle, String name) {
		SourceKind source = bundle.getKind();
		String bundleName = bundle.getName();
		return namer.fileOfPlaylist(root, source, bundleName, name);
	}

	@Override
	public void savePlaylist(Bundle bundle, Playlist playlist) throws IOException {
		String name = playlist.getName();
		File file = fileOfPlaylist(bundle, name);
		// FIXME implementme: loader.save(playlist, file);
		file.createNewFile();
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
