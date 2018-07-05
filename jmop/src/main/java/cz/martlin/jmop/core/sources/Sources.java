package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.tracks.Track;

public class Sources {
	private final BaseLocalSource local;
	private final AbstractRemoteSource remote;

	public Sources(BaseLocalSource local, AbstractRemoteSource remote) {
		super();
		this.local = local;
		this.remote = remote;
	}

	public BaseLocalSource getLocal() {
		return local;
	}

	public AbstractRemoteSource getRemote() {
		return remote;
	}

	public void prepareNextOf(Track current) {
		try {
			Track next = remote.getNextTrackOf(current);

			boolean contains = local.exists(next);
			if (!contains) {
				startDownloading(next);
			}
		} catch (Exception e) {
			// TODO report error
		}
	}

	private void startDownloading(Track next) {
		// TODO start download task

	}

	// TODO ...
}
