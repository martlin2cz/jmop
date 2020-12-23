package cz.martlin.jmop.player.engine.engines.withhandlers;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.runtime.PlaylistRuntime;
import cz.martlin.jmop.player.players.BasePlayer;
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
	public void startPlayingPlaylist(Playlist playlist) {
		runtime = new PlaylistRuntime(playlist);
	}

	@Override
	public void stopPlayingPlaylist(Playlist playlist) {
		runtime = null;
	}

	@Override
	public void playlistChanged() {
		Playlist currentPlaylist = runtime.getPlaylist();
		runtime = new PlaylistRuntime(currentPlaylist);
	}
	
	///////////////////////////////////////////////////////////////////////////

}