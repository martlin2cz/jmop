package cz.martlin.jmop.common.storages.load;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;

public abstract class AbstractMusicdataLoader implements BaseMusicdataLoader {

	public AbstractMusicdataLoader() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void load(BaseInMemoryMusicbase inmemory) {
		Map<String, Bundle> bundles = loadBundles(inmemory);

		for (String bundleName : bundles.keySet()) {
			Bundle bundle = bundles.get(bundleName);

			Map<String, Track> tracks = loadTracks(inmemory, bundleName, bundle);

			loadPlaylists(inmemory, bundleName, bundle, tracks);
		}
	}

	protected void loadPlaylists(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle,
			Map<String, Track> tracks) {

		for (String playlistName : loadPlaylistsNames(bundle, bundleName)) {
			Playlist playlist = loadPlaylist(bundle, tracks, playlistName);

			inmemory.addPlaylist(playlist);
		}
	}

	protected Map<String, Track> loadTracks(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle) {

		Map<String, Track> tracks = new HashMap<>();

		for (String trackTitle : loadTracksTitles(bundle, bundleName)) {
			Track track = loadTrack(bundle, trackTitle);

			inmemory.addTrack(track);
			tracks.put(trackTitle, track);
		}

		return tracks;
	}

	protected Map<String, Bundle> loadBundles(BaseInMemoryMusicbase inmemory) {
		Map<String, Bundle> bundles = new HashMap<>();

		for (String bundleName : loadBundlesNames()) {
			Bundle bundle = loadBundle(bundleName);

			inmemory.addBundle(bundle);
			bundles.put(bundleName, bundle);
		}

		return bundles;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public abstract List<String> loadBundlesNames();

	public abstract Bundle loadBundle(String bundleName);

	public abstract List<String> loadPlaylistsNames(Bundle bundle, String bundleName);

	public abstract Playlist loadPlaylist(Bundle bundle, Map<String, Track> tracks, String playlistName);

	public abstract List<String> loadTracksTitles(Bundle bundle, String bundleName);

	public abstract Track loadTrack(Bundle bundle, String trackTitle);

	/////////////////////////////////////////////////////////////////////////////////////
}
