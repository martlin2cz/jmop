package cz.martlin.jmop.common.storages.storage.musicdataloader;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.storages.storage.FileSystemedStorage;
import cz.martlin.jmop.common.storages.storage.musicdataloader.bundlesloaders.BaseBundlesLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.playlistsloaders.BasePlaylistsLoader;
import cz.martlin.jmop.common.storages.storage.musicdataloader.tracksloaders.BaseTracksLoader;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An common musicdata loader. Delegates its calls to bundles loader, playlists
 * loader and tracks loader respectivelly.
 * 
 * It's component of the {@link FileSystemedStorage}.
 * 
 * @author martin
 *
 */
public class CommonMusicdataLoader implements BaseMusicdataLoader {

	private final BaseBundlesLoader bundlesLoader;
	private final BasePlaylistsLoader playlistsLoader;
	private final BaseTracksLoader tracksLoader;

	public CommonMusicdataLoader(BaseBundlesLoader bundlesLoader, BasePlaylistsLoader playlistsLoader,
			BaseTracksLoader tracksLoader) {
		super();
		this.bundlesLoader = bundlesLoader;
		this.playlistsLoader = playlistsLoader;
		this.tracksLoader = tracksLoader;
	}

	@Override
	public void load(BaseInMemoryMusicbase inmemory) throws JMOPPersistenceException {
		Set<Bundle> bundles = loadBundles(inmemory);

		for (Bundle bundle : bundles) {
			Set<Track> tracks = loadTracks(inmemory, bundle);

			loadPlaylists(inmemory, bundle, tracks);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Loads bundles.
	 * 
	 * @param inmemory
	 * @return
	 * @throws JMOPPersistenceException
	 */
	protected Set<Bundle> loadBundles(BaseInMemoryMusicbase inmemory) throws JMOPPersistenceException {
		Set<Bundle> bundles = bundlesLoader.loadBundles();

		for (Bundle bundle : bundles) {
			inmemory.addBundle(bundle);
		}

		return bundles;
	}

	/**
	 * Loads tracks.
	 * 
	 * @param inmemory
	 * @param bundle
	 * @return
	 * @throws JMOPPersistenceException
	 */
	protected Set<Track> loadTracks(BaseInMemoryMusicbase inmemory, Bundle bundle) throws JMOPPersistenceException {
		Set<Track> tracks = tracksLoader.loadTracks(bundle);

		for (Track track : tracks) {
			inmemory.addTrack(track);
		}

		return tracks;
	}

	/**
	 * Loads playlists.
	 * 
	 * @param inmemory
	 * @param bundle
	 * @param tracks
	 * @return
	 * @throws JMOPPersistenceException
	 */
	protected Set<Playlist> loadPlaylists(BaseInMemoryMusicbase inmemory, Bundle bundle, Set<Track> tracks) throws JMOPPersistenceException {
		Map<String, Track> tracksMap = mapTracks(tracks);

		Set<Playlist> playlists = playlistsLoader.loadPlaylists(bundle, tracksMap);

		for (Playlist playlist : playlists) {
			inmemory.addPlaylist(playlist);
		}

		return playlists;
	}

	/////////////////////////////////////////////////////////////////////////////////////

//	private Map<String, Bundle> mapBundles(Set<Bundle> bundles) {
//		return bundles.stream() //
//				.collect(Collectors.toMap( //
//						(b) -> b.getName(), //
//						(b) -> b));
//	}

//	private Map<String, Playlist> mapPlaylists(Set<Playlist> playlists) {
//		return playlists.stream() //
//				.collect(Collectors.toMap( //
//						(p) -> p.getName(), //
//						(p) -> p));
//	}

	private Map<String, Track> mapTracks(Set<Track> tracks) {
		return tracks.stream() //
				.collect(Collectors.toMap( //
						(t) -> t.getTitle(), //
						(t) -> t));
	}

}