package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.AbstractPlayer;
import cz.martlin.jmop.core.player.BasePlaylister;
import cz.martlin.jmop.core.player.BasicPlaylistRuntime;
import cz.martlin.jmop.core.player.BetterPlaylistRuntime;
import cz.martlin.jmop.core.player.JMOPPlaylister;
import cz.martlin.jmop.core.player.NextTrackPreparer;
import cz.martlin.jmop.core.player.TrackPlayedHandler;

public class ToPlaylistAppendingHandler implements TrackPlayedHandler {

	// private final NextTrackPreparer preparer;

	// private BetterPlaylistRuntime playlist;

	private final JMOPPlaylister playlister;

	// public void setPlaylist(BetterPlaylistRuntime playlist) {
	// this.playlist = playlist;
	// }

	public ToPlaylistAppendingHandler(JMOPPlaylister playlister) {
		this.playlister = playlister;
	}

	@Override
	public void trackPlayed(Track track) {
		// try {
		playlister.toNext();
		// } catch (JMOPSourceException e) {
		// //TODO exception
		// e.printStackTrace();
		// }

	}

}