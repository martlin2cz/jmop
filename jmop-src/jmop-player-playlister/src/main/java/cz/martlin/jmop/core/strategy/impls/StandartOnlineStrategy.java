package cz.martlin.jmop.core.strategy.impls;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ops.BaseOperations;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;
import cz.martlin.jmop.core.strategy.base.AbstractNextOnlineLoadingStrategy;

/**
 * The standart online playlister. Plays tracks from playlist, when there is
 * less than one remaining, automatically starts to load new. The new track is
 * beeing appended after the next one.
 * 
 * @author martin
 *
 */
public class StandartOnlineStrategy extends AbstractNextOnlineLoadingStrategy {

	public StandartOnlineStrategy(BaseOperations operations) {
		super(operations);
	}

	@Override
	public void addTrack(Track track) {
		PlaylistRuntime runtime = getRuntime();
		runtime.append(track);
	}

}
