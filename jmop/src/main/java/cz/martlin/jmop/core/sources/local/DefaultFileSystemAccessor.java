package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.player.Playlist;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public class DefaultFileSystemAccessor implements AbstractFileSystemAccessor {

	private static final String FULL_PLAYLIST_NAME = null;
	private final BaseFilesNamer namer;
	private final PlaylistLoader loader;
	
	public DefaultFileSystemAccessor(BaseFilesNamer namer, PlaylistLoader loader) {
		super();
		this.namer = namer;
		this.loader = loader;
	}
	@Override
	public List<String> listBundles() {
		List<File> bundlesDirs = namer.listBundles();

		return bundlesDirs.stream() //
				.map((f) -> namer.dirToBundleName(f)) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Bundle loadBundle(String name) throws IOException {
		Playlist full = getFullPlaylist(null); // TODO
		SourceKind kind = null; // TODO
		return new Bundle(kind, name, full);
	}

	@Override
	public void createBundle(Bundle bundle) throws IOException {
		File directory = namer.directoryOfBundle(bundle);

		Path path = directory.toPath();
		Files.createDirectory(path);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> listPlaylists(Bundle bundle) {
		List<File> playlistDirs = namer.listPlaylists(bundle);

		return playlistDirs.stream() //
				.map((f) -> namer.fileToPlaylistName(f)) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Playlist getFullPlaylist(Bundle bundle) throws IOException {
		File file = namer.fileOfPlaylist(bundle, FULL_PLAYLIST_NAME);
		return loader.load(file);
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) throws IOException {
		File file = namer.fileOfPlaylist(bundle, name);
		return loader.load(file);
	}

	@Override
	public void savePlaylist(Bundle bundle, Playlist playlist) throws IOException {
		String name = playlist.getName();
		File file = namer.fileOfPlaylist(bundle, name);
		loader.save(playlist, file);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileFormat format) {
		return namer.fileOfTrack(bundle, track, format);
		// TODO test existence
	}

}
