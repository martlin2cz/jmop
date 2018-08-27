package cz.martlin.jmop.core.wrappers;

import java.io.File;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.player.ConvertingPlayer;
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
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remotes.YoutubeSource;
import cz.martlin.jmop.gui.util.JavaFXMediaPlayer;

public class JMOPPlayerBuilder {
	public static JMOPPlayer create(GuiDescriptor gui, File root, Playlist playlistToPlayOrNot) {

		PlaylistLoader loader = new DefaultPlaylistLoader();
		BaseFilesNamer namer = new DefaultFilesNamer();

		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(root, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(fileSystem);
		AbstractRemoteSource remote = new YoutubeSource();

		BaseSourceDownloader downloader = new YoutubeDlDownloader(local, remote);
		TrackFileFormat inputFormat = YoutubeDlDownloader.DOWNLOAD_FILE_FORMAT;
		TrackFileFormat outputFormat = DefaultLocalSource.MAIN_STORE_FORMAT;
		boolean deleteOriginal = false;
		BaseSourceConverter converter = new FFMPEGConverter(local, inputFormat, outputFormat, deleteOriginal);
		InternetConnectionStatus connection = new InternetConnectionStatus();
		AutomaticSavesPerformer saver = new AutomaticSavesPerformer(local);
		TrackPreparer preparer = new TrackPreparer(remote, local, converter, downloader, saver, gui);

		BasePlayer player = new JavaFXMediaPlayer(local);
		TrackFileFormat formatOfWrapped = JavaFXMediaPlayer.PLAYER_FORMAT;
		BasePlayer convertingPlayer = new ConvertingPlayer(local, player, formatOfWrapped );
		JMOPPlaylisterWithGui playlister = new JMOPPlaylisterWithGui(convertingPlayer, preparer, connection, saver);

		ToPlaylistAppendingHandler trackPlayedHandler = new ToPlaylistAppendingHandler(playlister);
		player.setHandler(trackPlayedHandler);

		return new JMOPPlayer(remote, local, downloader, converter, gui, playlistToPlayOrNot, playlister, preparer, saver);
	}
}
