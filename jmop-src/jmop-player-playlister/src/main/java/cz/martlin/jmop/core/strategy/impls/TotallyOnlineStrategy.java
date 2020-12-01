package cz.martlin.jmop.core.strategy.impls;

import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.ops.BaseOperations;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;
import cz.martlin.jmop.core.strategy.base.AbstractNextOnlineLoadingStrategy;

/**
 * The playlister which plays allways online. There is nothing like playlist for
 * him, at every single moment the next track is allways loaded as next of the
 * current one. When some are skipped, the've been replaced too.
 * 
 * @author martin
 *
 */
public class TotallyOnlineStrategy extends AbstractNextOnlineLoadingStrategy {

	public TotallyOnlineStrategy(BaseOperations operations) {
		super(operations);
	}

	@Override
	public void addTrack(Track track) {
		PlaylistRuntime runtime = getRuntime();
		runtime.replaceRest(track);
	}

}
