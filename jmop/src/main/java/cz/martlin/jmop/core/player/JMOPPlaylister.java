package cz.martlin.jmop.core.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlister.base.BasePlaylister;
import cz.martlin.jmop.core.sources.AutomaticSavesPerformer;
import javafx.util.Duration;

@Deprecated
public class JMOPPlaylister {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final BasePlayer player;
	private final InternetConnectionStatus connection;
	private final Void online;
	private final Void offline;
	private final AutomaticSavesPerformer saver;
	private BetterPlaylistRuntime playlist;

	public JMOPPlaylister(BasePlayer player, XXX_TrackPreparer preparer, InternetConnectionStatus connection,
			AutomaticSavesPerformer saver) {
		super();

		this.player = player;
		this.connection = connection;
		this.online = null;//new XXX_OnlinePlaylister(preparer, this, connection);
		this.offline = null; //new XXX_OfflinePlaylister();
		this.saver = saver;
	}

	public BetterPlaylistRuntime getPlaylist() {
		return playlist;
	}

	public void setPlaylist(BetterPlaylistRuntime playlist) {
		this.playlist = playlist;

		//this.online.setPlaylist(playlist);
		//this.offline.setPlaylist(playlist);
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
//		Track track = playlister.next();
		Track track = null; //FIXME

		startPlayingWithExceptionCheck(track);
	}

	public void toPrevious() {
		LOG.info("To previous");

		BasePlaylister playlister = getPlaylisterStrategy();
//		Track track = playlister.previous();
		Track track = null; //FIXME

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
			player.startPlaying(track);
		} catch (JMOPSourceException e) {
			// TODO exception
			e.printStackTrace();
		}
	}

	private BasePlaylister getPlaylisterStrategy() {
		boolean isOffline = connection.isOffline();

		if (isOffline) {
			//return offline;
		} else {
			//return online;
		}
		return null;
	}


}