package cz.martlin.jmop.core.wrappers.builder;

import cz.martlin.jmop.core.check.BaseJMOPEnvironmentChecker;
import cz.martlin.jmop.core.check.DefaultJMOPEnvironmentChecker;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.CommandlineData;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.player.JavaFXMediaPlayer;
import cz.martlin.jmop.core.player.PlayerWrapper;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.playlister.Playlister;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;
import cz.martlin.jmop.core.preparer.TrackPreparer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.LocalSourceWrapper;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseSourceConverter;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.remotes.FFMPEGConverter;
import cz.martlin.jmop.core.sources.remotes.YoutubeDlDownloader;
import cz.martlin.jmop.core.strategy.base.BasePlaylisterStrategy;
import cz.martlin.jmop.core.strategy.impls.InfiniteOfflineStrategy;
import cz.martlin.jmop.core.strategy.impls.StandartOnlineStrategy;
import cz.martlin.jmop.core.strategy.impls.StaticPlaylistStrategy;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;

public abstract class SimpleJMOPPlayerBuilder implements BaseJMOPBuilder {

	public SimpleJMOPPlayerBuilder() {
		super();
	}

	@Override
	public JMOPPlayer create(CommandlineData data) throws Exception {
		BaseConfiguration config = createConfiguration(data);
		ErrorReporter reporter = new ErrorReporter();
		InternetConnectionStatus connection = createInternetConnectionStatus(data, config);

		// level 1 things
		AbstractRemoteSource remote = createRemoteSource(data, config, connection);
		BaseLocalSource local = createLocalSource(data, config);

		BaseSourceDownloader downloader = createDownloader(data, config, connection, remote, local);
		BaseSourceConverter converter = createConverter(data, config, local);

		AbstractTrackFileLocator locator = createLocator(data, config);
		BasePlayer player = createPlayer(data, config, local, locator);

		// level 2 things
		TrackPreparer preparer = new TrackPreparer(reporter, config, remote, local, locator, downloader, converter,
				player);

		BasePlaylisterStrategy lockedStrategy = createLockedStrategy(data, config);
		BasePlaylisterStrategy offlineStrategy = createOfflineStrategy(data, config);
		BasePlaylisterStrategy onlineStrategy = createOnlineStrategy(data, config, preparer);

		Playlister playlister = new Playlister(connection, lockedStrategy, offlineStrategy, onlineStrategy);

		// level 3 things
		PlayerWrapper playerWrapper = new PlayerWrapper(player);
		PlaylisterWrapper playlisterWrapper = new PlaylisterWrapper(playlister);

		PlayerEngine engine = new PlayerEngine(reporter, playlisterWrapper, playerWrapper, preparer);

		LocalSourceWrapper localWrapper = new LocalSourceWrapper(reporter, local, playlisterWrapper);

		BaseJMOPEnvironmentChecker checker = createEnvironmentChecker(data, config, local, remote, downloader,
				converter);

		// level 4 things

		JMOPPlayer jmop = new JMOPPlayer(config, reporter, engine, localWrapper, preparer, checker);

		// level 5 things

		checkAndStartPlayling(data, config, jmop);

		return jmop;
	}

	private void checkAndStartPlayling(CommandlineData data, BaseConfiguration config, JMOPPlayer jmop)
			throws JMOPSourceException {
		if (data.getBundleToPlayName() != null) {
			String bundleName = data.getBundleToPlayName();
			String playlistName;

			if (data.getPlaylistToPlayName() != null) {
				playlistName = data.getPlaylistToPlayName();
			} else {
				playlistName = config.getAllTracksPlaylistName();
			}

			jmop.startPlaylist(bundleName, playlistName);
		}
	}

	private BaseJMOPEnvironmentChecker createEnvironmentChecker(CommandlineData data, BaseConfiguration config,
			BaseLocalSource local, AbstractRemoteSource remote, BaseSourceDownloader downloader,
			BaseSourceConverter converter) {

		return new DefaultJMOPEnvironmentChecker(downloader, converter);
	}

	private BasePlaylisterStrategy createLockedStrategy(CommandlineData data, BaseConfiguration config) {
		return new StaticPlaylistStrategy();
	}

	private BasePlaylisterStrategy createOfflineStrategy(CommandlineData data, BaseConfiguration config) {
		return new InfiniteOfflineStrategy(); // TODO data.getRandomSeed()
	}

	private BasePlaylisterStrategy createOnlineStrategy(CommandlineData data, BaseConfiguration config,
			TrackPreparer preparer) {
		return new StandartOnlineStrategy(preparer);
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