package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.PlayerWrapper;
import javafx.util.Duration;

public class Playlister {
	private final InternetConnectionStatus connection;
	private final PlaylisterWrapper offlinePlaylister;
	private final PlaylisterWrapper onlinePlaylister;
	private final PlayerWrapper player;

	public Playlister(InternetConnectionStatus connection, PlaylisterWrapper offlinePlaylister,
			PlaylisterWrapper onlinePlaylister, PlayerWrapper player) {
		super();
		this.connection = connection;
		this.offlinePlaylister = offlinePlaylister;
		this.onlinePlaylister = onlinePlaylister;
		this.player = player;
	}
	

	public void startPlayingPlaylist(Playlist playlist) {
		// TODO A!!!
		throw new UnsupportedOperationException();
	}



	public void stopPlayingPlaylist(Playlist currentPlaylist) {
		// TODO !!!
		throw new UnsupportedOperationException();
	}


	////////////////////////////////////////////////////////////////////////////

	public void playNext() throws JMOPSourceException {
		PlaylisterWrapper playlister = currentPlaylister();
		Track track = playlister.toNextToPlay();
		player.startPlayling(track);
	}

	public void play(int index) throws JMOPSourceException {
		PlaylisterWrapper playlister = currentPlaylister();
		Track track = playlister.toTrackAt(index);
		player.startPlayling(track);
	}

	public void stop() {
		player.stop();
	}

	public void pause() {
		player.pause();
	}

	public void resume() {
		player.resume();
	}

	public void seek(Duration to) {
		player.seek(to);
	}

	public void toNext() throws JMOPSourceException {
		PlaylisterWrapper playlister = currentPlaylister();
		Track track = playlister.toNext();
		player.startPlayling(track);
	}

	public void toPrevious() throws JMOPSourceException {
		PlaylisterWrapper playlister = currentPlaylister();
		Track track = playlister.toPrevious();
		player.startPlayling(track);
	}

	public void add(Track track) {
		PlaylisterWrapper playlister = currentPlaylister();
		playlister.addTrack(track);
	}

	////////////////////////////////////////////////////////////////////////////

	private PlaylisterWrapper currentPlaylister() {
		if (connection.isOffline()) {
			return offlinePlaylister;
		} else {
			return onlinePlaylister;
		}
	}



}
