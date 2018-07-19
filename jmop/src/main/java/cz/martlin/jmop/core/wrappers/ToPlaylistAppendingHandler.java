package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.BetterPlaylistRuntime;
import cz.martlin.jmop.core.player.NextTrackPreparer;
import cz.martlin.jmop.core.player.TrackPlayedHandler;

public class ToPlaylistAppendingHandler implements TrackPlayedHandler {
	
	private final NextTrackPreparer preparer;
	
	private BetterPlaylistRuntime playlist;
	
	public ToPlaylistAppendingHandler(NextTrackPreparer preparer) {
		this.preparer = preparer;
	}
	
	public void setPlaylist(BetterPlaylistRuntime playlist) {
		this.playlist = playlist;
	}

	@Override
	public void trackPlayed(Track track) {
		try {
			preparer.prepreAndAppend(track, playlist);
		} catch (JMOPSourceException e) {
			//TODO exception
			e.printStackTrace();
		}

	}

}