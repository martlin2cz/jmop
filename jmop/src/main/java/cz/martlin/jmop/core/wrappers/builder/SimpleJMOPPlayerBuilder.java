package cz.martlin.jmop.core.wrappers.builder;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.CommandlineData;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.player.JavaFXMediaPlayer;
import cz.martlin.jmop.core.player.PlayerWrapper;
import cz.martlin.jmop.core.playlister.BasePlaylister;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.playlister.Playlister;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;
import cz.martlin.jmop.core.playlister.StaticPlaylistPlaylister;
import cz.martlin.jmop.core.playlister.TotallyOnlinePlaylister;
import cz.martlin.jmop.core.preparer.TrackPreparer;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.FFMPEGConverter;
import cz.martlin.jmop.core.sources.download.YoutubeDlDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.LocalSourceWrapper;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.wrappers.BaseJMOPEnvironmentChecker;
import cz.martlin.jmop.core.wrappers.DefaultJMOPEnvironmentChecker;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;

public abstract class SimpleJMOPPlayerBuilder implements BaseJMOPBuilder {

	public SimpleJMOPPlayerBuilder() {
		super();
	}

	@Override
	public JMOPPlayer create(CommandlineData data) throws Exception {
		BaseConfiguration config = createConfiguration(data);
		InternetConnectionStatus connection = createInternetConnectionStatus(data, config);

		// level 1 things
		AbstractRemoteSource remote = createRemoteSource(data, config, connection);
		BaseLocalSource local = createLocalSource(data, config);

		BaseSourceDownloader downloader = createDownloader(data, config, connection, remote, local);
		BaseSourceConverter converter = createConverter(data, config, local);

		AbstractTrackFileLocator locator = createLocator(data, config);
		BasePlayer player = createPlayer(data, config, local, locator);

		// level 2 things
		TrackPreparer preparer = new TrackPreparer(config, remote, local, locator, downloader, converter, player);

		BasePlaylister offlinePlaylister = createOfflinePlaylister();
		BasePlaylister onlinePlaylister = createOnlinePlaylister(preparer);

		Playlister playlister = new Playlister(connection, offlinePlaylister, onlinePlaylister);

		// level 2 things

		PlayerWrapper playerWrapper = new PlayerWrapper(player);
		PlaylisterWrapper playlisterWrapper = new PlaylisterWrapper(playlister);

		PlayerEngine engine = new PlayerEngine(playlisterWrapper, playerWrapper, preparer);

		LocalSourceWrapper localWrapper = new LocalSourceWrapper(local, playlisterWrapper);

		BaseJMOPEnvironmentChecker checker = createEnvironmentChecker(data, config, local, remote, downloader,
				converter);

		// level 3 things

		JMOPPlayer jmop = new JMOPPlayer(config,  engine, localWrapper, preparer, checker);

		// level 4 things

		if (data.getBundleToPlayName() != null) {
			String bundleName = data.getBundleToPlayName();
			if (data.getPlaylistToPlayName() != null) {
				String playlistName = data.getPlaylistToPlayName();
				jmop.startPlaylist(bundleName, playlistName);
			}
		}

		return jmop;
	}

	private BaseJMOPEnvironmentChecker createEnvironmentChecker(CommandlineData data, BaseConfiguration config,
			BaseLocalSource local, AbstractRemoteSource remote, BaseSourceDownloader downloader,
			BaseSourceConverter converter) {

		return new DefaultJMOPEnvironmentChecker(downloader, converter);
	}

	private TotallyOnlinePlaylister createOnlinePlaylister(TrackPreparer preparer) {
		return new TotallyOnlinePlaylister(preparer);
	}

	private StaticPlaylistPlaylister createOfflinePlaylister() {
		return new StaticPlaylistPlaylister();
	}

	public abstract BaseConfiguration createConfiguration(CommandlineData data) throws Exception;

	public abstract InternetConnectionStatus createInternetConnectionStatus(CommandlineData data,
			BaseConfiguration config) throws Exception;

	public abstract AbstractRemoteSource createRemoteSource(CommandlineData data, BaseConfiguration config,
			InternetConnectionStatus connection) throws Exception;

	public abstract BaseLocalSource createLocalSource(CommandlineData data, BaseConfiguration config) throws Exception;

	public abstract AbstractTrackFileLocator createLocator(CommandlineData data, BaseConfiguration config)
			throws Exception;

	public abstract JavaFXMediaPlayer createPlayer(CommandlineData data, BaseConfiguration config,
			BaseLocalSource local, AbstractTrackFileLocator locator) throws Exception;

	public abstract YoutubeDlDownloader createDownloader(CommandlineData data, BaseConfiguration config,
			InternetConnectionStatus connection, AbstractRemoteSource remote, BaseLocalSource local) throws Exception;

	public abstract FFMPEGConverter createConverter(CommandlineData data, BaseConfiguration config,
			BaseLocalSource local) throws Exception;

}