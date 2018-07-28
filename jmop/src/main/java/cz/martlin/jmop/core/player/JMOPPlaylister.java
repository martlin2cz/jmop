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
	private final ObjectProperty<Track> previousTrackProperty;
	private final ObjectProperty<Track> nextTrackProperty;
	// private final TrackPlayedHandler playerHandler;

	// TODO shuffle?
	private BetterPlaylistRuntime playlist;

	public JMOPPlaylister(AbstractPlayer player, TrackPreparer preparer,
			InternetConnectionStatus connection) {
		super();
		this.player = player;
		this.connection = connection;
		this.online = new OnlinePlaylister(preparer, this, connection);
		this.offline = new OfflinePlaylister();
		this.currentTrackProperty = new SimpleObjectProperty<>();
		this.previousTrackProperty = new SimpleObjectProperty<>();
		this.nextTrackProperty = new SimpleObjectProperty<>();
		// this.playerHandler = playerHandler;

		this.playlist = null;
	}

	public BetterPlaylistRuntime getPlaylist() {
		return playlist;
	}

	public void setPlaylist(BetterPlaylistRuntime playlist) {
		this.playlist = playlist;

		this.online.setPlaylist(playlist);
		this.offline.setPlaylist(playlist);
		// this.playerHandler.setPlaylist(playlist);

	}

	public ObjectProperty<Track> currentTrackProperty() {
		return currentTrackProperty;
	}

	public ObjectProperty<Track> previousTrackProperty() {
		return previousTrackProperty;
	}

	public ObjectProperty<Track> nextTrackProperty() {
		return nextTrackProperty;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void play() {
		LOG.info("Plaing");

		Track track = playlist.startToPlay();

		updateProperties();
		player.startPlayling(track);
	}

	public void stop() {
		LOG.info("Stopping");

		updateProperties();
		player.stop();
	}

	public void toNext() {
		LOG.info("To next");

		BasePlaylister playlister = getPlaylisterStrategy();
		Track track = playlister.next();

		updateProperties();
		player.startPlayling(track);
	}

	public void toPrevious() {
		LOG.info("To previous");

		BasePlaylister playlister = getPlaylisterStrategy();
		Track track = playlister.previous();

		updateProperties();
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

	private void updateProperties() {
		Track current = playlist.getCurrentlyPlayed();
		currentTrackProperty.set(current);

		//TODO try to use menubar :)
		
		
		Track next = playlist.getNextToPlayOrNull();
		nextTrackProperty.set(next);

		Track previous = playlist.getLastPlayedOrNull();
		previousTrackProperty.set(previous);
	}

	public void appendTrack(Track track) {
		playlist.append(track);
		//TODO save playlist here ...
		
		updateProperties();
	
	}

}
