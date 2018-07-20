package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class OnlinePlaylister implements BasePlaylister {
	private final TrackPreparer preparer;
	
	private BetterPlaylistRuntime playlist;

	public OnlinePlaylister(TrackPreparer preparer) {
		super();
		this.preparer = preparer;
	}

	@Override
	public void setPlaylist(BetterPlaylistRuntime playlist) {
		this.playlist = playlist;
	}

	@Override
	public Track previous() {
		return playlist.toPreviousOrAnother();
	}

	@Override
	public Track next() {
		Track current = playlist.getCurrentlyPlayed();

		try {
			preparer.prepreNextAndAppend(current, playlist);
		} catch (JMOPSourceException e) {
			// TODO handle error
			e.printStackTrace();
		}

		return playlist.toNextOrAnother();
	}

}
