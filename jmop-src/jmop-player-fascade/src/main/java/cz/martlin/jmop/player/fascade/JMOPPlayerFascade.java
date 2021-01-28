package cz.martlin.jmop.player.fascade;

import java.util.Set;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.engines.PlayerEngineWrapper;
import cz.martlin.jmop.player.fascade.dflt.BaseDefaultJMOPConfig;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;


/**
 * @deprecated replaced by {@link JMOPPlayer}.
 * @author martin
 *
 */
@Deprecated
public class JMOPPlayerFascade {
	private final PlayerEngineWrapper engine;
	private final MusicbaseListingEncapsulator musicbaseListing;
	private final MusicbaseModyfiingEncapsulator musicbaseModyfiing;

	private final JMOPPlayerAdapter adapter;
	private final BaseDefaultJMOPConfig config;

	public JMOPPlayerFascade(BasePlayerEngine engine, BaseMusicbase musicbase, BaseDefaultJMOPConfig config) {
		super();
		this.engine = new PlayerEngineWrapper(engine);
		this.musicbaseListing = new MusicbaseListingEncapsulator(musicbase);
		this.musicbaseModyfiing = new MusicbaseModyfiingEncapsulator(musicbase);

		this.adapter = new JMOPPlayerAdapter(musicbase);
		this.config = config;
	}

	public JMOPPlayerAdapter adapter() {
		return adapter;
	}

	public BaseDefaultJMOPConfig config() {
		return config;
	}



	/////////////////////////////////////////////////////////////////
	public Set<Bundle> bundles()  {
		return musicbaseListing.bundles();
	}
	/////////////////////////////////////////////////////////////////

	public Set<Playlist> playlists(Bundle bundleOrNull)  {
		return musicbaseListing.playlists(bundleOrNull);
	}

	/////////////////////////////////////////////////////////////////

	public Set<Track> tracks(Bundle bundleOrNull)  {
		return musicbaseListing.tracks(bundleOrNull);
	}



	/////////////////////////////////////////////////////////////////

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
	public void play(Playlist playlist)  {
		engine.play(playlist);
	}
	
	public void play()  {
		engine.play();
	}

	public void play(int index)  {
		throw new UnsupportedOperationException();
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

	/////////////////////////////////////////////////////////////////

	public Bundle createNewBundle(String name)  {
		return musicbaseModyfiing.createNewBundle(name);
	}

	public void renameBundle(Bundle bundle, String newName)  {
		musicbaseModyfiing.renameBundle(bundle, newName);
	}

	public void removeBundle(Bundle bundle)  {
		musicbaseModyfiing.removeBundle(bundle);
	}

	public Playlist createNewPlaylist(Bundle bundle, String name)  {
		return musicbaseModyfiing.createNewPlaylist(bundle, name);
	}

	public void renamePlaylist(Playlist playlist, String newName)  {
		musicbaseModyfiing.renamePlaylist(playlist, newName);
	}

	public void movePlaylist(Playlist playlist, Bundle newBundle)  {
		//TODO what to do with track in it? move too, keep original or copy?
		musicbaseModyfiing.movePlaylist(playlist, newBundle, false);
	}

	public void removePlaylist(Playlist playlist)  {
		musicbaseModyfiing.removePlaylist(playlist);
	}

	public Track createNewTrack(Bundle bundle, TrackData data)  {
		throw new UnsupportedOperationException("deprectaed");
	}

	public void renameTrack(Track track, String newTitle)  {
		musicbaseModyfiing.renameTrack(track, newTitle);
	}

	public void moveTrack(Track track, Bundle newBundle)  {
		//TODO remove from all playlists it contains?
		musicbaseModyfiing.moveTrack(track, newBundle);
	}

	public void removeTrack(Track track)  {
		musicbaseModyfiing.removeTrack(track);
	}
	
	public void updateTrack(Track track, TrackData newData)  {
		musicbaseModyfiing.updateTrack(track, newData);
	}

}
