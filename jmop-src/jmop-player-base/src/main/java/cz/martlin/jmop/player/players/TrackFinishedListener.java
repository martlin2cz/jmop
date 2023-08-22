package cz.martlin.jmop.player.players;

import cz.martlin.jmop.common.data.model.Track;

/**
 * The handler of played track. Gets triggered by player, and the target is
 * supposed to ... I guess start playing next track or anything.
 * 
 * @author martin
 *
 */
public interface TrackFinishedListener {

	/**
	 * The track is played. Do something.
	 * 
	 * @param track
	 */
	public void trackOver(Track track);
}
