package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;

//@FunctionalInterface
@Deprecated
public interface TrackPlayedHandler {
	public void trackPlayed(Track track);

//	public void setPlaylist(BetterPlaylistRuntime playlist);
}
