package cz.martlin.jmop.core.playlister.playlisters;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.playlister.base.AbstractNextOnlineLoadingPlaylister;
import cz.martlin.jmop.core.preparer.TrackPreparer;

/**
 * The standart online playlister. Plays tracks from playlist, when there is
 * less than one remaining, automatically starts to load new. The new track is
 * beeing appended after the next one.
 * 
 * @author martin
 *
 */
public class StandartOnlinePlaylister extends AbstractNextOnlineLoadingPlaylister {

	public StandartOnlinePlaylister(TrackPreparer preparer) {
		super(preparer);
	}

	@Override
	public void addTrack(Track track) {
		PlaylistRuntime runtime = getRuntime();
		runtime.append(track);
	}

}
