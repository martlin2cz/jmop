package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TemporarySimpleTrackedPlaylist;
import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.PlayerEngineWrapper;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

public class JMOPPlaying {
	
	private final BaseConfiguration config;
	private final MusicbaseListingEncapsulator listing;
	private final PlayerEngineWrapper engine;
	
	public JMOPPlaying(BaseConfiguration config, BaseMusicbase musicbase, BasePlayerEngine engine) {
		super();
		
		this.config = config;
		this.listing = new MusicbaseListingEncapsulator(musicbase);
		this.engine = new PlayerEngineWrapper(engine);
	}
	
	/**
	 * Only for testing purposes.
	 * @return
	 */
	protected BasePlayerEngine getEngine() {
		return engine.getEngine();
	}
	
	/////////////////////////////////////////////////////////////////

	public void play(Bundle bundle) {
		String allTracksPlaylistName = ((BaseDefaultStorageConfig) config).getAllTrackPlaylistName();
		Playlist allTracksPlaylist = listing.getPlaylist(bundle, allTracksPlaylistName);
		engine.play(allTracksPlaylist);
	}

	public void play(Playlist playlist)  {
		engine.play(playlist);
	}
	
	public void play(Track track) {
		Playlist playlist = new TemporarySimpleTrackedPlaylist(track);
		engine.play(playlist);
	}
	
	public void play(TrackIndex index) {
		engine.play(index);
	}
	
	public void play()  {
		engine.play();
	}


	public void stop()  {
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

	public void toNext()  {
		engine.toNext();
	}

	public void toPrevious()  {
		engine.toPrevious();
	}





}
