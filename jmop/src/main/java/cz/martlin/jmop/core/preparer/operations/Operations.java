package cz.martlin.jmop.core.preparer.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.preparer.operations.TrackSearchOperation.SearchData;
import cz.martlin.jmop.core.preparer.operations.base.BaseOperation;
import cz.martlin.jmop.core.preparer.operations.base.TwosetOperation;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;

public class Operations {

	private final TwosetOperation<SearchData, Track, Track> searchAndLoad;
	private final TwosetOperation<Track, Track, Track> nextAndLoad;
	private final BaseOperation<Track, Track> load;

	public Operations(BaseConfiguration config, AbstractTrackFileLocator locator, AbstractRemoteSource remote,
			BaseLocalSource local, BaseSourceDownloader downloader, BaseSourceConverter converter, BasePlayer player) {
		super();

		BaseOperation<SearchData, Track> search = new TrackSearchOperation(config, remote);
		BaseOperation<Track, Track> next = new NextTrackOperation(config, remote);
		BaseOperation<Track, Track> load = new TrackFilesLoadOperation(config, locator, local, downloader,
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
