package cz.martlin.jmop.core.wrappers.builder;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.CommandlineData;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.BaseOperations;
import cz.martlin.jmop.core.misc.ops.Operations;
import cz.martlin.jmop.core.misc.ops.OperationsManager;
import cz.martlin.jmop.core.player.JavaFXMediaPlayer;
import cz.martlin.jmop.core.player.PlayerWrapper;
import cz.martlin.jmop.core.player.base.player.BasePlayer;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.playlister.Playlister;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;
import cz.martlin.jmop.core.sources.local.misc.flu.FormatsLocationsUtility;
import cz.martlin.jmop.core.sources.local.misc.locator.BaseTrackFileLocator;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
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
		BaseErrorReporter reporter = createErrorReporter();
		InternetConnectionStatus connection = createInternetConnectionStatus(data, config);

		// level 1 things
		AbstractRemoteSource remote = createRemoteSource(data, config, connection);
		XXX_BaseLocalSource local = createLocalSource(data, config);

		BaseDownloader downloader = createDownloader(data, config, connection, remote, local);
		BaseConverter converter = createConverter(data, config, local);
		//TODO create remote source
		BaseTrackFileLocator locator = createLocator(data, config);
		BasePlayer player = createPlayer(data, config, local, locator);

		// level 2 things
		OperationsManager manager = new OperationsManager();
		FormatsLocationsUtility flu = new FormatsLocationsUtility(config, locator, downloader, player);
		BaseOperations operations = new Operations(manager, flu, remote, local);

		BasePlaylisterStrategy lockedStrategy = createLockedStrategy(data, config);
		BasePlaylisterStrategy offlineStrategy = createOfflineStrategy(data, config);
		BasePlaylisterStrategy onlineStrategy = createOnlineStrategy(data, config, operations);

		Playlister playlister = new Playlister(connection, lockedStrategy, offlineStrategy, onlineStrategy);

		// level 3 things
		PlayerWrapper playerWrapper = new PlayerWrapper(player);
		PlaylisterWrapper playlisterWrapper = new PlaylisterWrapper(playlister);

		PlayerEngine engine = new PlayerEngine(reporter, playlister, player, operations);

		// level 4 things
		JMOPPlayer jmop = new JMOPPlayer(config, reporter, engine, local, operations);

		// level 5 things
		checkAndStartPlayling(data, config, jmop);

		return jmop;
	}

	private BaseErrorReporter createErrorReporter() {
		return new GuiErrorReporter();
	}

	private void checkAndStartPlayling(CommandlineData data, BaseConfiguration config, JMOPPlayer jmop)
			throws JMOPMusicbaseException {
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

	private BasePlaylisterStrategy createLockedStrategy(CommandlineData data, BaseConfiguration config) {
		return new StaticPlaylistStrategy();
	}

	private BasePlaylisterStrategy createOfflineStrategy(CommandlineData data, BaseConfiguration config) {
		return new InfiniteOfflineStrategy(); // TODO data.getRandomSeed()
	}

	private BasePlaylisterStrategy createOnlineStrategy(CommandlineData data, BaseConfiguration config,
			BaseOperations operations) {
		return new StandartOnlineStrategy(operations);
	}

	public abstract BaseConfiguration createConfiguration(CommandlineData data) throws Exception;

	public abstract InternetConnectionStatus createInternetConnectionStatus(CommandlineData data,
			BaseConfiguration config) throws Exception;

	public abstract AbstractRemoteSource createRemoteSource(CommandlineData data, BaseConfiguration config,
			InternetConnectionStatus connection) throws Exception;

	public abstract XXX_BaseLocalSource createLocalSource(CommandlineData data, BaseConfiguration config) throws Exception;

	public abstract BaseTrackFileLocator createLocator(CommandlineData data, BaseConfiguration config)
			throws Exception;

	public abstract JavaFXMediaPlayer createPlayer(CommandlineData data, BaseConfiguration config,
			XXX_BaseLocalSource local, BaseTrackFileLocator locator) throws Exception;

	public abstract BaseDownloader createDownloader(CommandlineData data, BaseConfiguration config,
			InternetConnectionStatus connection, AbstractRemoteSource remote, XXX_BaseLocalSource local) throws Exception;

	public abstract BaseConverter createConverter(CommandlineData data, BaseConfiguration config,
			XXX_BaseLocalSource local) throws Exception;

}