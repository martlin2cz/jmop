package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.data.misc.TemporarySimpleTrackedPlaylist;
import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.storages.config.BaseDefaultStorageConfig;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.PlayerEngineWrapper;
import javafx.util.Duration;

/**
 * The jmop playing player fascade. Does all the playing stuff.
 * 
 * @author martin
 *
 */
public class JMOPPlaying {

	/**
	 * The configuration.
	 */
	private final BaseConfiguration config;
	/**
	 * The musicbase (readonly).
	 */
	private final MusicbaseListingEncapsulator listing;
	/**
	 * The actual playing engine.
	 */
	private final PlayerEngineWrapper engine;

	/**
	 * Creates.
	 * 
	 * @param config
	 * @param musicbase
	 * @param engine
	 */
	public JMOPPlaying(BaseConfiguration config, BaseMusicbase musicbase, BasePlayerEngine engine) {
		super();

		this.config = config;
		this.listing = new MusicbaseListingEncapsulator(musicbase);
		this.engine = new PlayerEngineWrapper(engine);
	}

	/**
	 * Only for testing purposes.
	 * 
	 * @return
	 */
	protected BasePlayerEngine getEngine() {
		return engine.getEngine();
	}

	/////////////////////////////////////////////////////////////////

	/**
	 * Starts playing bundle (all tracks inside).
	 * 
	 * @param bundle
	 */
	public void play(Bundle bundle) {
		String allTracksPlaylistName = ((BaseDefaultStorageConfig) config).getAllTracksPlaylistName();
		Playlist allTracksPlaylist = listing.getPlaylist(bundle, allTracksPlaylistName);
		engine.play(allTracksPlaylist);
	}

	/**
	 * Starts playing playlist.
	 * 
	 * @param playlist
	 */
	public void play(Playlist playlist) {
		engine.play(playlist);
	}

	/**
	 * Plays track (only that and stops).
	 * 
	 * @param track
	 */
	public void play(Track track) {
		Playlist playlist = new TemporarySimpleTrackedPlaylist(track);
		engine.play(playlist);
	}

	/**
	 * Starts playing track of given index in the current playlist.
	 * 
	 * @param index
	 */
	public void play(TrackIndex index) {
		engine.play(index);
	}

	/**
	 * Starts the playing, if stopped.
	 */
	public void play() {
		engine.play();
	}

	/**
	 * Stops the playing.
	 */
	public void stop() {
		engine.stop();
	}

	/**
	 * Pauses the playing.
	 */
	public void pause() {
		engine.pause();
	}

	/**
	 * Resumes the paused playing.
	 */
	public void resume() {
		engine.resume();
	}

	/**
	 * Seeks to given duration.
	 * 
	 * @param to
	 */
	public void seek(Duration to) {
		engine.seek(to);
	}

	/**
	 * Goes to next track.
	 */
	public void toNext() {
		engine.toNext();
	}

	/**
	 * Goes to previous track.
	 */
	public void toPrevious() {
		engine.toPrevious();
	}

}
