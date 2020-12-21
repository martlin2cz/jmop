package cz.martlin.jmop.core.player.caching;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.player.BaseCachingManager;

public abstract class AbstractCachingManager implements BaseCachingManager {
	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	protected final Map<Track, Consumer<Track>> onCached;

	public AbstractCachingManager() {
		super();
		this.onCached = new HashMap<>();
	}

	@Override
	public synchronized boolean isCaching(Track track) {
		boolean caching = this.onCached.containsKey(track);
		LOG.info("Is track {} beeing currently cached? {}", track.getTitle(), caching);
		return caching;
	}

	@Override
	public synchronized void whenCached(Track track, Consumer<Track> onCached) {
		LOG.info("Updated what to do when track done {}.", track.getTitle());

		Consumer<Track> onCacheds = this.onCached.get(track);
		Consumer<Track> newOnCacheds = onCacheds.andThen(onCached);
		this.onCached.put(track, newOnCacheds);
	}

	@Override
	public synchronized void startCaching(Track track, Consumer<Track> onCached) {
		LOG.info("Starting to cache track {}.", track.getTitle());

		doStartCaching(track);

		cachingStarted(track, onCached);
	}

	protected abstract void doStartCaching(Track track);

	private synchronized void cachingStarted(Track track, Consumer<Track> onCached) {
		LOG.info("Track {} caching started.", track.getTitle());

		this.onCached.put(track, onCached);
	}

	protected synchronized void cachingDone(Track track) {
		LOG.info("Track {} caching done.", track.getTitle());

		Consumer<Track> onCached = this.onCached.remove(track);
		onCached.accept(track);
	}

}