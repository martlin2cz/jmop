package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.PlayerEngineWrapper;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

public class JMOPPlaying {
	
//	private final MusicbaseListingEncapsulator listing;
	private final PlayerEngineWrapper engine;
	
	public JMOPPlaying(BaseMusicbase musicbase, BasePlayerEngine engine) {
		super();
//		this.listing = new MusicbaseListingEncapsulator(musicbase);
		this.engine = new PlayerEngineWrapper(engine);
	}
	

	public Bundle currentBundle() {
		return engine.currentBundle();
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

	/////////////////////////////////////////////////////////////////
	

	public void play(Playlist playlist) throws JMOPMusicbaseException {
		engine.play(playlist);
	}
	
	public void play() throws JMOPMusicbaseException {
		engine.play();
	}

	public void play(int index) throws JMOPMusicbaseException {
		engine.play(index);
	}

	public void stop() throws JMOPMusicbaseException {
		engine.stop();
	}

	public void pause() {
		engine.pause();
	}

	public void resume() {
		engine.resume();
	}

	public void seek(Duration to) {
		engine.seek(to);
	}

	public void toNext() throws JMOPMusicbaseException {
		engine.toNext();
	}

	public void toPrevious() throws JMOPMusicbaseException {
		engine.toPrevious();
	}

	/////////////////////////////////////////////////////////////////

}
