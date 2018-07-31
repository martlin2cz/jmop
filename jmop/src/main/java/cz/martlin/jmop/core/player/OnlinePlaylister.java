package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class OnlinePlaylister implements BasePlaylister {
	private final TrackPreparer preparer;
	private final JMOPPlaylister playlister;
	private final InternetConnectionStatus connection;

	private BetterPlaylistRuntime playlist;

	public OnlinePlaylister(TrackPreparer preparer, JMOPPlaylister playlister, InternetConnectionStatus connection) {
		super();
		this.preparer = preparer;
		this.playlister = playlister;
		this.connection = connection;
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
		// TODO shouldňt here overrride the rest of playlist
		// instead of appending to its end?
		Track next = playlist.toNextOrAnother();

		try {
			preparer.prepreNextAndAppend(next, playlister);
		} catch (JMOPSourceException e) {

			connection.markOffline();

			// TODO handle error
			e.printStackTrace();
		}

		return next;
	}

}
