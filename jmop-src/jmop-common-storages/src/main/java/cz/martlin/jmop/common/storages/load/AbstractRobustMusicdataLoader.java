package cz.martlin.jmop.common.storages.load;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

public abstract class AbstractRobustMusicdataLoader extends AbstractMusicdataLoader {

	private final BaseErrorReporter reporter;

	public AbstractRobustMusicdataLoader(BaseErrorReporter reporter) {
		super();
		this.reporter = reporter;
	}

	protected void loadPlaylists(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle,
			Map<String, Track> tracks) {

		List<String> playlistsNames;
		try {
			playlistsNames = loadPlaylistsNames(bundle, bundleName);
		} catch (JMOPRuntimeException e) {
			reporter.report("Could not list playlist names in " + bundleName, e);
			playlistsNames = Collections.emptyList();
		}

		for (String playlistName : playlistsNames) {
			try {
				Playlist playlist = loadPlaylist(bundle, tracks, playlistName);
				inmemory.addPlaylist(playlist);
			} catch (Exception e) {
				reporter.report("Could not load playlist " + playlistName, e);
			}
		}
	}

	protected Map<String, Track> loadTracks(BaseInMemoryMusicbase inmemory, String bundleName, Bundle bundle) {

		Map<String, Track> tracks = new HashMap<>();

		List<String> tracksTitles;
		try {
			tracksTitles = loadTracksTitles(bundle, bundleName);
		} catch (JMOPRuntimeException e) {
			reporter.report("Could not list track titles in " + bundleName, e);
			tracksTitles = Collections.emptyList();
		}

		for (String trackTitle : tracksTitles) {
			try {
				Track track = loadTrack(bundle, trackTitle);

				inmemory.addTrack(track);
				tracks.put(trackTitle, track);
			} catch (Exception e) {
				reporter.report("Could not load track " + trackTitle, e);
			}
		}

		return tracks;
	}

	protected Map<String, Bundle> loadBundles(BaseInMemoryMusicbase inmemory) {
		Map<String, Bundle> bundles = new HashMap<>();

		List<String> bundlesNames;
		try {
			bundlesNames = loadBundlesNames();
		} catch (JMOPRuntimeException e) {
			reporter.report("Could not list bundles names ", e);
			bundlesNames = Collections.emptyList();
		}

		for (String bundleName : bundlesNames) {
			try {
				Bundle bundle = loadBundle(bundleName);

				inmemory.addBundle(bundle);
				bundles.put(bundleName, bundle);
			} catch (Exception e) {
				reporter.report("Could not load bundle " + bundleName, e);
			}
		}

		return bundles;
	}

}
