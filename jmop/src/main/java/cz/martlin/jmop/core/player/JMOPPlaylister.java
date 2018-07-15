package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.sources.Sources;

public class JMOPPlaylister {
	private final AbstractPlayer player;
	private final InternetConnectionStatus connection;
	private final OnlinePlaylister online;
	private final OfflinePlaylister offline;
	//TODO shuffle?
	private BetterPlaylistRuntime playlist;

	public JMOPPlaylister(AbstractPlayer player,  Sources sources,
			InternetConnectionStatus connection) {
		super();
		this.player = player;
		this.connection = connection;
		this.online = new OnlinePlaylister(sources, playlist);
		this.offline = new OfflinePlaylister(playlist);
		this.playlist = null;
	}
	
	public BetterPlaylistRuntime getPlaylist() {
		return playlist;
	}

	public void setPlaylist(BetterPlaylistRuntime playlist) {
		this.playlist = playlist;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	public void play() {
		Track track = playlist.startToPlay();
		player.play(track);
	}

	public void stop() {
		player.stop();
	}

	public void toNext() {
		BasePlaylister playlister = getPlaylisterStrategy();
		Track track = playlister.next();

		player.play(track);
	}

	public void toPrevious() {
		BasePlaylister playlister = getPlaylisterStrategy();
		Track track = playlister.previous();

		player.play(track);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private BasePlaylister getPlaylisterStrategy() {
		boolean isOffline = connection.isOffline();

		if (isOffline) {
			return offline;
		} else {
			return online;
		}
	}
}
