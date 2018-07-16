package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.player.BetterPlaylistRuntime;
import cz.martlin.jmop.core.player.JMOPPlaylister;

public class JMOPPlaying {

	private final JMOPPlaylister playlister;
	private Playlist currentPlaylist;

	public JMOPPlaying(JMOPPlaylister playlister, Playlist playlistToPlayOrNot) {
		super();
		this.playlister = playlister;
		this.currentPlaylist = playlistToPlayOrNot;

		if (playlistToPlayOrNot != null) {
			// TODO make this nicer
			BetterPlaylistRuntime runtime = playlistToPlayOrNot.getRuntime();
			playlister.setPlaylist(runtime);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public Playlist getCurrentPlaylist() {
		return currentPlaylist;
	}

	public void startPlayingPlaylist(Playlist playlist) {
		// TODO stop currently played?
		this.currentPlaylist = playlist;
		
		BetterPlaylistRuntime runtime = playlist.getRuntime();
		playlister.setPlaylist(runtime);
		startPlaying();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public void startPlaying() {
		playlister.play();
	}

	public void stopPlaying() {
		playlister.stop();
	}

	public void pausePlaying() {
		playlister.pause();
	}

	public void resumePlaying() {
		playlister.resume();
	}

	public void toNext() {
		playlister.toNext();
	}

	public void toPrevious() {
		playlister.toPrevious();
	}

}
