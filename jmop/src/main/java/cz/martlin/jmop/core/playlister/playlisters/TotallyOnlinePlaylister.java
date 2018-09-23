package cz.martlin.jmop.core.playlister.playlisters;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.playlister.base.AbstractNextOnlineLoadingPlaylister;
import cz.martlin.jmop.core.preparer.TrackPreparer;

/**
 * The playlister which plays allways online. There is nothing like playlist for
 * him, at every single moment the next track is allways loaded as next of the
 * current one. When some are skipped, the've been replaced too.
 * 
 * @author martin
 *
 */
public class TotallyOnlinePlaylister extends AbstractNextOnlineLoadingPlaylister {

	public TotallyOnlinePlaylister(TrackPreparer preparer) {
		super(preparer);
	}

	@Override
	public void addTrack(Track track) {
		PlaylistRuntime runtime = getRuntime();
		runtime.replaceRest(track);
	}

}
