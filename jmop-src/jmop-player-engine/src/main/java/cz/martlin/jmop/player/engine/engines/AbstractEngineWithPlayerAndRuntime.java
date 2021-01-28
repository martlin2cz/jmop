package cz.martlin.jmop.player.engine.engines;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.runtime.PlaylistRuntime;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.PlayerStatus;
import cz.martlin.jmop.player.players.TrackFinishedListener;

public abstract class AbstractEngineWithPlayerAndRuntime implements BasePlayerEngine, TrackFinishedListener {

	protected final BasePlayer player;
	protected PlaylistRuntime runtime;

	public AbstractEngineWithPlayerAndRuntime(BasePlayer player) {
		super();

		this.player = player;
		this.runtime = null;

		player.specifyListener(this);
	}

	@Override
	public void startPlayingPlaylist(Playlist playlist)  {
		runtime = new PlaylistRuntime(playlist);
	}

	@Override
	public void stopPlayingPlaylist()  {
		runtime = null;
	}

	@Override
	public void playlistChanged() {
		Playlist currentPlaylist = runtime.getPlaylist();
		runtime = new PlaylistRuntime(currentPlaylist);
	}
	
	@Override
	public Playlist currentPlaylist() {
		if (runtime == null) {
			return null;
		} else {
			return runtime.getPlaylist();
		}
	}
	
	@Override
	public PlayerStatus currentStatus() {
		return player.currentStatus();
	}
	

	@Override
	public boolean hasNext() {
		return runtime.hasNextToPlay();
	}
	
	@Override
	public boolean hasPrevious() {
		return runtime.hasPlayed();
	}
	
	///////////////////////////////////////////////////////////////////////////

	protected void stopAndPlayAnother(Track track)  {
		if (player.currentStatus().isPlayingTrack()) {
			stopTrack();
		}
		
		playTrack(track);
	}

	protected void stopTrack()  {
		player.stop();
	}

	protected void playTrack(Track track)  {
		player.startPlaying(track);
	}
	
	protected void ifHasPlayNext()  {
		if (runtime.hasNextToPlay()) {
			toNext();
		}
	}
}