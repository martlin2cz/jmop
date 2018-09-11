package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.MapperWithException;
import cz.martlin.jmop.core.misc.MapperWithException.ExceptionInLoop;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

public class DefaultLocalSource implements BaseLocalSource {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseConfiguration config;
	private final AbstractFileSystemAccessor fileSystem;

	public DefaultLocalSource(BaseConfiguration config, AbstractFileSystemAccessor fileSystem) {
		super();
		this.config = config;
		this.fileSystem = fileSystem;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> listBundlesNames() throws JMOPSourceException {
		LOG.info("Listing bundle names");
		try {
			return listBundlesNamesInternal();
		} catch (IOException | ExceptionInLoop e) {
			throw new JMOPSourceException("Cannot list bundles", e);
		}
	}

	@Override
	public Bundle getBundle(String name) throws JMOPSourceException {
		LOG.info("Loading bundle " + name);
		try {
			return getBundleInternal(name);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot load bundle", e);
		}
	}

	@Override
	public void createBundle(Bundle bundle) throws JMOPSourceException {
		try {
			createBundleInternal(bundle);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot create bundle", e);
		}
	}

	@Override
	public void saveBundle(Bundle bundle) throws JMOPSourceException {
		try {
			saveBundleInternal(bundle);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot create bundle", e);
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<String> listPlaylistNames(Bundle bundle) throws JMOPSourceException {
		LOG.info("Listing playlists of bundle " + bundle.getName());
		try {
			return listsPlaylistNamesInternal(bundle);
		} catch (IOException | ExceptionInLoop e) {
			throw new JMOPSourceException("Cannot list playlists", e);
		}
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		LOG.info("Loading playlist " + name + " of bundle " + bundle.getName());
		try {
			return getPlaylistInternal(bundle, name);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot load playlist", e);
		}
	}

	@Override
	public void savePlaylist(Bundle bundle, Playlist playlist) throws JMOPSourceException {
		LOG.info("Saving playlist " + playlist.getName() + " of bundle " + bundle.getName());
		try {
			savePlaylistInternal(bundle, playlist);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot save playlist", e);
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track getTrack(Bundle bundle, String id) throws JMOPSourceException {
		LOG.info("Loading track " + id + " of bundle " + bundle.getName());
		return bundle.getTrack(id);
	}

	@Override
	public File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format)
			throws JMOPSourceException {
		LOG.info("Infering file of track " + track.getTitle() + " in " + location + " as " + format);
		try {
			return fileOfTrackInternal(track, location, format);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot infer file of track", e);
		}
	}

	@Override
	public boolean exists(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException {
		LOG.info("Checking existence of track " + track.getTitle() + " in " + location + " as " + format);
		try {
			return existsInternal(track, location, format);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannnot check file existence", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

	private List<String> listBundlesNamesInternal() throws IOException, ExceptionInLoop {
		List<String> dirs = fileSystem.listBundlesDirectoriesNames();
		return MapperWithException
				.mapWithException(dirs.stream(), //
						(d) -> dirNameToBundleName(d)) //
				.filter((b) -> b != null) //
				.collect(Collectors.toList());
	}

	private String dirNameToBundleName(String bundleDirName) throws IOException {
		PlaylistFileData data = loadAllTracksPlaylistMetadata(bundleDirName);
		if (data != null) {
			return data.getBundleName();
		} else {
			return null;
		}
	}

	private PlaylistFileData loadAllTracksPlaylist(Bundle bundle, String bundleDirName) throws IOException {
		String playlistName = config.getAllTracksPlaylistName();
		return fileSystem.getPlaylistOfName(bundle, bundleDirName, playlistName);
	}
	
	private PlaylistFileData loadAllTracksPlaylistMetadata(String bundleDirName) throws IOException {
		String playlistName = config.getAllTracksPlaylistName();
			boolean exists = fileSystem.existsPlaylist(bundleDirName, playlistName);
			if (!exists) {
				return null;
			}

		PlaylistFileData data = fileSystem.getPlaylistMetadataOfName(bundleDirName, playlistName);
		return data;
	}

	private Bundle getBundleInternal(String name) throws IOException {
		String bundleDirName = fileSystem.bundleDirectoryName(name);
		PlaylistFileData data = loadAllTracksPlaylistMetadata(bundleDirName);
		
		SourceKind kind = data.getKind();
		Bundle bundle = new Bundle(kind, name);
		
		loadAllTracksPlaylist(bundle, bundleDirName);
		
		return bundle;
	}

	private void createBundleInternal(Bundle bundle) throws IOException {
		String name = bundle.getName();
		fileSystem.createBundleDirectory(name);
	}

	
	private void saveBundleInternal(Bundle bundle) throws IOException {
		String playlistName = config.getAllTracksPlaylistName();
		Tracklist tracks = bundle.tracks();
		Playlist playlist = new Playlist(bundle, playlistName, tracks);
		
		savePlaylistInternal(bundle, playlist);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	private List<String> listsPlaylistNamesInternal(Bundle bundle) throws IOException, ExceptionInLoop {
		String bundleName = bundle.getName();
		String bundleDirName = fileSystem.bundleDirectoryName(bundleName);
		List<String> playlistsFiles = fileSystem.listPlaylistsFiles(bundleDirName);
		return MapperWithException
				.mapWithException(playlistsFiles.stream(), //
						(f) -> playlistFileToPlaylistName(bundleDirName, f)) //
				.collect(Collectors.toList());
	}

	private String playlistFileToPlaylistName(String bundleDirName, String playlistFileName) throws IOException {
		PlaylistFileData data = fileSystem.getPlaylistMetadataOfFile(bundleDirName, playlistFileName);
		return data.getPlaylistName();
	}

	private Playlist getPlaylistInternal(Bundle bundle, String name) throws IOException {
		String bundleName = bundle.getName();
		String bundleDirName = fileSystem.bundleDirectoryName(bundleName);
		PlaylistFileData data = fileSystem.getPlaylistOfName(bundle, bundleDirName, name);
		Tracklist tracklist = data.getTracklist();
		return new Playlist(bundle, name, tracklist);
	}

	private void savePlaylistInternal(Bundle bundle, Playlist playlist) throws IOException {
		String bundleName = bundle.getName();
		String bundleDirName = fileSystem.bundleDirectoryName(bundleName);
		fileSystem.savePlaylist(bundleDirName, playlist);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private File fileOfTrackInternal(Track track, TrackFileLocation location, TrackFileFormat format)
			throws IOException {
		Bundle bundle = track.getBundle();
		return fileSystem.getFileOfTrack(bundle, track, location, format);
	}

	private boolean existsInternal(Track track, TrackFileLocation location, TrackFileFormat format) throws IOException {
		Bundle bundle = track.getBundle();
		return fileSystem.existsTrack(bundle, track, location, format);
	}
}
