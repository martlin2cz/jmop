package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.BetterPlaylistRuntime;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import javafx.event.EventType;

public class Sources {
	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;
	private final BaseSourceDownloader downloader;
	private final BaseSourceConverter converter;

	public Sources(BaseLocalSource local, AbstractRemoteSource remote, BaseSourceDownloader downloader,
			BaseSourceConverter converter) {
		super();
		this.local = local;
		this.remote = remote;
		this.downloader = downloader;
		this.converter = converter;
	}

	public BaseLocalSource getLocal() {
		return local;
	}

	public AbstractRemoteSource getRemote() {
		return remote;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void prepareNextOf(Track current, BetterPlaylistRuntime playlist) {
		try {
			Track next = remote.getNextTrackOf(current);

			boolean contains = local.exists(next);
			if (!contains) {
				startDownloading(next, playlist);
			}
		} catch (Exception e) {
			// TODO report error
			e.printStackTrace();
		}
	}

	private void startDownloading(Track track, BetterPlaylistRuntime playlist) {
		DownloaderTask task = new DownloaderTask(downloader, converter, track);
	
		task.addEventHandler(EventType.ROOT, (e) -> {
			playlist.append(track);
		});

		// TODO running, message and progress properties - handle somehow
		task.run();
	}

	// TODO ...
}
