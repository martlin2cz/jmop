package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.engines.AbstractEngineWithPlayerAndRuntime;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.BeforeTrackStartedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.OnPlaylistEndedHandler;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.OnPlaylistStartedHandler;
import cz.martlin.jmop.player.players.BasePlayer;
import javafx.util.Duration;

public class EngineWithHandlers extends AbstractEngineWithPlayerAndRuntime {

	private final OnPlaylistStartedHandler playlistStarted;
	private final OnPlaylistEndedHandler playlistEnded;
	private final BeforeTrackStartedHandler beforeTrackStarted;
	private final AfterTrackStartedHandler afterTrackStarted;
	private final BeforeTrackEndedHandler beforeTrackEnded;
	private final AfterTrackEndedHandler afterTrackEnded;
	

	public EngineWithHandlers(BasePlayer player, //
			OnPlaylistStartedHandler playlistStarted, OnPlaylistEndedHandler playlistEnded, //
			BeforeTrackStartedHandler beforeTrackStarted, AfterTrackStartedHandler afterTrackStarted, //
			BeforeTrackEndedHandler beforeTrackEnded, AfterTrackEndedHandler afterTrackEnded) {

		super(player);
		
		this.playlistStarted = playlistStarted;
		this.playlistEnded = playlistEnded;
		this.beforeTrackStarted = beforeTrackStarted;
		this.afterTrackStarted = afterTrackStarted;
		this.beforeTrackEnded = beforeTrackEnded;
		this.afterTrackEnded = afterTrackEnded;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Track currentTrack() {
		return player.actualTrack();
	}

	@Override
	public Duration currentDuration() {
		return player.currentTime();
	}

	@Override
	public void startPlayingPlaylist(Playlist playlist) throws JMOPMusicbaseException {
		if (playlistStarted != null) {
			playlistStarted.onPlaylistStarted(this, playlist);
		}
		
		super.startPlayingPlaylist(playlist);
		
	}
	
	@Override
	public void stopPlayingPlaylist() throws JMOPMusicbaseException {
		Playlist playlist = currentPlaylist();
		
		super.stopPlayingPlaylist();
		
		if (playlistEnded != null) {
			playlistEnded.onPlaylistEnded(this, playlist);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void play() throws JMOPMusicbaseException {
		Track track = runtime.current();
		stopAndPlayAnother(track);
	}

	@Override
	public void play(int index) throws JMOPMusicbaseException {
		Track track = runtime.play(index);
		stopAndPlayAnother(track);
	}

	@Override
	public void stop() throws JMOPMusicbaseException {
		stopTrack();
	}

	@Override
	public void pause() {
		player.pause();
	}

	@Override
	public void resume() {
		player.resume();
	}

	@Override
	public void seek(Duration to) {
		player.seek(to);
	}

	@Override
	public void toNext() throws JMOPMusicbaseException {
		Track track = runtime.toNext();
		stopAndPlayAnother(track);
	}

	@Override
	public void toPrevious() throws JMOPMusicbaseException {
		Track track = runtime.toPrevious();
		stopAndPlayAnother(track);
	}

	@Override
	public void trackOver(Track track) throws JMOPMusicbaseException {
		ifHasPlayNext();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected void playTrack(Track track) throws JMOPMusicbaseException {
		if (beforeTrackStarted != null) {
			boolean canStart = beforeTrackStarted.beforeTrackStarted(this, track);

			if (!canStart) {
				return;
			}
		}

		super.playTrack(track);

		if (afterTrackStarted != null) {
			afterTrackStarted.afterTrackStarted(this, track);
		}
	}

	@Override
	protected void stopTrack() throws JMOPMusicbaseException {
		Track track = runtime.current();

		if (beforeTrackEnded != null) {
			beforeTrackEnded.beforeTrackEnded(this, track);
		}

		player.stop();

		if (afterTrackEnded != null) {
			afterTrackEnded.afterTrackEnded(this, track);
		}
	}

}
