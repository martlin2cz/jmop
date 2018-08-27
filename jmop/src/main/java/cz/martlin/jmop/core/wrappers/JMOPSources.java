package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.BetterPlaylistRuntime;
import cz.martlin.jmop.core.player.JMOPPlaylisterWithGui;
import cz.martlin.jmop.core.player.TrackPreparer;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

public class JMOPSources {
	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;
	private final BaseSourceDownloader downloader;
	private final BaseSourceConverter converter;
	private final GuiDescriptor gui;
	private final TrackPreparer preparer;
	private final JMOPPlaylisterWithGui playlister;

	public JMOPSources(BaseLocalSource local, AbstractRemoteSource remote, BaseSourceDownloader downloader,
			BaseSourceConverter converter, TrackPreparer preparer, JMOPPlaylisterWithGui playlister, GuiDescriptor gui) {
		super();
		this.local = local;
		this.remote = remote;
		this.downloader = downloader;
		this.converter = converter;
		this.preparer = preparer;
		this.playlister = playlister;
		this.gui = gui;
	}
	
	public TrackPreparer getPreparer() {
		return preparer;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public Playlist createNewBundleAndPrepare(SourceKind kind, String bundleName, String querySeed)
			throws JMOPSourceException {
		Bundle bundle = createBundle(kind, bundleName);
		Track track = prepareInitialTrack(bundle, querySeed);

		Playlist playlist = createInitialPlaylist(bundle, track, querySeed);
		preparer.prepreNextAndAppend(track, playlister);
		return playlist;
	}

	public Playlist createNewPlaylist(Bundle bundle, String querySeed) throws JMOPSourceException {
		//TODO queryAndCreatePlaylist method: ?
		Track track = prepareInitialTrack(bundle, querySeed);

		Playlist playlist = createInitialPlaylist(bundle, track, querySeed);
		preparer.prepreNextAndAppend(track, playlister);
		return playlist;
	}
	
	public Playlist loadPlaylist(String bundleName, String playlistName) throws JMOPSourceException {
		Bundle bundle = local.getBundle(bundleName);
		Playlist playlist = local.getPlaylist(bundle, playlistName);
		return playlist;

	}

	public void savePlaylist(Playlist playlist, String newPlaylistName) throws JMOPSourceException {
		playlist.changeName(newPlaylistName);
		Bundle bundle = playlist.getBundle();
		local.savePlaylist(bundle, playlist);
	}
	
	public Track queryAndLoad(Bundle bundle, String querySeed) throws JMOPSourceException {
		return prepareInitialTrack(bundle, querySeed);
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

	private Track prepareInitialTrack(Bundle bundle, String querySeed) throws JMOPSourceException {
		Track track = remote.search(bundle, querySeed);
		preparer.load(track);
		return track;
	}

	private Playlist createInitialPlaylist(Bundle bundle, Track track, String querySeed) {
		BetterPlaylistRuntime runtime = new BetterPlaylistRuntime(track);
		Playlist playlist = new Playlist(bundle, querySeed, runtime);
		// TODO save here?
		return playlist;
	}

	



	///////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////

}
