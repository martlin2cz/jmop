package cz.martlin.jmop.core.operation.operations;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ErrorReporter;
import cz.martlin.jmop.core.operation.base.TwosetOperation;
import cz.martlin.jmop.core.operation.operations.TrackSearchOperation.SearchData;
import cz.martlin.jmop.core.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseSourceConverter;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;

/**
 * An utility class holding all the operations needed in the JMOP.
 * 
 * @author martin
 *
 */
public class Operations {

	private final TwosetOperation<SearchData, Track, Track> searchAndLoad;
	private final TwosetOperation<Track, Track, Track> nextAndLoad;
	private final TrackFilesLoadOperation load;

	public Operations(ErrorReporter reporter, BaseConfiguration config, AbstractTrackFileLocator locator,
			AbstractRemoteSource remote, BaseLocalSource local, BaseSourceDownloader downloader,
			BaseSourceConverter converter, BasePlayer player) {
		super();

		TrackSearchOperation search = new TrackSearchOperation(reporter, config, remote);
		NextTrackOperation next = new NextTrackOperation(reporter, config, remote);
		TrackFilesLoadOperation load = new TrackFilesLoadOperation(reporter, config, locator, local, downloader,
				converter, player);

		this.searchAndLoad = new TwosetOperation<>(search, load);
		this.nextAndLoad = new TwosetOperation<>(next, load);
		this.load = load;
	}

	/**
	 * Returns the "search and load such track" operation.
	 * 
	 * @return
	 */
	public TwosetOperation<SearchData, Track, Track> searchAndLoadOperation() {
		return searchAndLoad;
	}

	/**
	 * Returns the "get next and load such track" operation.
	 * 
	 * @return
	 */
	public TwosetOperation<Track, Track, Track> nextAndLoadOperation() {
		return nextAndLoad;
	}

	/**
	 * Returns "load given track" operation.
	 * 
	 * @return
	 */
	public TrackFilesLoadOperation loadOperation() {
		return load;
	}

}
