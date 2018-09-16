package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.preparer.TrackPreparer;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.LocalSourceWrapper;

public class JMOPSources {
	private final LocalSourceWrapper local;
	private final TrackPreparer preparer;

	public JMOPSources(LocalSourceWrapper local, TrackPreparer preparer) {
		super();
		this.local = local;
		this.preparer = preparer;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public Playlist createNewBundleAndPrepare(SourceKind kind, String bundleName, String query, PlayerEngine engine)
			throws JMOPSourceException {
		Bundle bundle = createBundle(kind, bundleName);
		Playlist playlist = new Playlist(bundle, query);

		preparer.startSearchAndLoadInBg(bundle, query, engine);

		return playlist;
	}

	public Playlist createNewPlaylist(Bundle bundle, String query, PlayerEngine engine) throws JMOPSourceException {
		Playlist playlist = new Playlist(bundle, query);

		preparer.startSearchAndLoadInBg(bundle, query, engine);
		return playlist;
	}

	public Playlist loadPlaylist(String bundleName, String playlistName, PlayerEngine engine) throws JMOPSourceException {
		Bundle bundle = local.getBundle(bundleName);
		Playlist playlist = local.getPlaylist(bundle, playlistName);

		return playlist;

	}

	public void savePlaylist(Playlist playlist, String newPlaylistName) throws JMOPSourceException {
		playlist.setName(newPlaylistName);
		Bundle bundle = playlist.getBundle();
		local.savePlaylist(bundle, playlist);
	}

	public void queryAndLoad(Bundle bundle, String query, PlayerEngine engine) throws JMOPSourceException {
		preparer.startSearchAndLoadInBg(bundle, query, engine);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	public List<String> listAllBundles() throws JMOPSourceException {
		return local.listBundlesNames();
	}

	public List<String> listPlaylists(String bundleName) throws JMOPSourceException {
		Bundle bundle = local.getBundle(bundleName);
		return local.listPlaylistNames(bundle);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	private Bundle createBundle(SourceKind kind, String bundleName) throws JMOPSourceException {
		Bundle bundle = new Bundle(kind, bundleName);
		local.createBundle(bundle);
		return bundle;
	}

	// @Deprecated
	// private Track prepareInitialTrack(Bundle bundle, String querySeed) throws
	// JMOPSourceException {
	// Track track = remote.search(bundle, querySeed);
	// // preparer.load(track);
	// return track;
	// }
	//
	// private Playlist createInitialPlaylist(Bundle bundle, String query) {
	// BetterPlaylistRuntime runtime = new BetterPlaylistRuntime();
	// Playlist playlist = new Playlist(bundle, query, runtime);
	// return playlist;
	// }

	///////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////

}
