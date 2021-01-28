package cz.martlin.jmop.player.engine.engines;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

public class PlayerEngineWrapper {
	private final BasePlayerEngine engine;

	public PlayerEngineWrapper(BasePlayerEngine engine) {
		super();
		this.engine = engine;
	}

	public void play(Playlist playlist)  {
		if (engine.currentPlaylist() != null) {
			engine.stopPlayingPlaylist();
		}

		engine.startPlayingPlaylist(playlist);
		engine.play();
	}

	public void playlistChanged() {
		engine.playlistChanged();
		// TODO if currently played track is no more in the playlist, toNext()
	}

	public void terminate()  {
		if (engine.currentPlaylist() != null) {
			if (engine.currentStatus().isPlayingTrack()) {
				engine.stop();
			}

			engine.stopPlayingPlaylist();
		}
	}

	///////////////////////////////////////////////////////////////////////////

	public Bundle currentBundle() {
		if (engine.currentPlaylist() != null) {
			return engine.currentPlaylist().getBundle();
		} else {
			return null;
		}
	}

	public Playlist currentPlaylist() {
		return engine.currentPlaylist();
	}

	public Track currentTrack() {
		return engine.currentTrack();
	}

	public Duration currentDuration() {
		return engine.currentDuration();
	}

	public PlayerStatus currentStatus() {
		return engine.currentStatus();
	}
	

	public boolean hasPrevious() {
		requirePlaying();
		return engine.hasPrevious();
	}
	
	public boolean hasNext() {
		requirePlaying();
		return engine.hasNext();
	}

///////////////////////////////////////////////////////////////////////////

	
	public void play()  {
		requirePlaying();
		engine.play();
	}

	public void play(TrackIndex index)  {
		requirePlaying();
		engine.play(index);
	}

	public void stop()  {
		requirePlaying();
		engine.stop();
	}

	public void pause() {
		requirePlaying();
		engine.pause();
	}

	public void resume() {
		requirePlaying();
		engine.resume();
	}

	public void seek(Duration to) {
		requirePlaying();
		engine.seek(to);
	}

	public void toNext()  {
		requirePlaying();
		engine.toNext();
	}

	public void toPrevious()  {
		requirePlaying();
		engine.toPrevious();
	}

///////////////////////////////////////////////////////////////////////////////

	private void requirePlaying() {
		if (engine.currentPlaylist() == null) {
			throw new IllegalStateException("Not playing");
		}
	}

}
