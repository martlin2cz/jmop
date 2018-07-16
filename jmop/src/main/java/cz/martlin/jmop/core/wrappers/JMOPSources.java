package cz.martlin.jmop.core.wrappers;

import java.util.List;
import java.util.function.Consumer;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.BetterPlaylistRuntime;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

public class JMOPSources {
	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;
	private final BaseSourceDownloader downloader;
	private final BaseSourceConverter converter;
	private final GuiDescriptor gui;


	public JMOPSources(BaseLocalSource local, AbstractRemoteSource remote, BaseSourceDownloader downloader,
			BaseSourceConverter converter, GuiDescriptor gui) {
		super();
		this.local = local;
		this.remote = remote;
		this.downloader = downloader;
		this.converter = converter;
		this.gui = gui;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public Playlist createNewBundleAndPrepare(SourceKind kind, String bundleName, String querySeed)
			throws JMOPSourceException {
		Bundle bundle = createBundle(kind, bundleName);
		Track track = prepareInitialTrack(bundle, querySeed);
		Playlist playlist = createInitialPlaylist(bundle, track, querySeed);
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
		checkAndDownload(track, null);
		return track;
	}

	private Playlist createInitialPlaylist(Bundle bundle, Track track, String querySeed) {
		BetterPlaylistRuntime runtime = new BetterPlaylistRuntime(track);
		Playlist playlist = new Playlist(bundle, querySeed, runtime);
		// TODO save here?
		return playlist;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public void checkAndDownload(Track track, Consumer<Track> onComplete) {
		DownloaderTask task = new DownloaderTask(downloader, converter, track);
		// TODO set binding to GUI

		if (onComplete != null) {
			task.setOnSucceeded((e) -> onComplete.accept(track));
			Thread thread = new Thread(task, "DownloaderTaskThread");
			thread.start();
		} else {
			task.run();
		}
	}
}
