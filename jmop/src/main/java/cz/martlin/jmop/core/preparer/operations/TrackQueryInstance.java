package cz.martlin.jmop.core.preparer.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.misc.TextualStatusUpdateListener;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.preparer.operations.TrackQueryInstance.SearchData;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;

public class TrackQueryInstance extends BaseTrackOperation<SearchData, Track> {

	private final AbstractRemoteSource remote;
	private final DownloadAndConvertOperation downloadAndConvert;

	public TrackQueryInstance(BaseConfiguration config, AbstractRemoteSource remote, AbstractTrackFileLocator locator, BaseLocalSource local,
			BaseSourceDownloader downloader, BaseSourceConverter converter, BasePlayer player) {

		super("Track query");
		this.remote = remote;
		this.downloadAndConvert = new DownloadAndConvertOperation(config, locator, local, downloader, converter, player);
		
	}

	@Override
	protected Track runInternal(SearchData input, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) throws Exception {

		Bundle bundle = input.getBundle();
		String query = input.getQuery();

		Track track = search(bundle, query, progressListener, statusListener);

		return downloadAndConvert.runInternal(track, progressListener, statusListener);
	}

	private Track search(Bundle bundle, String query, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) throws JMOPSourceException {

		startSubTask("Searching ...", progressListener, statusListener);

		Track track = remote.search(bundle, query);

		return track;
	}

	public static class SearchData {

		private final Bundle bundle;
		private final String query;

		public SearchData(Bundle bundle, String query) {
			this.bundle = bundle;
			this.query = query;
		}

		public Bundle getBundle() {
			return bundle;
		}

		public String getQuery() {
			return query;
		}

	}

}
