package cz.martlin.jmop.core.preparer;

import java.util.function.Consumer;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.playlister.Playlister;
import cz.martlin.jmop.core.preparer.operations.BaseTrackOperation;
import cz.martlin.jmop.core.preparer.operations.DownloadAndConvertOperation;
import cz.martlin.jmop.core.preparer.operations.NextTrackLoadInstance;
import cz.martlin.jmop.core.preparer.operations.TrackQueryInstance;
import cz.martlin.jmop.core.preparer.operations.TrackQueryInstance.SearchData;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;

public class TrackPreparer {

	private final DownloadAndConvertOperation files;
	private final TrackQueryInstance query;
	private final NextTrackLoadInstance nexts;

	public TrackPreparer(BaseConfiguration config, AbstractRemoteSource remote, BaseLocalSource local,
			AbstractTrackFileLocator locator, BaseSourceDownloader downloader, BaseSourceConverter converter,
			BasePlayer player) {

		this.files = new DownloadAndConvertOperation(config, locator, local, downloader, converter, player);

		this.query = new TrackQueryInstance(config, remote, locator, local, downloader, converter, player);

		this.nexts = new NextTrackLoadInstance(config, remote, locator, local, downloader, converter, player);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void startSearchAndLoad(Bundle bundle, String query, Playlister addTo) {
		SearchData data = new SearchData(bundle, query);
		runInBackground(this.query, data, (t) -> appendAndStartPlaying(t, addTo));
	}

	public void startSearchAndLoad(Bundle bundle, String query, Consumer<Track> onLoaded) {
		SearchData data = new SearchData(bundle, query);
		runInBackground(this.query, data, (t) -> onLoaded.accept(t));
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void startLoadingNextOf(Track track, Playlister addTo) {
		runInBackground(this.nexts, track, (t) -> append(t, addTo));
	}

	public void startLoadingNextOf(Track track, Consumer<Track> onLoaded) {
		runInBackground(this.nexts, track, (t) -> onLoaded.accept(t));
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void checkAndStartLoadingTrack(Track track, Playlister addTo) {
		runInBackground(this.files, track, (t) -> append(t, addTo));
	}

	public void checkAndStartLoadingTrack(Track track, Consumer<Track> onLoaded) {
		runInBackground(this.files, track, (t) -> onLoaded.accept(t));
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void appendAndStartPlaying(Track track, Playlister addTo) {
		try {
			addTo.add(track);
			addTo.playNext();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO error handler
		}
	}

	private void append(Track track, Playlister addTo) {
		try {
			addTo.add(track);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO error handler
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private static <IT, OT> void runInBackground(BaseTrackOperation<IT, OT> operation, IT data,
			Consumer<OT> onCompleted) {

		PreparationInstanceTask<IT, OT> task = new PreparationInstanceTask<>(operation, data);

		task.setOnSucceeded((e) -> onCompleted.accept(task.getValue()));

		// TODO running tasks . add ( task )

		Thread thr = new Thread(task, "TrackOperationTaskThread");
		thr.start();
	}
	/////////////////////////////////////////////////////////////////////////////////////
}
