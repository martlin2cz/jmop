package cz.martlin.jmop.core.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.AutomaticSavesPerformer;
import javafx.util.Duration;

public class JMOPPlaylister {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final BasePlayer player;
	private final InternetConnectionStatus connection;
	private final OnlinePlaylister online;
	private final OfflinePlaylister offline;
	private final AutomaticSavesPerformer saver;
	private BetterPlaylistRuntime playlist;

	public JMOPPlaylister(BasePlayer player, TrackPreparer preparer, InternetConnectionStatus connection,
			AutomaticSavesPerformer saver) {
		super();

		this.player = player;
		this.connection = connection;
		this.online = new OnlinePlaylister(preparer, this, connection);
		this.offline = new OfflinePlaylister();
		this.saver = saver;
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
	
	public BasePlayer getPlayer() {
		return player;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void play() {
		LOG.info("Plaing");

		Track track = playlist.startToPlay();

		startPlayingWithExceptionCheck(track);
	}

	public void stop() {
		LOG.info("Stopping");

		player.stop();
	}

	public void toNext() {
		LOG.info("To next");

		BasePlaylister playlister = getPlaylisterStrategy();
		Track track = playlister.next();

		startPlayingWithExceptionCheck(track);
	}

	public void toPrevious() {
		LOG.info("To previous");

		BasePlaylister playlister = getPlaylisterStrategy();
		Track track = playlister.previous();

		startPlayingWithExceptionCheck(track);
	}

	public void pause() {
		LOG.info("Paused");

		player.pause();
	}

	public void resume() {
		LOG.info("Resumed");

		player.resume();
	}
	

	public void seek(Duration to) {
		LOG.info("Seeking");

		player.seek(to);
	}

	public void appendTrack(Track track) {
		playlist.append(track);
		saver.saveCurrentPlaylist();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void startPlayingWithExceptionCheck(Track track) {
		try {
			player.startPlayling(track);
		} catch (JMOPSourceException e) {
			// TODO exception
			e.printStackTrace();
		}
	}

	private BasePlaylister getPlaylisterStrategy() {
		boolean isOffline = connection.isOffline();

		if (isOffline) {
			return offline;
		} else {
			return online;
		}
	}


}