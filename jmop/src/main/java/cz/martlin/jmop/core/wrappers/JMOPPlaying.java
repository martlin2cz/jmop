package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.PlayerWrapper;
import cz.martlin.jmop.core.playlister.Playlister;
import cz.martlin.jmop.core.playlister.PlaylisterWrapper;
import javafx.util.Duration;

public class JMOPPlaying {
	private final Playlister playlister;
	private Playlist currentPlaylist;

	public JMOPPlaying(Playlister playlister) {
		super();
		this.playlister = playlister;

	}
	
	protected Playlister getPlaylister() {
		return playlister;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void startPlayingPlaylist(Playlist playlist) throws JMOPSourceException {
		playlister.stopPlayingPlaylist(currentPlaylist);

		playlister.startPlayingPlaylist(playlist);

		startPlaying();
	}

	public void addToPlaylist(Track track) {
		playlister.add(track);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public void startPlaying() throws JMOPSourceException {
		playlister.playNext();
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

	public void toNext() throws JMOPSourceException {
		playlister.toNext();

	}

	public void toPrevious() throws JMOPSourceException {
		playlister.toPrevious();
	}

	public void seek(Duration to) {
		playlister.seek(to);
	}

}
