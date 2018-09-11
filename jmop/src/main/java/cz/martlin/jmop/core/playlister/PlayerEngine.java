package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.PlayerWrapper;
import javafx.util.Duration;

public class PlayerEngine {

	private final PlaylisterWrapper playlister;
	private final PlayerWrapper player;

	public PlayerEngine(PlaylisterWrapper playlister, PlayerWrapper player) {
		super();
		this.playlister = playlister;
		this.player = player;
	}

	public PlaylisterWrapper getPlaylister() {
		return playlister;
	}

	public PlayerWrapper getPlayer() {
		return player;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void startPlayingPlaylist(Playlist playlist) {
		playlister.startPlayingPlaylist(playlist);
	}

	public void stopPlayingPlaylist(Playlist currentPlaylist) {
		playlister.stopPlayingPlaylist(currentPlaylist);
	}

	public void playNext() throws JMOPSourceException {
		Track track = playlister.playNext();
		player.startPlaying(track);
	}

	public void play(int index) throws JMOPSourceException {
		Track track = playlister.play(index);
		player.startPlaying(track);
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
		Track track = playlister.toNext();
		player.startPlaying(track);
	}

	public void toPrevious() throws JMOPSourceException {
		Track track = playlister.toNext();
		player.startPlaying(track);
	}

	public void add(Track track) {
		playlister.add(track);
	}

}
