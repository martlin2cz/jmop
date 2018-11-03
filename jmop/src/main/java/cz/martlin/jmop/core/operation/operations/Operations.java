package cz.martlin.jmop.core.operation.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.operation.base.BaseOperation;
import cz.martlin.jmop.core.operation.base.TwosetOperation;
import cz.martlin.jmop.core.operation.operations.TrackSearchOperation.SearchData;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.remote.BaseSourceConverter;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;

public class Operations {

	private final TwosetOperation<SearchData, Track, Track> searchAndLoad;
	private final TwosetOperation<Track, Track, Track> nextAndLoad;
	private final BaseOperation<Track, Track> load;

	public Operations(ErrorReporter reporter, BaseConfiguration config, AbstractTrackFileLocator locator,
			AbstractRemoteSource remote, BaseLocalSource local, BaseSourceDownloader downloader,
			BaseSourceConverter converter, BasePlayer player) {
		super();

		BaseOperation<SearchData, Track> search = new TrackSearchOperation(reporter, config, remote);
		BaseOperation<Track, Track> next = new NextTrackOperation(reporter, config, remote);
		BaseOperation<Track, Track> load = new TrackFilesLoadOperation(reporter, config, locator, local, downloader,
				converter, player);

		this.searchAndLoad = new TwosetOperation<>(search, load);
		this.nextAndLoad = new TwosetOperation<>(next, load);
		this.load = load;
	}

	public TwosetOperation<SearchData, Track, Track> searchAndLoadOperation() {
		return searchAndLoad;
	}

	public TwosetOperation<Track, Track, Track> nextAndLoadOperation() {
		return nextAndLoad;
	}

	public BaseOperation<Track, Track> loadOperation() {
		return load;
	}

}
