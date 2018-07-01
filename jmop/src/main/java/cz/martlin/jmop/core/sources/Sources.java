package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.tracks.Track;

public class Sources {
	private final BaseLocalSource local;
	private final BaseRemoteSource remote;

	public Sources(BaseLocalSource local, BaseRemoteSource remote) {
		super();
		this.local = local;
		this.remote = remote;
	}

	public void prepareNextOf(Track current) {
		try {
			Track next = remote.getNextOf(current);

			boolean contains = local.contains(next);
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
