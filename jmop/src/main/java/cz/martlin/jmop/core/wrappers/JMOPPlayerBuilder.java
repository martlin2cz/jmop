package cz.martlin.jmop.core.wrappers;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.config.Configuration;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.player.JMOPPlaylisterWithGui;
import cz.martlin.jmop.core.player.TrackPreparer;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.AutomaticSavesPerformer;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.FFMPEGConverter;
import cz.martlin.jmop.core.sources.download.YoutubeDlDownloader;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.DefaultFilesNamer;
import cz.martlin.jmop.core.sources.local.DefaultLocalSource;
import cz.martlin.jmop.core.sources.local.DefaultPlaylistLoader;
import cz.martlin.jmop.core.sources.local.PlaylistLoader;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.DefaultLocator;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import cz.martlin.jmop.gui.util.JavaFXMediaPlayer;

public class JMOPPlayerBuilder {
	public static JMOPPlayer create(GuiDescriptor gui, File root, Playlist playlistToPlayOrNot) throws IOException {
		Configuration config = new Configuration();

		PlaylistLoader loader = new DefaultPlaylistLoader();
		BaseFilesNamer namer = new DefaultFilesNamer();
		InternetConnectionStatus connection = new InternetConnectionStatus();
		AbstractRemoteSource remote = new YoutubeSource();

		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(fileSystem);
		AutomaticSavesPerformer saver = new AutomaticSavesPerformer(local);

		BaseSourceDownloader downloader = new YoutubeDlDownloader(local, remote);
		BaseSourceConverter converter = new FFMPEGConverter(local);
		AbstractTrackFileLocator locatorDepended = null; //XXX FIXME dependencies cycle
		BasePlayer player = new JavaFXMediaPlayer(local, locatorDepended);

		AbstractTrackFileLocator locator = new DefaultLocator(config, downloader, player);
		TrackPreparer preparer = new TrackPreparer(config, remote, local, locator, converter, downloader, player, saver, gui);
		JMOPPlaylisterWithGui playlister = new JMOPPlaylisterWithGui(player, preparer, connection, saver);

		ToPlaylistAppendingHandler trackPlayedHandler = new ToPlaylistAppendingHandler(playlister);
		player.setHandler(trackPlayedHandler);

		return new JMOPPlayer(remote, local, downloader, converter, gui, playlistToPlayOrNot, playlister, preparer,
				saver);
	}
}
