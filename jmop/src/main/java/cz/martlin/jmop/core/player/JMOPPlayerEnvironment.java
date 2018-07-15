package cz.martlin.jmop.core.player;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.Sources;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.FFMPEGConverter;
import cz.martlin.jmop.core.sources.download.SimpleLoggingListener;
import cz.martlin.jmop.core.sources.download.YoutubeDlDownloader;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.local.DefaultLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultPlaylistLoader;
import cz.martlin.jmop.core.sources.local.PlaylistLoader;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;

public class JMOPPlayerEnvironment {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final JMOPPlaylister playlister;
	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;

	private Bundle currentBundle;
	private Playlist currentPlaylist;

	public JMOPPlayerEnvironment(JMOPPlaylister playlister, BaseLocalSource local, AbstractRemoteSource remote) {
		super();
		this.playlister = playlister;
		this.local = local;
		this.remote = remote;
	}

	public JMOPPlaylister getPlaylister() {
		return playlister;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	public void startNewBundle(SourceKind kind, String bundleName, String querySeed) throws JMOPSourceException {
		LOG.info("Starting bundle " + kind + " " + bundleName + " with query " + querySeed);
		
		Bundle bundle = new Bundle(kind, bundleName);
		local.createBundle(bundle);

		this.currentBundle = bundle;

		Track initial = remote.search(bundle, querySeed);
		BetterPlaylistRuntime runtime = new BetterPlaylistRuntime(initial);

		Playlist playlist = new Playlist(bundle, querySeed, runtime);
		local.savePlaylist(bundle, playlist);

		playlister.setPlaylist(runtime);
		playlister.play();
	}

	public void startPlaylist(Bundle bundle, String playlistName) throws JMOPSourceException {
		LOG.info("Starting playlist" + playlistName + " of bundle " + bundle.getName());
		
		Playlist playlist = local.getPlaylist(bundle, playlistName);

		BetterPlaylistRuntime btrPlaylist = convert(playlist);

		playlister.setPlaylist(btrPlaylist);
		playlister.play();
	}

	public void renameCurrentPlaylist(String newName) throws JMOPSourceException {
		LOG.info("Renaming current playlist to " + newName);
		
		currentPlaylist.changeName(newName);
		local.savePlaylist(currentBundle, currentPlaylist);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private BetterPlaylistRuntime convert(Playlist playlist) {
		return new BetterPlaylistRuntime(playlist.getTracks().getTracks());
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public static JMOPPlayerEnvironment create(File rootDirectory, AbstractPlayer player) {
		AbstractRemoteSource remote = new YoutubeSource();
		BaseLocalSource local = createLocal(rootDirectory);

		// TODO FIXME listener shall be task itself
		ProgressListener listener = new SimpleLoggingListener(System.out);
		BaseSourceDownloader downloader = new YoutubeDlDownloader(local, remote, listener);
		TrackFileFormat inputFormat = YoutubeDlDownloader.DOWNLOAD_FILE_FORMAT;
		TrackFileFormat outputFormat = TrackFileFormat.MP3;
		BaseSourceConverter converter = new FFMPEGConverter(local, inputFormat, outputFormat, listener);
		Sources sources = new Sources(local, remote, downloader, converter);

		InternetConnectionStatus connection = new InternetConnectionStatus();
		JMOPPlaylister playlister = new JMOPPlaylister(player, sources, connection);

		return new JMOPPlayerEnvironment(playlister, local, remote);
	}

	private static BaseLocalSource createLocal(File rootDirectory) {
		BaseFilesNamer namer = new DefaultFilesNamer();
		PlaylistLoader loader = new DefaultPlaylistLoader();
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(rootDirectory, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(fileSystem);
		return local;
	}
}
