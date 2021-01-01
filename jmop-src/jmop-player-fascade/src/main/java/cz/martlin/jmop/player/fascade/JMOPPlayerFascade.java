package cz.martlin.jmop.player.fascade;

import java.io.File;
import java.util.Set;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

public class JMOPPlayerFascade {
	private final BasePlayerEngine engine;
	private final BaseMusicbase musicbase;


	public JMOPPlayerFascade(BasePlayerEngine engine, BaseMusicbase musicbase) {
		super();
		this.engine = engine;
		this.musicbase = musicbase;
	}

	public void startPlayingPlaylist(Playlist playlist) {
		engine.startPlayingPlaylist(playlist);
	}

	public void stopPlayingPlaylist() {
		engine.stopPlayingPlaylist();
	}

	public void playlistChanged() {
		engine.playlistChanged();
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

	public void load() throws JMOPMusicbaseException {
		musicbase.load();
	}

	public File trackFile(Track track) throws JMOPMusicbaseException {
		return musicbase.trackFile(track);
	}

	public void addBundle(Bundle bundle) throws JMOPMusicbaseException {
		musicbase.addBundle(bundle);
	}

	public Bundle createNewBundle(String name) throws JMOPMusicbaseException {
		return musicbase.createNewBundle(name);
	}

	public void addPlaylist(Playlist playlist) throws JMOPMusicbaseException {
		musicbase.addPlaylist(playlist);
	}

	public void renameBundle(Bundle bundle, String newName) throws JMOPMusicbaseException {
		musicbase.renameBundle(bundle, newName);
	}

	public void addTrack(Track track) throws JMOPMusicbaseException {
		musicbase.addTrack(track);
	}

	public void removeBundle(Bundle bundle) throws JMOPMusicbaseException {
		musicbase.removeBundle(bundle);
	}

	public Set<Bundle> bundles() throws JMOPMusicbaseException {
		return musicbase.bundles();
	}

	public void bundleUpdated(Bundle bundle) throws JMOPMusicbaseException {
		musicbase.bundleUpdated(bundle);
	}

	public Playlist createNewPlaylist(Bundle bundle, String name) throws JMOPMusicbaseException {
		return musicbase.createNewPlaylist(bundle, name);
	}

	public Set<Playlist> playlists(Bundle bundle) throws JMOPMusicbaseException {
		return musicbase.playlists(bundle);
	}

	public Set<Track> tracks(Bundle bundle) throws JMOPMusicbaseException {
		return musicbase.tracks(bundle);
	}

	public void renamePlaylist(Playlist playlist, String newName) throws JMOPMusicbaseException {
		musicbase.renamePlaylist(playlist, newName);
	}

	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPMusicbaseException {
		musicbase.movePlaylist(playlist, newBundle);
	}

	public void removePlaylist(Playlist playlist) throws JMOPMusicbaseException {
		musicbase.removePlaylist(playlist);
	}

	public void playlistUpdated(Playlist playlist) throws JMOPMusicbaseException {
		musicbase.playlistUpdated(playlist);
	}

	public Track createNewTrack(Bundle bundle, TrackData data) throws JMOPMusicbaseException {
		return musicbase.createNewTrack(bundle, data);
	}

	public void renameTrack(Track track, String newTitle) throws JMOPMusicbaseException {
		musicbase.renameTrack(track, newTitle);
	}

	public void moveTrack(Track track, Bundle newBundle) throws JMOPMusicbaseException {
		musicbase.moveTrack(track, newBundle);
	}

	public void removeTrack(Track track) throws JMOPMusicbaseException {
		musicbase.removeTrack(track);
	}

	public void trackUpdated(Track track) throws JMOPMusicbaseException {
		musicbase.trackUpdated(track);
	}
	
	
}
