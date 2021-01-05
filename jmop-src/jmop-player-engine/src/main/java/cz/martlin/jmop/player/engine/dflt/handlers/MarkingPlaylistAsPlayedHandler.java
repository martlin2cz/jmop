package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.OnPlaylistStartedHandler;

public class MarkingPlaylistAsPlayedHandler implements OnPlaylistStartedHandler {

	private final BaseMusicbase musicbase;

	public MarkingPlaylistAsPlayedHandler(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
	}

	@Override
	public void onPlaylistStarted(BasePlayerEngine engine, Playlist playlist) throws JMOPMusicbaseException {
		playlist.played();
		musicbase.playlistUpdated(playlist);
	}

}
