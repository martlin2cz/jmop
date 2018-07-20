package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.BetterPlaylistRuntime;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.wrappers.JMOPSources;
import javafx.event.EventType;

@Deprecated
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
	/***
	 * Use {@link JMOPSources#download(Track, java.util.function.Consumer)} instead.
	 * @param track
	 * @param playlist
	 */
	@Deprecated
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

	/***
	 * Use {@link JMOPSources#download(Track, java.util.function.Consumer)} instead.
	 * @param track
	 * @param playlist
	 */
	@Deprecated
	//TODO just debug, make privat back !
	public void startDownloading(Track track, BetterPlaylistRuntime playlist) {
		DownloaderTask task = new DownloaderTask(downloader, converter,  track);

		task.addEventHandler(EventType.ROOT, (e) -> {
			playlist.append(track);
		});

		task.run();
		
		// TODO running, message and progress properties - handle somehow
		// TODO run in background, unccomment:
		// Thread thread = new Thread(task, "DownloaderTask");
		// thread.start();
	}

	// TODO ...
}
