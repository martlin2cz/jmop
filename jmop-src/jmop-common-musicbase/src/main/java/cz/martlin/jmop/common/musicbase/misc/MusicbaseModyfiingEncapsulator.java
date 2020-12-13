package cz.martlin.jmop.common.musicbase.misc;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public class MusicbaseModyfiingEncapsulator {
	private final BaseMusicbase musicbase;

	public MusicbaseModyfiingEncapsulator(BaseMusicbase musicbase) {
		super();
		this.musicbase = musicbase;
	}
	

	public void load() throws JMOPSourceException {
		musicbase.load();
	}

	public void addBundle(Bundle bundle) throws JMOPSourceException {
		musicbase.addBundle(bundle);
	}

	public Bundle createNewBundle(String name) throws JMOPSourceException {
		return musicbase.createNewBundle(name);
	}

	public void addPlaylist(Playlist playlist) throws JMOPSourceException {
		musicbase.addPlaylist(playlist);
	}

	public void renameBundle(Bundle bundle, String newName) throws JMOPSourceException {
		musicbase.renameBundle(bundle, newName);
	}

	public void addTrack(Track track) throws JMOPSourceException {
		musicbase.addTrack(track);
	}

	public void removeBundle(Bundle bundle) throws JMOPSourceException {
		musicbase.removeBundle(bundle);
	}

	public void bundleUpdated(Bundle bundle) throws JMOPSourceException {
		musicbase.bundleUpdated(bundle);
	}

	public Playlist createNewPlaylist(Bundle bundle, String name) throws JMOPSourceException {
		return musicbase.createNewPlaylist(bundle, name);
	}


	public void renamePlaylist(Playlist playlist, String newName) throws JMOPSourceException {
		musicbase.renamePlaylist(playlist, newName);
	}

	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPSourceException {
		musicbase.movePlaylist(playlist, newBundle);
	}

	public void removePlaylist(Playlist playlist) throws JMOPSourceException {
		musicbase.removePlaylist(playlist);
	}

	public void playlistUpdated(Playlist playlist) throws JMOPSourceException {
		musicbase.playlistUpdated(playlist);
	}

	public Track createNewTrack(Bundle bundle, TrackData data) throws JMOPSourceException {
		return musicbase.createNewTrack(bundle, data);
	}

	public void renameTrack(Track track, String newTitle) throws JMOPSourceException {
		musicbase.renameTrack(track, newTitle);
	}

	public void moveTrack(Track track, Bundle newBundle) throws JMOPSourceException {
		musicbase.moveTrack(track, newBundle);
	}

	public void removeTrack(Track track) throws JMOPSourceException {
		musicbase.removeTrack(track);
	}

	public void trackUpdated(Track track) throws JMOPSourceException {
		musicbase.trackUpdated(track);
	}

}
