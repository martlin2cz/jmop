package cz.martlin.jmop.core.preparer.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.misc.TextualStatusUpdateListener;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;

public class NextTrackLoadInstance extends BaseTrackOperation<Track, Track> {
	private final AbstractRemoteSource remote;
	private final DownloadAndConvertOperation downloadAndConvert;

	public NextTrackLoadInstance(BaseConfiguration config, AbstractRemoteSource remote,
			AbstractTrackFileLocator locator, BaseLocalSource local, BaseSourceDownloader downloader,
			BaseSourceConverter converter, BasePlayer player) {

		super("Next track");
		this.remote = remote;
		this.downloadAndConvert = new DownloadAndConvertOperation(config, locator, local, downloader, converter,
				player);

	}

	@Override
	protected Track runInternal(Track input, ProgressListener progressListener,
			TextualStatusUpdateListener statusListener) throws Exception {

		Track track = loadNext(input, progressListener, statusListener);

		return downloadAndConvert.runInternal(track, progressListener, statusListener);
	}

	private Track loadNext(Track track, ProgressListener progressListener, TextualStatusUpdateListener statusListener)
			throws JMOPSourceException {

		startSubTask("Loading next ...", progressListener, statusListener);

		Track next = remote.getNextTrackOf(track);

		return next;
	}
}
