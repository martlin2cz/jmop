package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import javafx.util.Duration;

public class JMOPPlaying {
	private final PlayerEngine engine;

	private Playlist currentPlaylist;

	public JMOPPlaying(PlayerEngine engine) {
		super();
		this.engine = engine;

	}

	protected PlayerEngine getEngine() {
		return engine;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void startPlayingPlaylist(Playlist playlist, boolean startPlaying) throws JMOPSourceException {
		if (currentPlaylist != null) {
			engine.stopPlayingPlaylist(currentPlaylist);
		}
		
		engine.startPlayingPlaylist(playlist);
		
		if (startPlaying) {
			startPlaying();
		}
	}

	public void stopPlayingPlaylist(Playlist currentPlaylist) {
		engine.stopPlayingPlaylist(currentPlaylist);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public void startPlaying() throws JMOPSourceException {
		engine.playNext();
	}

	public void stopPlaying() {
		engine.stop();
	}

	public void pausePlaying() {
		engine.pause();
	}

	public void resumePlaying() {
		engine.resume();
	}

	public void toNext() throws JMOPSourceException {
		engine.toNext();
	}

	public void toPrevious() throws JMOPSourceException {
		engine.toPrevious();
	}

	public void seek(Duration to) {
		engine.seek(to);
	}

	public void addToPlaylist(Track track) {
		engine.add(track);
	}

	public void playTrack(int index) throws JMOPSourceException {
		engine.play(index);
	}

}
