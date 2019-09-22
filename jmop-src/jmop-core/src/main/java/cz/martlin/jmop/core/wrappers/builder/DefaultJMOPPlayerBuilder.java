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
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.BaseTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.DefaultLocator;
import cz.martlin.jmop.core.sources.locals.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.locals.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.locals.DefaultLocalSource;
import cz.martlin.jmop.core.sources.locals.DefaultPlaylistLoader;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.core.sources.remote.ffmpeg.FFMPEGConverter;
import cz.martlin.jmop.core.sources.remote.youtube.YoutubeQuerier;
import cz.martlin.jmop.core.sources.remote.youtubedl.YoutubeDLDownloader;

public class DefaultJMOPPlayerBuilder extends SimpleJMOPPlayerBuilder {

	@Override
	public BaseConverter createConverter(CommandlineData data, BaseConfiguration config, XXX_BaseLocalSource local) {
		return new FFMPEGConverter(local);
	}

	@Override
	public BaseDownloader createDownloader(CommandlineData data, BaseConfiguration config,
			InternetConnectionStatus connection, AbstractRemoteSource remote, XXX_BaseLocalSource local) {
		BaseRemoteSourceQuerier querier = new YoutubeQuerier(config, connection); 
		//FIXME extract as param
		return new YoutubeDLDownloader(querier , local);
	}

	@Override
	public JavaFXMediaPlayer createPlayer(CommandlineData data, BaseConfiguration config, XXX_BaseLocalSource local,
			BaseTrackFileLocator locator) {
		return new JavaFXMediaPlayer(local, locator);
	}

	@Override
	public BaseTrackFileLocator createLocator(CommandlineData data, BaseConfiguration config) {
		return new DefaultLocator(config);
	}

	@Override
	public XXX_BaseLocalSource createLocalSource(CommandlineData data, BaseConfiguration config) throws IOException {
		File root = data.getRoot();
		AbstractPlaylistLoader loader = new DefaultPlaylistLoader();
		BaseFilesNamer namer = new DefaultFilesNamer();

		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		return new DefaultLocalSource(config, fileSystem);
	}

	@Override
	public  AbstractRemoteSource createRemoteSource(CommandlineData data, BaseConfiguration config,
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
