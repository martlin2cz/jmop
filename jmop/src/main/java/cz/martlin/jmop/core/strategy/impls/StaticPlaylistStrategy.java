package cz.martlin.jmop.core.strategy.impls;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;
import cz.martlin.jmop.core.strategy.base.SimplePlaylisterStrategy;

/**
 * Playlister completelly follows the playlist (i.e. runtime). 
 * @author martin
 *
 */
public class StaticPlaylistStrategy extends SimplePlaylisterStrategy {

	public StaticPlaylistStrategy() {
		super();
	}

	@Override
	public void addTrack(Track track) {
		PlaylistRuntime runtime = getRuntime();
		
		runtime.append(track);
	}

}
