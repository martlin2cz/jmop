package cz.martlin.jmop.player.engine.dflt.handlers;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.withhandlers.EngineHandlers.AfterTrackStartedHandler;

public class UpdatingPlaylistCurrentTrackHandler implements AfterTrackStartedHandler {

	private final BaseMusicbase musicbase;

	public UpdatingPlaylistCurrentTrackHandler(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
	}
	
	@Override
	public void afterTrackStarted(BasePlayerEngine engine, Track track) throws JMOPMusicbaseException {
		Playlist playlist = engine.currentPlaylist();
		
		musicbase.playlistUpdated(playlist);
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
