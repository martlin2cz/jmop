package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.JMOPPlaylisterWithGui;
import cz.martlin.jmop.core.player.TrackPlayedHandler;
@Deprecated
public class ToPlaylistAppendingHandler implements TrackPlayedHandler {

	// private final NextTrackPreparer preparer;

	// private BetterPlaylistRuntime playlist;

	private final JMOPPlaylisterWithGui playlister;

	// public void setPlaylist(BetterPlaylistRuntime playlist) {
	// this.playlist = playlist;
	// }

	public ToPlaylistAppendingHandler(JMOPPlaylisterWithGui playlister) {
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