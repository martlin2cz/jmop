package cz.martlin.jmop.common.storages.storage.musicdataloader;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.storages.components.BundlesDirStorageComponent;
import cz.martlin.jmop.common.storages.components.SimpleStorageComponent;
import cz.martlin.jmop.common.storages.filesystem.BaseFileSystemAccessor;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BaseBundlesDirLocator;
import cz.martlin.jmop.common.storages.storage.filesystemer.locators.BasePlaylistLocator;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.BaseBundlesByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.BaseBundlesIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.BasePlaylistsByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.BasePlaylistsIdentifiersLister;
import cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.BaseTracksByIdentifierLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.BaseTracksIdentifiersLister;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * An generic implementor of the bundles, playlists and tracks by identifiers
 * listers and loaders.
 * 
 * It's component of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public class SimpleBundlesDirListerLoader implements //
		BaseBundlesIdentifiersLister<String>, BaseBundlesByIdentifierLoader<String>, //
		BasePlaylistsIdentifiersLister<String>, BasePlaylistsByIdentifierLoader<String>, //
		BaseTracksIdentifiersLister<String>, BaseTracksByIdentifierLoader<String>, //
		SimpleStorageComponent, BundlesDirStorageComponent {

	private final File root;
	private final BaseFileSystemAccessor fs;
	private final BaseBundlesDirLocator bundlesDirLocator;
	private final BasePlaylistLocator playlistLocator;

	public SimpleBundlesDirListerLoader(File root, BaseFileSystemAccessor fs, BaseBundlesDirLocator bundlesDirLocator,
			BasePlaylistLocator playlistLocator) {
		super();
		this.root = root;
		this.fs = fs;
		this.bundlesDirLocator = bundlesDirLocator;
		this.playlistLocator = playlistLocator;
	}

	@Override
	public Set<String> loadBundlesIdentifiers() throws JMOPPersistenceException {
		try {
			return fs.listDirectories(root) //
					.map(d -> d.getName()) //
					.collect(Collectors.toSet());
		} catch (JMOPPersistenceException e) {
			throw new JMOPPersistenceException("Could not load bundle names", e);
		}
	}

	@Override
	public Bundle loadBundle(String name) {
		Metadata metadata = Metadata.createNew();
		return new Bundle(name, metadata);
	}

	@Override
	public Set<String> loadPlaylistsIdentifiers(Bundle bundle) throws JMOPPersistenceException {
		try {
			File bundleDir = bundlesDirLocator.bundleDir(bundle);
			return fs.listFiles(bundleDir) //
					.filter(f -> isTracklistFile(f)) //
					.map(f -> tracklistFileToPlaylistName(f)) //
					.collect(Collectors.toSet());
		} catch (JMOPPersistenceException e) {
			throw new JMOPPersistenceException("Could not load playlist names", e);
		}
	}

	@Override
	public Playlist loadPlaylist(Bundle bundle, String name, Map<String, Track> tracksMap)
			throws JMOPPersistenceException {
		try {
			Metadata metadata = Metadata.createNew();
			File playlistFile = playlistLocator.playlistFile(bundle, name);
			Tracklist tracklist = loadTracklist(fs, bundle, playlistFile, tracksMap);
			TrackIndex currentTrackIndex = TrackIndex.ofIndex(0);

			return new Playlist(bundle, name, tracklist, currentTrackIndex, metadata);
		} catch (JMOPPersistenceException e) {
			throw new JMOPPersistenceException("Could not load playlist", e);
		}
	}

	@Override
	public Set<String> loadTracksIdentifiers(Bundle bundle) throws JMOPPersistenceException {
		try {
			File bundleDir = bundlesDirLocator.bundleDir(bundle);
			return fs.listFiles(bundleDir) //
					.filter(f -> isTrackFile(f)) //
					.map(f -> trackFileToTrackId(f)) //
					.collect(Collectors.toSet());
		} catch (JMOPPersistenceException e) {
			throw new JMOPPersistenceException("Could not load tracks titles", e);
		}
	}

	@Override
	public Track loadTrack(Bundle bundle, String title) throws JMOPPersistenceException {
		Metadata metadata = Metadata.createNew();
		Duration duration = DurationUtilities.createDuration(0, 3, 15);
		String description = title;
		File file = new File(title);
		URI uri = file.toURI();
		
		return new Track(bundle, title, description, duration, uri, file, metadata);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private static boolean isTracklistFile(File file) {
		return file.getName().endsWith(".txt");
	}

	private static boolean isTrackFile(File file) {
		return file.getName().endsWith(".mp3"); // TODO save format
	}

	private static String tracklistFileToPlaylistName(File file) {
		return file.getName().replace(".txt", "");
	}

	private static String trackFileToTrackId(File file) {
		return file.getName().replace(".mp3", "");
	}

	private static Tracklist loadTracklist(BaseFileSystemAccessor fs, Bundle bundle, File playlistFile,
			Map<String, Track> tracks) throws JMOPPersistenceException {
		try {
			return new Tracklist( //
					fs.loadLines(playlistFile) //
							.stream() //
							.map(t -> tracks.get(t)) //
							.collect(Collectors.toList()));
		} catch (JMOPPersistenceException e) {
			throw new JMOPPersistenceException("Could not load tracklist", e);
		}
	}

}
