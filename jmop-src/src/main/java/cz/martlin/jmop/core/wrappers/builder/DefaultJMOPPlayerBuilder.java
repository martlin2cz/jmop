package cz.martlin.jmop.core.wrappers.builder;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.CommandlineData;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.player.JavaFXMediaPlayer;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.DefaultLocator;
import cz.martlin.jmop.core.sources.locals.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.locals.DefaultLocalSource;
import cz.martlin.jmop.core.sources.locals.DefaultPlaylistLoader;
import cz.martlin.jmop.core.sources.remote.XXX_AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remotes.XXX_FFMPEGConverter;
import cz.martlin.jmop.core.sources.remotes.XXX_YoutubeDlDownloader;

public class DefaultJMOPPlayerBuilder extends SimpleJMOPPlayerBuilder {

	@Override
	public XXX_FFMPEGConverter createConverter(CommandlineData data, BaseConfiguration config, BaseLocalSource local) {
		return new XXX_FFMPEGConverter(local);
	}

	@Override
	public XXX_YoutubeDlDownloader createDownloader(CommandlineData data, BaseConfiguration config,
			InternetConnectionStatus connection, XXX_AbstractRemoteSource remote, BaseLocalSource local) {
		return new XXX_YoutubeDlDownloader(connection, local, remote);
	}

	@Override
	public JavaFXMediaPlayer createPlayer(CommandlineData data, BaseConfiguration config, BaseLocalSource local,
			AbstractTrackFileLocator locator) {
		return new JavaFXMediaPlayer(local, locator);
	}

	@Override
	public AbstractTrackFileLocator createLocator(CommandlineData data, BaseConfiguration config) {
		return new DefaultLocator(config);
	}

	@Override
	public BaseLocalSource createLocalSource(CommandlineData data, BaseConfiguration config) throws IOException {
		File root = data.getRoot();
		AbstractPlaylistLoader loader = new DefaultPlaylistLoader();
		BaseFilesNamer namer = new DefaultFilesNamer();

		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		return new DefaultLocalSource(config, fileSystem);
	}

	@Override
	public XXX_AbstractRemoteSource createRemoteSource(CommandlineData data, BaseConfiguration config,
			InternetConnectionStatus connection) {
		// TODO replace with Factory for SourceKind
		return null; //FIXME
	}

	@Override
	public InternetConnectionStatus createInternetConnectionStatus(CommandlineData data, BaseConfiguration config) {
		return new InternetConnectionStatus(config);
	}

	@Override
	public BaseConfiguration createConfiguration(CommandlineData data) {
		return new ConstantConfiguration();
	}

}
