package cz.martlin.jmop.core.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class JMOPPlaylister {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final AbstractPlayer player;
	private final InternetConnectionStatus connection;
	private final OnlinePlaylister online;
	private final OfflinePlaylister offline;
	private final ObjectProperty<Track> currentTrackProperty;
//	private final TrackPlayedHandler playerHandler;

	// TODO shuffle?
	private BetterPlaylistRuntime playlist;

	

	public JMOPPlaylister(AbstractPlayer player, TrackPreparer preparer, InternetConnectionStatus connection/*, TrackPlayedHandler playerHandler*/) {
		super();
		this.player = player;
		this.connection = connection;
		this.online = new OnlinePlaylister(preparer);
		this.offline = new OfflinePlaylister();
		this.currentTrackProperty = new SimpleObjectProperty<>();
//		this.playerHandler = playerHandler;

		this.playlist = null;
	}

	public BetterPlaylistRuntime getPlaylist() {
		return playlist;
	}

	public void setPlaylist(BetterPlaylistRuntime playlist) {
		this.playlist = playlist;

		this.online.setPlaylist(playlist);
		this.offline.setPlaylist(playlist);
//		this.playerHandler.setPlaylist(playlist);

	}
	
	public ObjectProperty<Track> currentTrackProperty() {
		return currentTrackProperty;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void play() {
		LOG.info("Plaing");

		Track track = playlist.startToPlay();
		
		currentTrackProperty.set(track);
		player.startPlayling(track);
	}

	public void stop() {
		LOG.info("Stopping");
		
		currentTrackProperty.set(null);
		player.stop();
	}

	public void toNext() {
		LOG.info("To next");

		BasePlaylister playlister = getPlaylisterStrategy();
		Track track = playlister.next();
		
		currentTrackProperty.set(track);
		player.startPlayling(track);
	}

	public void toPrevious() {
		LOG.info("To previous");

		BasePlaylister playlister = getPlaylisterStrategy();
		Track track = playlister.previous();

		currentTrackProperty.set(track);
		player.startPlayling(track);
	}

	public void pause() {
		LOG.info("Paused");
		player.pause();
	}

	public void resume() {
		LOG.info("Resumed");
		player.resume();
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
