package cz.martlin.jmop.core.player.caching;

import java.util.concurrent.TimeUnit;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

public class TestingCachingManager extends AbstractCachingManager {

	private final BaseErrorReporter reporter;
	private final int seconds;

	public TestingCachingManager(BaseErrorReporter reporter, int seconds) {
		super();
		this.reporter = reporter;
		this.seconds = seconds;

	}

	@Override
	public synchronized boolean isCached(Track track) {
		boolean cached = (this.seconds == 0);
		LOG.info("Is track {} cached? {}", track.getTitle(), cached);
		return cached;
	}

	@Override
	protected void doStartCaching(Track track) {
		Runnable run = new TestingCachingTreadRunnable(track);
		Thread thread = new Thread(run, "Caching " + track.getTitle() + " thread");

		thread.start();
	}

	public class TestingCachingTreadRunnable implements Runnable {

		private final Track track;

		public TestingCachingTreadRunnable(Track track) {
			this.track = track;
		}

		@Override
		public void run() {
			LOG.info("Caching of track {} started.", track.getTitle());

			try {
				TimeUnit.SECONDS.sleep(seconds);
			} catch (InterruptedException e) {
				reporter.report(e);
			}

			LOG.info("Caching of track {} ended.", track.getTitle());
			cachingDone(track);
		}
	}
}
