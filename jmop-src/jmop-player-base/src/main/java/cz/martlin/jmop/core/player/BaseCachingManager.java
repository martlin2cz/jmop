package cz.martlin.jmop.core.player;

import java.util.function.Consumer;

import cz.martlin.jmop.common.data.model.Track;

public interface BaseCachingManager {

	boolean isCached(Track track);

	boolean isCaching(Track track);

	void startCaching(Track track, Consumer<Track> onCached);

}
