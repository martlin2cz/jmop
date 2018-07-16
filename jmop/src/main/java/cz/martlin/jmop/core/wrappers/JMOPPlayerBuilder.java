package cz.martlin.jmop.core.wrappers;

import java.io.File;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.player.AbstractPlayer;
import cz.martlin.jmop.core.player.JMOPPlaylister;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.Sources;
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

		ProgressListener listener = gui.getProgressListener();
		BaseSourceDownloader downloader = new YoutubeDlDownloader(local, remote, listener);
		TrackFileFormat inputFormat = YoutubeDlDownloader.DOWNLOAD_FILE_FORMAT;
		TrackFileFormat outputFormat = JavaFXMediaPlayer.LOCAL_FORMAT;
		BaseSourceConverter converter = new FFMPEGConverter(local, inputFormat, outputFormat, listener);
		InternetConnectionStatus connection = new InternetConnectionStatus();
		AbstractPlayer player = new JavaFXMediaPlayer(local);
		Sources sources = new Sources(local, remote, downloader, converter);
		JMOPPlaylister playlister = new JMOPPlaylister(player, sources, connection);

		return new JMOPPlayer(remote, local, downloader, converter, gui, playlistToPlayOrNot, playlister);
	}
}
